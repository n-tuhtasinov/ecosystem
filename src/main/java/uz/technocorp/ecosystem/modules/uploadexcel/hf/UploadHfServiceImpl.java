package uz.technocorp.ecosystem.modules.uploadexcel.hf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityRepository;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.hf.enums.HFSphere;
import uz.technocorp.ecosystem.modules.hfappeal.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.hftype.HfTypeService;
import uz.technocorp.ecosystem.modules.integration.iip.IIPService;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 20.06.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UploadHfServiceImpl implements UploadHfExcelService {

    private final HazardousFacilityRepository hazardousFacilityRepository;
    private final IIPService iipService;
    private final ProfileService profileService;
    private final UserService userService;
    private final DistrictService districtService;
    private final RegionService regionService;
    private final HazardousFacilityService hazardousFacilityService;
    private final HfTypeService hfTypeService;

//    @Transactional(rollbackFor = ExcelParsingException.class)
    @Override
    public void upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Fayl bo'sh bo'lishi mumkin emas");
        }

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            // Oxirgi ma'lumotga ega qator raqami
            int lastRowNum = sheet.getLastRowNum();

            for (int rowIndex = 2; rowIndex <= lastRowNum; rowIndex++) {

                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int excelRowNumber = rowIndex + 1;
                String registryNumber = null;

                try {
                    HazardousFacility hf = new HazardousFacility();

                    registryNumber = getRegistryNumber(dataFormatter, row, hf); // b) registryNumber
                    getRegistrationDate(row, hf); // a) registrationDate
                    getInspectorName(dataFormatter, row, hf); // c) inspectorName
                    getUpperOrganication(dataFormatter, row, hf); // d) upperOrganization
                    getLegal(dataFormatter, row, hf); // e) legalTin
                    getHfName(dataFormatter, row, hf); // f) name
                    District district = getDistrict(dataFormatter, row, hf); // g) districtSoato
                    getRegionAndAddress(dataFormatter, row, district, hf); // h) address
                    getHfType(dataFormatter, row, hf); // i) hfTypeId
                    getExtraArea(dataFormatter, row, hf); // j) extraArea
                    getSubstance(dataFormatter, row, hf); // k) hazardousSubstance
                    getHfSpheres(dataFormatter, row, hf); // l) hFSphere
                    setFiles(hf); // set files
                    getDescription(dataFormatter, row, hf); // description

                    //create registry file
                    Appeal appeal = Appeal.builder().legalName(hf.getLegalName()).legalTin(hf.getLegalTin()).address(hf.getAddress()).build();
                    String hfTypeName = hfTypeService.getHfTypeNameById(hf.getHfTypeId());
                    HfAppealDto dto = new HfAppealDto();
                    dto.setUpperOrganization(hf.getUpperOrganization());
                    dto.setName(hf.getName());
                    dto.setHfTypeName(hfTypeName);
                    dto.setExtraArea(hf.getExtraArea());
                    dto.setHazardousSubstance(hf.getHazardousSubstance());
                    String registryPdfPath = hazardousFacilityService.createHfRegistryPdf(appeal, hf.getRegistryNumber(), dto, hf.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    hf.setRegistryFilePath(registryPdfPath);

                    hazardousFacilityRepository.save(hf);



                } catch (Exception e) {
                    log.error("Xatolik! Excel faylning {}-qatoridagi {} sonli ro'yhat raqamli ma'lumotlarni o'qishda muammo yuzaga keldi. Tafsilotlar: {}", excelRowNumber, registryNumber, e.getMessage());
//                    throw new ExcelParsingException("Excel faylni o'qishda xatolik", excelRowNumber, e.getMessage(), e);
                }
            }
            log.info("Fayl muvaffaqiyatli o'qildi. {} qator ma'lumot o'qildi.", lastRowNum+1);

//        } catch (ExcelParsingException e) {
//            throw e; // to rollback transaction
        } catch (Exception e) {
            log.error("Excel faylni qayta ishlashda kutilmagan xatolik: {}", e.getMessage());
//            throw new RuntimeException("Excel faylni qayta ishlashda kutilmagan xatolik: " + e.getMessage(), e);
        }
    }

    private void getDescription(DataFormatter dataFormatter, Row row, HazardousFacility hf) {
        String description = dataFormatter.formatCellValue(row.getCell(17));
        if (description != null &&  !description.isBlank()) {
            hf.setDescription(description);
        }
    }

    private static void setFiles(HazardousFacility hf) {
        Map<String, String> files = new HashMap<>();
        files.put("identificationCardPath", null);
        files.put("receiptPath", null);
        files.put("expertOpinionPath", null);
        files.put("projectDocumentationPath", null);
        files.put("cadastralPassportPath", null);
        files.put("industrialSafetyDeclarationPath", null);
        files.put("insurancePolicyPath", null);
        files.put("licensePath", null);
        files.put("permitPath", null);
        files.put("certificationPath", null);
        files.put("deviceTestingPath", null);
        files.put("appointmentOrderPath", null);
        files.put("ecologicalConclusionPath", null);
        files.put("fireSafetyConclusionPath", null);
        hf.setFiles(files);
    }

    private static void getHfSpheres(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String hfSpheresStr = dataFormatter.formatCellValue(row.getCell(18));
        if (hfSpheresStr == null || hfSpheresStr.isBlank()) {
            throw new Exception("hfSphere(l) bo'sh bo'lishi mumkin emas");
        }
        List<HFSphere> spheres = Arrays.stream(hfSpheresStr.split(","))
                .map(String::trim)
                .map(HFSphere::valueOf)
                .collect(Collectors.toList());
        hf.setSpheres(spheres);
    }

    private static void getSubstance(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String substance = dataFormatter.formatCellValue(row.getCell(16));
        if (substance == null || substance.isBlank()) {
            throw new Exception("hazardousSubstance(k) bo'sh bo'lishi mumkin emas");
        }
        hf.setHazardousSubstance(substance);
    }

    private static void getExtraArea(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String extraArea = dataFormatter.formatCellValue(row.getCell(15));
        if (extraArea == null || extraArea.isBlank()) {
            throw new Exception("extraArea(j) bo'sh bo'lishi mumkin emas");
        }
        hf.setExtraArea(extraArea);
    }

    private static void getHfType(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String hfTypeIdStr = dataFormatter.formatCellValue(row.getCell(14));
        if (hfTypeIdStr == null || hfTypeIdStr.isBlank()) {
            throw new Exception("hfTypeId(i) bo'sh bo'lishi mumkin emas");
        }
        hf.setHfTypeId(Integer.valueOf(hfTypeIdStr));
    }

    private void getRegionAndAddress(DataFormatter dataFormatter, Row row, District district, HazardousFacility hf) throws Exception {
        String addressExcel = dataFormatter.formatCellValue(row.getCell(13));
        if (addressExcel == null || addressExcel.isBlank()) {
            throw new Exception("address(h) bo'sh bo'lishi mumkin emas");
        }
//        Region region = regionService.findById(district.getRegionId());
//        String fullAddress = String.format("%s, %s, %s",
//                region.getName(),
//                district.getName(),
//                addressExcel);
        hf.setAddress(addressExcel);
    }

    private District getDistrict(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String soato = dataFormatter.formatCellValue(row.getCell(12));
        if (soato == null || soato.isBlank()) {
            throw new Exception("districtSoato(g) bo'sh bo'lishi mumkin emas");
        }
        District district = districtService.findBySoato(Integer.valueOf(soato));
        hf.setDistrictId(district.getId());
        hf.setRegionId(district.getRegionId());
        return district;
    }

    private static void getHfName(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String name = dataFormatter.formatCellValue(row.getCell(8));
        if (name == null || name.isBlank()) {
            throw new Exception("name bo'sh bo'lishi mumkin emas");
        }
        hf.setName(name);
    }

    private void getLegal(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String legalTin = dataFormatter.formatCellValue(row.getCell(7));
        if (legalTin == null || legalTin.isBlank()) {
            throw new Exception("legalTin bo'sh bo'lishi mumkin emas");
        }
        boolean isExist = profileService.existsProfileByTin(Long.parseLong(legalTin));
        if (!isExist) {
            LegalUserDto legalDto = iipService.getGnkInfo(legalTin);
            userService.create(legalDto);
        }
        ProfileInfoView profileInfo = profileService.getProfileInfo(Long.parseLong(legalTin));
        hf.setProfileId(profileInfo.getId());
        hf.setLegalTin(profileInfo.getTin());
        hf.setLegalName(profileInfo.getLegalName());
        hf.setLegalAddress(profileInfo.getLegalAddress());
    }

    private static void getUpperOrganication(DataFormatter dataFormatter, Row row, HazardousFacility hf) {
        String upperOrganization = dataFormatter.formatCellValue(row.getCell(5));
        if (upperOrganization == null || upperOrganization.isBlank()) {
            upperOrganization = null;
        }
        hf.setUpperOrganization(upperOrganization);
    }

    private static void getInspectorName(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String inspectorName = dataFormatter.formatCellValue(row.getCell(3));
        if (inspectorName == null || inspectorName.isBlank()) {
            throw new Exception("inspectorName bo'sh bo'lishi mumkin emas");
        }
        hf.setInspectorName(inspectorName);
    }

    private static String getRegistryNumber(DataFormatter dataFormatter, Row row, HazardousFacility hf) throws Exception {
        String registryNumber = dataFormatter.formatCellValue(row.getCell(2));
        if (registryNumber == null || registryNumber.isBlank()) {
            throw new Exception("registryNumber bo'sh bo'lishi mumkin emas");
        }
        hf.setRegistryNumber(registryNumber.trim());
        return registryNumber;
    }

    private void getRegistrationDate(Row row, HazardousFacility hf) throws Exception {
        Cell cell = row.getCell(1);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            throw new Exception("registrationDate bo'sh bo'lishi mumkin emas");
        }
        if (cell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(cell)) {
            throw new Exception("registrationDate format yacheykasi date bo'lishi kerak");
        }
        LocalDate registerDate = cell.getLocalDateTimeCellValue().toLocalDate();
        hf.setRegistrationDate(registerDate);
    }
}
