package uz.technocorp.ecosystem.utils;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.integration.iip.IIPService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.uploadexcel.dto.HfUploadDto;
import uz.technocorp.ecosystem.modules.user.User;
import uz.technocorp.ecosystem.modules.user.UserRepository;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;
import uz.technocorp.ecosystem.modules.user.helper.UserViewByInspectorPin;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 25.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class ExcelReader {

    private final IIPService iipService;
    private final UserService userService;

    private static final int EXPECTED_COLUMNS = 25;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public List<HfUploadDto> read (MultipartFile file) {

        List<HfUploadDto> results = new ArrayList<>();
        try(InputStream in = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(in)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            //skip header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            int rowNumber = 2; //starting from row 2 (after header)
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (isRowEmpty(row)){
                    continue;
                }

                HazardousFacility hf = mapRowToEntity(row, rowNumber);


            }

            return results;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HazardousFacility mapRowToEntity(Row row, int rowNumber) {

        User legalUser = getLegalUser(row, rowNumber);
        Profile legalProfile = profileRepository.findById(legalUser.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profile", "ID", legalUser.getProfileId()));
        LocalDate registrationDate = LocalDate.parse(row.getCell(1).getStringCellValue());
        UserViewByInspectorPin inspector = userService.getInspectorByPin(Long.parseLong(row.getCell(4).getStringCellValue()));


//        HazardousFacility.builder()
//                .legalTin(legalProfile.getTin())
//                .legalName(legalProfile.getLegalName())
//                .orderNumber(7L)
//                .registrationDate(registrationDate)
//                .registryNumber(row.getCell(2).getStringCellValue())
//                .regionId(Integer.parseInt(row.getCell(10).getStringCellValue()))
//                .districtId(Integer.parseInt(row.getCell(12).getStringCellValue()))
//                .profileId(legalProfile.getId())
//                .
//                .build();

        return null;
    }

    private User getLegalUser(Row row, int rowNumber) {
        try {
            User legalUser;
            Optional<User> byUsername = userRepository.findByUsername(row.getCell(7).getStringCellValue());
            if (byUsername.isPresent()) {
                legalUser = byUsername.get();
            }else {
                LegalUserDto legalInfo = iipService.getGnkInfo(row.getCell(7).getStringCellValue());
                legalUser = userService.create(legalInfo);
            }
            return legalUser;
        }catch (Exception e){
            throw  new RuntimeException(rowNumber + " qatorda xatolik yuz berdi: " + e.getMessage());
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int i = 0; i < EXPECTED_COLUMNS; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK ){
                return false;
            }
        }
        return true;
    }
}
