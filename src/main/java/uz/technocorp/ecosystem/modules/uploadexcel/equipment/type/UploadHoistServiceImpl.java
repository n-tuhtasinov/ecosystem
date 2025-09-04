package uz.technocorp.ecosystem.modules.uploadexcel.equipment.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.exceptions.CustomException;
import uz.technocorp.ecosystem.modules.appeal.Appeal;
import uz.technocorp.ecosystem.modules.appeal.enums.OwnerType;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipment;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentRepository;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentInfoDto;
import uz.technocorp.ecosystem.modules.equipment.dto.EquipmentRegistryDto;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.integration.iip.IIPService;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.enums.ProfileType;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;
import uz.technocorp.ecosystem.modules.uploadexcel.equipment.UploadEquipmentExcelService;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.dto.IndividualUserDto;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;
import uz.technocorp.ecosystem.shared.dto.FileDto;
import uz.technocorp.ecosystem.shared.enums.RegistrationMode;

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
@Service("HOIST")
@RequiredArgsConstructor
@Slf4j
public class UploadHoistServiceImpl implements UploadEquipmentExcelService {

    private final IIPService iipService;
    private final ProfileService profileService;
    private final UserService userService;
    private final DistrictService districtService;
    private final ChildEquipmentService childEquipmentService;
    private final EquipmentService equipmentService;
    private final EquipmentRepository equipmentRepository;
    private final ObjectMapper objectMapper;

    private static final String DATE_FORMAT = "dd.MM.yyyy";


    @Override
    public void upload(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalStateException("Fayl bo'sh bo'lishi mumkin emas");
        }

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(3);                                   //TODO: Shu joyga qarash kerak
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

                    String identityLatter = "V";                                    //TODO: Shu joyga qarash kerak
                    EquipmentType equipmentType = EquipmentType.HOIST;              //TODO: Shu joyga qarash kerak

                    registryNumber = getRegistryNumber(dataFormatter, row, equipment, 14); // m) registry number
                    getLegalOrIndividual(dataFormatter, row, equipment, 2, 4, 5); // b) legalTin
                    getDistrict(dataFormatter, row, equipment, 10); // g) districtSoato
                    getAddress(dataFormatter, row, equipment, 11); // h) address
                    String childEquipmentName = getChildEquipment(dataFormatter, row, equipment, equipmentType, 12);// k) child equipment
                    getFactoryNumber(dataFormatter, row, equipment, 13); // l) factoryNumber
                    getOldRegistryNumber(dataFormatter, row, equipment, identityLatter, 15);
                    getFactory(dataFormatter, row, equipment, 16);
                    getModel(dataFormatter, row, equipment, 17);
                    getManufacturedAt(row, equipment, 18); // q) manufacturedAt
                    getPartialCheckDate(row, equipment, 20); // s) partialCheckDate
                    getFullCheckDate(row, equipment, 21); // t) full check date
                    getRegistrationDate(row, equipment, 24); // w) registration date
                    getInspectorName(dataFormatter, row, equipment, 25); // x) inspectorName
                    getIsActive(dataFormatter, row, equipment, 27); // z) is active
                    getParams(dataFormatter, row, equipment); // u) params              //TODO: Shu joyga qarash kerak
                    setFiles(equipment); // set files
                    equipment.setType(equipmentType); // set equipment type
                    equipment.setMode(RegistrationMode.OFFICIAL);

                    // create registry file
                    EquipmentInfoDto info = new EquipmentInfoDto(equipmentType, equipment.getRegistryNumber(), null);
                    ObjectNode objectNode = objectMapper.createObjectNode();
                    objectNode.put("childEquipmentName", childEquipmentName);
                    Appeal appeal = Appeal.builder()
                            .ownerAddress(equipment.getOwnerAddress())
                            .data(objectNode)
                            .ownerIdentity(equipment.getOwnerIdentity())
                            .ownerName(equipment.getOwnerName())
                            .address(equipment.getAddress())
                            .mode(RegistrationMode.OFFICIAL)
                            .build();

                    EquipmentRegistryDto registryDto = new EquipmentRegistryDto();
                    registryDto.setType(info.equipmentType());
                    registryDto.setRegistryNumber(info.registryNumber());
                    registryDto.setRegistrationDate(equipment.getRegistrationDate());
                    registryDto.setManufacturedAt(equipment.getManufacturedAt());
                    registryDto.setFactory(equipment.getFactory());
                    registryDto.setFactoryNumber(equipment.getFactoryNumber());
                    registryDto.setModel(equipment.getModel());
                    registryDto.setParameters(equipment.getParameters());
                    registryDto.setAttractionName(equipment.getAttractionName());
                    registryDto.setRiskLevel(equipment.getRiskLevel());

                    String registryPdfPath = equipmentService.createEquipmentRegistryPdf(appeal, registryDto);

                    equipment.setRegistryFilePath(registryPdfPath);
                    equipmentRepository.save(equipment);
                } catch (Exception e) {
                    log.error("Xatolik! Excel faylning {}-qatoridagi {} sonli ro'yhat raqamli ma'lumotlarni o'qishda muammo yuzaga keldi. Tafsilotlar: {}", excelRowNumber, registryNumber, e.getMessage());
                }
            }
            log.info("Fayl muvaffaqiyatli o'qildi. {} qator ma'lumot o'qildi.", lastRowNum + 1);
        } catch (Exception e) {
            log.error("Excel faylni qayta ishlashda kutilmagan xatolik: {}", e.getMessage());
        }
    }

    private static void setFiles(Equipment equipment) {
        Map<String, FileDto> files = new HashMap<>();
        files.put("labelPath", new FileDto());
        files.put("saleContractPath", new FileDto());
        files.put("equipmentCertPath", new FileDto());
        files.put("assignmentDecreePath", new FileDto());
        files.put("expertisePath", new FileDto());
        files.put("installationCertPath", new FileDto());
        files.put("additionalFilePath", new FileDto());
        equipment.setFiles(files);
    }

    private void getParams(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        Map<String, String> params = new HashMap<>();

        String height = dataFormatter.formatCellValue(row.getCell(22));
        isValid(height, "Ko'tarish balandligi(w)");
        params.put("height", height);

        String liftingCapacity = dataFormatter.formatCellValue(row.getCell(23));
        isValid(liftingCapacity, "Yuk ko'tarish quvvati(w)");
        params.put("liftingCapacity", liftingCapacity);

        equipment.setParameters(params);
    }

    private void getModel(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String model = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(model, "model(p)");
        equipment.setModel(model);
    }

    private void getOldRegistryNumber(DataFormatter dataFormatter, Row row, Equipment equipment, String identityLatter, int cellIndex) {
        String oldRegNumber = dataFormatter.formatCellValue(row.getCell(cellIndex));
        if (oldRegNumber != null && !oldRegNumber.isEmpty()) {
            equipment.setOldRegistryNumber(identityLatter + oldRegNumber);
        }
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
        equipment.setFullCheckDate(fullCheckDate);
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

    private void getAddress(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String addressExcel = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(addressExcel, "address(j)");
//        Region region = regionService.findById(district.getRegionId());
//        String fullAddress = String.format("%s, %s, %s",
//                region.getName(),
//                district.getName(),
//                addressExcel);
        equipment.setAddress(addressExcel);
    }

    private void getDistrict(DataFormatter dataFormatter, Row row, Equipment equipment, int cellIndex) throws Exception {
        String soato = dataFormatter.formatCellValue(row.getCell(cellIndex));
        isValid(soato, "districtSoato(i)");
        District district = districtService.findBySoato(Integer.valueOf(soato));
        equipment.setDistrictId(district.getId());
        equipment.setRegionId(district.getRegionId());
    }


    private void getLegalOrIndividual(DataFormatter dataFormatter, Row row, Equipment equipment, int legalCellIndex, int individualCellIndex, int birthCellIndex) throws Exception {
        String identity = dataFormatter.formatCellValue(row.getCell(legalCellIndex));
        if (identity == null || identity.isBlank()) {
            identity = dataFormatter.formatCellValue(row.getCell(individualCellIndex));
            isValid(identity, "Ham STIR ham JSHSHIR");

            try {
                Profile profile = profileService.findByIdentity(Long.valueOf(identity));
                if (ProfileType.EMPLOYEE.equals(profile.getType())) {
                    throw new RuntimeException("JSHSHIRi "+identity+" bo'lgan ichki hodim tizimda mavjud");
                }
            } catch (CustomException e) {
                Cell birthCell = row.getCell(birthCellIndex);
                LocalDate birthDate = getLocalDate(birthCell, "Qurilma egasi tug'ilgan sanasi");
                IndividualUserDto pinInfo = iipService.getPinInfo(identity, birthDate);
                userService.create(pinInfo);
            }
            equipment.setOwnerType(OwnerType.INDIVIDUAL);
        }else {
            boolean isExist = profileService.existsProfileByTin(Long.parseLong(identity));
            if (!isExist) {
                LegalUserDto legalDto = iipService.getGnkInfo(identity);
                userService.create(legalDto);
            }
            equipment.setOwnerType(OwnerType.LEGAL);
        }

        ProfileInfoView profileInfo = profileService.getProfileInfo(Long.parseLong(identity));
        equipment.setOwnerIdentity(profileInfo.getTin());
        equipment.setOwnerName(profileInfo.getLegalName());
        equipment.setOwnerAddress(profileInfo.getLegalAddress());
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

