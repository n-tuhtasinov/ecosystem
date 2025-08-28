package uz.technocorp.ecosystem.modules.uploadexcel.irs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.hfappeal.register.dto.HfAppealDto;
import uz.technocorp.ecosystem.modules.integration.iip.IIPService;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSource;
import uz.technocorp.ecosystem.modules.irs.IonizingRadiationSourceRepository;
import uz.technocorp.ecosystem.modules.irs.enums.IrsCategory;
import uz.technocorp.ecosystem.modules.irs.enums.IrsIdentifierType;
import uz.technocorp.ecosystem.modules.irs.enums.IrsUsageType;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 28.08.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UploadIrsServiceImpl implements UploadIrsService {

    private final ProfileService profileService;
    private final IIPService iipService;
    private final UserService userService;
    private final RegionService regionService;
    private final DistrictService districtService;

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private final IonizingRadiationSourceRepository ionizingRadiationSourceRepository;

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
                    IonizingRadiationSource irs = new IonizingRadiationSource();

                    registryNumber = getRegistryNumber(dataFormatter, row, irs); // b) registryNumber
                    getOrderNumber(dataFormatter, row, irs);
                    getLegal(dataFormatter, row, irs); // e) legalTin
                    getParent(dataFormatter, row, irs);
                    getRegionAndDistrict(dataFormatter, row, irs); // g) districtSoato
                    getAddress(dataFormatter, row, irs); // g) address
                    getSupervisor(dataFormatter, row, irs);
                    getDivision(dataFormatter, row, irs);
                    getIdentifierType(dataFormatter, row, irs);
                    getSymbol(dataFormatter, row, irs);
                    getSphere(dataFormatter, row, irs);
                    getFactoryNumber(dataFormatter, row, irs);
                    getSerialNumber(dataFormatter, row, irs);
                    getActivity(dataFormatter, row, irs);
                    getType(dataFormatter, row, irs);
                    getCategory(dataFormatter, row, irs);
                    getCountry(dataFormatter, row, irs);
                    getManufacturedAt(row, irs);
                    getAcceptedFrom(dataFormatter, row, irs);
                    getAcceptedAt(row, irs);
                    getIsValid(dataFormatter, row, irs);
                    getUsageType(dataFormatter, row, irs);
                    getStorageLocation(dataFormatter, row, irs);
                    getRegistrationDate(row, irs);
                    getInspectorName(dataFormatter, row, irs);
                    getDescription(dataFormatter, row, irs);
//                    setFiles(irs); // set files


                    //create registry file //TODO: pdfni generatsi qilish kerak
//                    Appeal appeal = Appeal.builder().ownerName(irs.getLegalName()).ownerIdentity(irs.getLegalTin()).address(irs.getAddress()).build();
//                    String hfTypeName = hfTypeService.getHfTypeNameById(irs.getHfTypeId());
//                    HfAppealDto dto = new HfAppealDto();
//                    dto.setUpperOrganization(irs.getUpperOrganization());
//                    dto.setName(irs.getName());
//                    dto.setHfTypeName(hfTypeName);
//                    dto.setExtraArea(irs.getExtraArea());
//                    dto.setHazardousSubstance(irs.getHazardousSubstance());
//                    String registryPdfPath = hazardousFacilityService.createHfRegistryPdf(appeal, irs.getRegistryNumber(), dto, irs.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//                    irs.setRegistryFilePath(registryPdfPath);

                    ionizingRadiationSourceRepository.save(irs);
                } catch (Exception e) {
                    log.error("Xatolik! Excel faylning {}-qatoridagi {} sonli ro'yhat raqamli ma'lumotlarni o'qishda muammo yuzaga keldi. Tafsilotlar: {}", excelRowNumber, registryNumber, e.getMessage());
                }
            }
            log.info("Fayl muvaffaqiyatli o'qildi. {} qator ma'lumot o'qildi.", lastRowNum + 1);
        } catch (Exception e) {
            log.error("Excel faylni qayta ishlashda kutilmagan xatolik: {}", e.getMessage());
        }
    }

    private void getDescription(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) {
        String description = dataFormatter.formatCellValue(row.getCell(33));
        if (description != null && !description.isBlank()) {
            irs.setDescription(description);
        }
    }

    private void getInspectorName(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String inspectorName = getAsStringNotNull(dataFormatter, row, 31);
        irs.setInspectorName(inspectorName);
    }

    private void getRegistrationDate(Row row, IonizingRadiationSource irs) throws Exception {
        LocalDate localDate = getLocalDate(row.getCell(30), "registrationDate");
        irs.setRegistrationDate(localDate);
    }

    private void getStorageLocation(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String storageLocation = getAsStringNotNull(dataFormatter, row, 29);
        irs.setStorageLocation(storageLocation);
    }

    private void getUsageType(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String usageType = getAsStringNotNull(dataFormatter, row, 28);
        IrsUsageType irsUsageType = IrsUsageType.valueOf(usageType);
        irs.setUsageType(irsUsageType);
    }

    private void getIsValid(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String isValid = getAsStringNotNull(dataFormatter, row, 27);
        irs.setIsValid(Boolean.parseBoolean(isValid));
    }

    private void getAcceptedAt(Row row, IonizingRadiationSource irs) throws Exception {
        LocalDate acceptedAt = getLocalDate(row.getCell(26), "acceptedAt");
        irs.setAcceptedAt(acceptedAt);
    }

    private void getAcceptedFrom(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String acceptedFrom = getAsStringNotNull(dataFormatter, row, 25);
        irs.setAcceptedFrom(acceptedFrom);
    }

    private void getManufacturedAt(Row row, IonizingRadiationSource irs) throws Exception {
        LocalDate manufacturedAt = getLocalDate(row.getCell(24), "manufacturedAt");
        irs.setManufacturedAt(manufacturedAt);
    }

    private void getCountry(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String country = getAsStringNotNull(dataFormatter, row, 23);
        irs.setCountry(country);
    }

    private void getCategory(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String category = getAsStringNotNull(dataFormatter, row, 22);
        IrsCategory irsCategory = IrsCategory.valueOf(category);
        irs.setCategory(irsCategory);
    }

    private void getType(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String type = getAsStringNotNull(dataFormatter, row, 21);
        irs.setType(type);
    }

    private void getActivity(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String activity = getAsStringNotNull(dataFormatter, row, 20);
        irs.setActivity(activity);
    }

    private void getSerialNumber(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String serialNumber = getAsStringNotNull(dataFormatter, row, 19);
        irs.setSerialNumber(serialNumber);
    }

    private void getFactoryNumber(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String factoryNumber = getAsStringNotNull(dataFormatter, row, 18);
        irs.setFactoryNumber(factoryNumber);
    }

    private void getSphere(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String sphere = getAsStringNotNull(dataFormatter, row, 17);
        irs.setSphere(sphere);
    }

    private void getSymbol(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String symbol = getAsStringNotNull(dataFormatter, row, 16);
        irs.setSymbol(symbol);
    }

    private void getIdentifierType(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
       String text = getAsStringNotNull(dataFormatter, row, 15);
        IrsIdentifierType irsIdentifierType = IrsIdentifierType.valueOf(text);
        irs.setIdentifierType(irsIdentifierType);
    }

    private void getDivision(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String division = getAsStringNotNull(dataFormatter, row, 14);
        irs.setDivision(division);
    }

    private void getSupervisor(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String name = getAsStringNotNull(dataFormatter, row, 9);
        String position = getAsStringNotNull(dataFormatter, row, 10);
        String status = getAsStringNotNull(dataFormatter, row, 11);
        String education = getAsStringNotNull(dataFormatter, row, 12);
        String phoneNumber = getAsStringNotNull(dataFormatter, row, 13);

        irs.setSupervisorName(name);
        irs.setSupervisorPosition(position);
        irs.setSupervisorStatus(status);
        irs.setSupervisorEducation(education);
        irs.setSupervisorPhoneNumber(phoneNumber);
    }

    private void getAddress(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String address = getAsStringNotNull(dataFormatter, row, 8);
        irs.setAddress(address);
    }

    private void getRegionAndDistrict(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String soato = getAsStringNotNull(dataFormatter, row, 7);
        District district = districtService.findBySoato(Integer.valueOf(soato));
        irs.setDistrictId(district.getId());
        irs.setRegionId(district.getRegionId());
    }

    private void getLegal(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String legalTin = getAsStringNotNull(dataFormatter, row, 2);
        boolean isExist = profileService.existsProfileByTin(Long.parseLong(legalTin));
        if (!isExist) {
            LegalUserDto legalDto = iipService.getGnkInfo(legalTin);
            userService.create(legalDto);
        }
        ProfileInfoView profileInfo = profileService.getProfileInfo(Long.parseLong(legalTin));
        irs.setProfileId(profileInfo.getId());
        irs.setLegalTin(profileInfo.getTin());
        irs.setLegalName(profileInfo.getLegalName());
        irs.setLegalAddress(profileInfo.getLegalAddress());
    }

    private void getParent(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String parent = getAsStringNotNull(dataFormatter, row, 3);
        irs.setParentOrganization(parent);
    }

    private void getOrderNumber(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String orderNumber = getAsStringNotNull(dataFormatter, row, 34);
        irs.setOrderNumber(Long.parseLong(orderNumber));
    }

    private String getRegistryNumber(DataFormatter dataFormatter, Row row, IonizingRadiationSource irs) throws Exception {
        String orderNumber = getAsStringNotNull(dataFormatter, row, 34);
        String registryNumber ="INM" + String.format("%05d", Integer.parseInt(orderNumber.trim()));
        irs.setRegistryNumber(registryNumber);
        return registryNumber;
    }

    private String getAsStringNotNull(DataFormatter dataFormatter, Row row, int rowIndex) throws Exception {

        String text = dataFormatter.formatCellValue(row.getCell(rowIndex));
        if (text == null || text.isBlank()) {
            throw new Exception("division bo'sh bo'lishi mumkin emas");
        }
        return text;
    }

    private LocalDate getLocalDate(Cell cell, String fieldName) throws Exception {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            throw new Exception(fieldName + " bo'sh bo'lishi mumkin emas");
        }
        LocalDate manufacturedAt;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            manufacturedAt = cell.getLocalDateTimeCellValue().toLocalDate();
        } else if (cell.getCellType() == CellType.STRING) {
            String dateStr = cell.getStringCellValue();
            manufacturedAt = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } else {
            throw new Exception(fieldName + " format yacheykasi date bo'lishi kerak");
        }
        return manufacturedAt;
    }


}
