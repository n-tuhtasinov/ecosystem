package uz.technocorp.ecosystem.modules.uploadexcel.equipment.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipment;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentRepository;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.integration.iip.IIPService;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;
import uz.technocorp.ecosystem.modules.uploadexcel.equipment.UploadEquipmentExcelService;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 21.06.2025
 * @since v1.0
 */
@Service("CRANE")
@RequiredArgsConstructor
@Slf4j
public class UploadCraneServiceImpl implements UploadEquipmentExcelService {

    private final IIPService iipService;
    private final ProfileService profileService;
    private final UserService userService;
    private final DistrictService districtService;
    private final HazardousFacilityService hazardousFacilityService;
    private final ChildEquipmentService childEquipmentService;
    private final EquipmentService equipmentService;
    private final EquipmentRepository equipmentRepository;
    private final ObjectMapper objectMapper;

    private static final String DATE_FORMAT = "dd.MM.yyyy";


    //    @Transactional(rollbackFor = ExcelParsingException.class)
    @Override
    public void upload(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalStateException("Fayl bo'sh bo'lishi mumkin emas");
        }

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);                                   //TODO: Shu joyga qarash kerak
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
                    Equipment equipment = new Equipment();

                    String identityLatter = "P";                                    //TODO: Shu joyga qarash kerak
                    EquipmentType equipmentType = EquipmentType.CRANE;              //TODO: Shu joyga qarash kerak

                    registryNumber = getRegistryNumber(dataFormatter, row, equipment, 13); // m) registry number
                    getLegal(dataFormatter, row, equipment, 2); // b) legalTin
//                    getHf(dataFormatter, row, equipment, 5); // b) hfRegistryNumber
                    District district = getDistrict(dataFormatter, row, equipment, 9); // g) districtSoato
                    getRegionAndAddress(dataFormatter, row, district, equipment, 10); // h) address
                    String childEquipmentName = getChildEquipment(dataFormatter, row, equipment, equipmentType, 11);// k) child equipment
                    getFactoryNumber(dataFormatter, row, equipment, 12); // l) factoryNumber
                    getOldEquipment(dataFormatter, row, equipment, identityLatter, 14); // n) old equipment
                    getFactory(dataFormatter, row, equipment, 15);
                    getModel(dataFormatter, row, equipment, 16);
                    getManufacturedAt(row, equipment, 17); // q) manufacturedAt
                    getPartialCheckDate(row, equipment, 19); // s) partialCheckDate
                    getFullCheckDate(row, equipment, 20); // t) full check date
                    getRegistrationDate(row, equipment, 23); // w) registration date
                    getInspectorName(dataFormatter, row, equipment, 24); // x) inspectorName
                    getIsActive(dataFormatter, row, equipment, 26); // z) is active
                    getParams(dataFormatter, row, equipment); // u) params              //TODO: Shu joyga qarash kerak
                    setFiles(equipment); // set files
                    equipment.setType(equipmentType); // set equipment type

                    // create registry file
                    EquipmentInfoDto info = new EquipmentInfoDto(equipmentType, equipment.getRegistryNumber(), null);
                    ObjectNode objectNode = objectMapper.createObjectNode();
                    objectNode.put("childEquipmentName", childEquipmentName);
                    Appeal appeal = Appeal.builder()
                            .legalAddress(equipment.getLegalAddress())
                            .data(objectNode)
                            .legalTin(equipment.getLegalTin())
                            .legalName(equipment.getLegalName())
                            .address(equipment.getAddress())
                            .build();
                    EquipmentDto dto = new EquipmentDto(
                            null, null, null, equipment.getFactoryNumber(), equipment.getModel(), equipment.getFactory(), null, equipment.getManufacturedAt(), null,
                            null, null, equipment.getParameters(), null, null, null, null, null, null,
                            null, null, null, null, null, null, null);
                    String registryPdfPath = equipmentService.createEquipmentRegistryPdf(appeal, dto, info, equipment.getRegistrationDate());

                    equipment.setRegistryFilePath(registryPdfPath);
                    equipmentRepository.save(equipment);
                } catch (Exception e) {
                    log.error("Xatolik! Excel faylning {}-qatoridagi {} sonli ro'yhat raqamli ma'lumotlarni o'qishda muammo yuzaga keldi. Tafsilotlar: {}", excelRowNumber, registryNumber, e.getMessage());
//                    throw new ExcelParsingException("Excel faylni o'qishda xatolik", excelRowNumber, e.getMessage(), e);
                }
            }
            log.info("Fayl muvaffaqiyatli o'qildi. {} qator ma'lumot o'qildi.", lastRowNum + 1);
//        } catch (ExcelParsingException e) {
//            throw e; // to rollback transaction
        } catch (Exception e) {
            log.error("Excel faylni qayta ishlashda kutilmagan xatolik: {}", e.getMessage());
//            throw new RuntimeException("Excel faylni qayta ishlashda kutilmagan xatolik: " + e.getMessage(), e);
        }
    }

    private static void setFiles(Equipment equipment) {
        Map<String, String> files = new HashMap<>();
        files.put("labelPath", null);
        files.put("saleContractPath", null);
        files.put("equipmentCertPath", null);
        files.put("assignmentDecreePath", null);
        files.put("expertisePath", null);
        files.put("installationCertPath", null);
        files.put("additionalFilePath", null);
        equipment.setFiles(files);
    }

    private void getParams(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String boomLength = dataFormatter.formatCellValue(row.getCell(21));
        isValid(boomLength, "strellasining uzunligi(u)");

        String liftingCapacity = dataFormatter.formatCellValue(row.getCell(22));
        isValid(liftingCapacity, "yuk ko'tar olishi(v)");

        Map<String, String> params = Map.of("boomLength", boomLength, "liftingCapacity", liftingCapacity);
        equipment.setParameters(params);
    }

    private void getModel(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String model = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(model, "model(p)");
        equipment.setModel(model);
    }

    private void getFactory(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String factory = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(factory, "factory(o)");
        equipment.setFactory(factory);
    }

    private void getRegistrationDate(Row row, Equipment equipment, int cellIndex) throws Exception {
        Cell cell = row.getCell(cellIndex);
        LocalDate registrationDate = getLocalDate(cell, "ro'yhatga olingan sanasi(w)");
        equipment.setRegistrationDate(registrationDate);
    }

    private void getIsActive(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String isActive = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(isActive, "holati(z)");
        equipment.setIsActive(Boolean.parseBoolean(isActive));
    }

    private void getInspectorName(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String inspectorName = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(inspectorName, "inspektor FIO(x)");
        equipment.setInspectorName(inspectorName);
    }

    private void getFullCheckDate(Row row, Equipment equipment, int cellIndex) throws Exception {
        Cell cell = row.getCell(cellIndex);
        LocalDate fullCheckDate = getLocalDate(cell, "To'liq texnk ko'rik sanasi(t)");
        equipment.setPartialCheckDate(fullCheckDate);
    }

    private void getPartialCheckDate(Row row, Equipment equipment, int cellIndex) throws Exception {
        Cell cell = row.getCell(cellIndex);
        LocalDate partialCheckDate = getLocalDate(cell, "qisman ko'rik sanasi(s)");
        equipment.setPartialCheckDate(partialCheckDate);
    }

    private void getManufacturedAt(Row row, Equipment equipment, int cellIndex) throws Exception {
        Cell cell = row.getCell(cellIndex);
        LocalDate manufacturedAt = getLocalDate(cell, "manufacturedAt(q)");
        equipment.setManufacturedAt(manufacturedAt);
    }

    private void getOldEquipment(DataFormatter dataFormatter, Row row, Equipment equipment, String identityLetter, int cellIndex) throws Exception {
        String oldEquipmentRegistryNumber = dataFormatter.formatCellValue(row.getCell(cellIndex));
        if (oldEquipmentRegistryNumber != null && !oldEquipmentRegistryNumber.isBlank()) {
            Equipment oldEquipment = equipmentService.findByRegistryNumber(identityLetter + oldEquipmentRegistryNumber);
            equipment.setOldEquipmentId(oldEquipment.getId());
        }
    }

    private String getRegistryNumber(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String registryNumber = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(registryNumber, "registryNumber(m)");
        equipment.setRegistryNumber(registryNumber);
        return registryNumber;
    }

    private void getFactoryNumber(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String factoryNumber = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(factoryNumber, "factoryNumber(l)");
        equipment.setFactoryNumber(factoryNumber);
    }

    private String getChildEquipment(DataFormatter dataFormatter, Row row, Equipment equipment, EquipmentType equipmentType, int cellIndex) throws Exception {
        String childEquipmentName = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(childEquipmentName, "childEquipmentName(k)");
        ChildEquipment childEquipment = childEquipmentService.findByNameAndEquipmentType(childEquipmentName, equipmentType);
        equipment.setChildEquipmentId(childEquipment.getId());
        return childEquipmentName;
    }

    private void getRegionAndAddress(DataFormatter dataFormatter, Row row, District district, Equipment equipment, int cellIndex) throws Exception {
        String addressExcel = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(addressExcel, "address(j)");
//        Region region = regionService.findById(district.getRegionId());
//        String fullAddress = String.format("%s, %s, %s",
//                region.getName(),
//                district.getName(),
//                addressExcel);
        equipment.setAddress(addressExcel);
    }

    private District getDistrict(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String soato = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(soato, "districtSoato(i)");
        District district = districtService.findBySoato(Integer.valueOf(soato));
        equipment.setDistrictId(district.getId());
        equipment.setRegionId(district.getRegionId());
        return district;
    }

    private void getHf(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String hfRegistryNumber = dataFormatter.formatCellValue(row.getCell(cellIndex));
        if (hfRegistryNumber != null && !hfRegistryNumber.isBlank()) {
            HazardousFacility hf = hazardousFacilityService.findByRegistryNumber(hfRegistryNumber);
            equipment.setHazardousFacilityId(hf.getId());
        }
    }

    private void getLegal(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String legalTin = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(legalTin, "legalTin(b)");

        boolean isExist = profileService.existsProfileByTin(Long.parseLong(legalTin));
        if (!isExist) {
            LegalUserDto legalDto = iipService.getGnkInfo(legalTin);
            userService.create(legalDto);
        }

        ProfileInfoView profileInfo = profileService.getProfileInfo(Long.parseLong(legalTin));
        equipment.setLegalTin(profileInfo.getTin());
        equipment.setLegalName(profileInfo.getLegalName());
        equipment.setLegalAddress(profileInfo.getLegalAddress());
    }

    private static void isValid(String fieldValue, String fieldName) throws Exception {
        if (fieldValue == null || fieldValue.isBlank()) {
            throw new Exception(fieldName + " bo'sh bo'lishi mumkin emas");
        }
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

