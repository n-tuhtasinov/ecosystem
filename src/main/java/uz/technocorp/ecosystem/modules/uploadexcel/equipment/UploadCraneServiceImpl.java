package uz.technocorp.ecosystem.modules.uploadexcel.equipment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.technocorp.ecosystem.exceptions.ExcelParsingException;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipment;
import uz.technocorp.ecosystem.modules.childequipment.ChildEquipmentService;
import uz.technocorp.ecosystem.modules.district.District;
import uz.technocorp.ecosystem.modules.district.DistrictService;
import uz.technocorp.ecosystem.modules.equipment.Equipment;
import uz.technocorp.ecosystem.modules.equipment.EquipmentRepository;
import uz.technocorp.ecosystem.modules.equipment.EquipmentService;
import uz.technocorp.ecosystem.modules.equipment.enums.EquipmentType;
import uz.technocorp.ecosystem.modules.hf.HazardousFacility;
import uz.technocorp.ecosystem.modules.hf.HazardousFacilityService;
import uz.technocorp.ecosystem.modules.integration.iip.IIPService;
import uz.technocorp.ecosystem.modules.profile.ProfileService;
import uz.technocorp.ecosystem.modules.profile.projection.ProfileInfoView;
import uz.technocorp.ecosystem.modules.region.Region;
import uz.technocorp.ecosystem.modules.region.RegionService;
import uz.technocorp.ecosystem.modules.user.UserService;
import uz.technocorp.ecosystem.modules.user.dto.LegalUserDto;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 21.06.2025
 * @since v1.0
 */
@Service("crane")
@RequiredArgsConstructor
@Slf4j
public class UploadCraneServiceImpl implements UploadEquipmentExcelService {

    private final IIPService iipService;
    private final ProfileService profileService;
    private final UserService userService;
    private final DistrictService districtService;
    private final RegionService regionService;
    private final HazardousFacilityService hazardousFacilityService;
    private final ChildEquipmentService childEquipmentService;
    private final EquipmentService equipmentService;
    private final EquipmentRepository equipmentRepository;


    @Transactional(rollbackFor = ExcelParsingException.class)
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

                try {
                    Equipment equipment = new Equipment();
                    getLegal(dataFormatter, row, equipment); // b) legalTin
                    getHf(dataFormatter, row, equipment); // b) hfRegistryNumber
                    District district = getDistrict(dataFormatter, row, equipment); // g) districtSoato
                    getRegionAndAddress(dataFormatter, row, district, equipment); // h) address
                    getChildEquipment(dataFormatter, row, equipment); // k) child equipment
                    getFactoryNumber(dataFormatter, row, equipment); // l) factoryNumber
                    getRegistryNumber(dataFormatter, row, equipment); // m) registry number
                    getOldEquipment(dataFormatter, row, equipment); // n) old equipment
                    getManufacturedAt(row, equipment); // q) manufacturedAt
                    getPartialCheckDate(row, equipment); // s) partialCheckDate
                    getFullCheckDate(row, equipment); // t) full check date
                    getRegistrationDate(row, equipment); // w) registration date
                    getInspectorName(dataFormatter, row, equipment); // x) inspectorName
                    getIsActive(dataFormatter, row, equipment); // z) is active

                    getParams(dataFormatter, row, equipment); // u) params
                    setFiles(equipment); // set files

                    equipmentRepository.save(equipment);
                } catch (Exception e) {
                    log.error("Xatolik! Excel faylning {}-qatorida ma'lumotlarni o'qishda muammo yuzaga keldi. Tafsilotlar: {}", excelRowNumber, e.getMessage());
                    throw new ExcelParsingException("Excel faylni o'qishda xatolik", excelRowNumber, e.getMessage(), e);
                }
            }
        } catch (ExcelParsingException e) {
            throw e; // to rollback transaction
        } catch (Exception e) {
            log.error("Excel faylni qayta ishlashda kutilmagan xatolik: {}", e.getMessage());
            throw new RuntimeException("Excel faylni qayta ishlashda kutilmagan xatolik: " + e.getMessage(), e);
        }
    }

    private void setFiles(Equipment equipment) {
        Map<String, String> files = new HashMap<>();
        files.put("boomLength", null);
        files.put("liftingCapacity", null);
        equipment.setFiles(files);
    }

    private void getParams(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String boomLength = dataFormatter.formatCellValue(row.getCell(20));
        isValid(boomLength, "strellasining uzunligi(u)");

        String liftingCapacity = dataFormatter.formatCellValue(row.getCell(21));
        isValid(liftingCapacity, "yuk ko'tar olishi(v)");

        Map<String, String> params = Map.of("boomLength", boomLength, "liftingCapacity", liftingCapacity);
        equipment.setParameters(params);
    }

    private void getRegistrationDate(Row row, Equipment equipment) throws Exception {
        Cell cell = row.getCell(22);
        isValidDate(cell, "ro'yhatga olingan sanasi(w)");
        LocalDate registrationDate = cell.getLocalDateTimeCellValue().toLocalDate();
        equipment.setRegistrationDate(registrationDate);
    }

    private void getIsActive(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String isActive = dataFormatter.formatCellValue(row.getCell(25));
        isValid(isActive, "holati(z)");
        equipment.setIsActive(Boolean.parseBoolean(isActive));
    }

    private void getInspectorName(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String inspectorName = dataFormatter.formatCellValue(row.getCell(23));
        isValid(inspectorName, "inspektor FIO(x)");
        equipment.setInspectorName(inspectorName);
    }

    private void getFullCheckDate(Row row, Equipment equipment) throws Exception {
        Cell cell = row.getCell(19);
        isValidDate(cell, "To'liq texnk ko'rik sanasi(t)");
        LocalDate fullCheckDate = cell.getLocalDateTimeCellValue().toLocalDate();
        equipment.setPartialCheckDate(fullCheckDate);
    }

    private void getPartialCheckDate(Row row, Equipment equipment) throws Exception {
        Cell cell = row.getCell(18);
        isValidDate(cell, "qisman ko'rik sanasi(s)");
        LocalDate partialCheckDate = cell.getLocalDateTimeCellValue().toLocalDate();
        equipment.setPartialCheckDate(partialCheckDate);
    }

    private void getManufacturedAt(Row row, Equipment equipment) throws Exception {
        Cell cell = row.getCell(16);
        isValidDate(cell, "manufacturedAt(q)");
        LocalDate manufacturedAt = cell.getLocalDateTimeCellValue().toLocalDate();
        equipment.setManufacturedAt(manufacturedAt);
    }

    private void getOldEquipment(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String oldEquipmentRegistryNumber = dataFormatter.formatCellValue(row.getCell(12));
        isValid(oldEquipmentRegistryNumber, "oldEquipmentRegistryNumber(n)");
        Equipment oldEquipment = equipmentService.findByRegistryNumber(oldEquipmentRegistryNumber);
        equipment.setOldEquipmentId(oldEquipment.getId());
    }

    private void getRegistryNumber(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String registryNumber = dataFormatter.formatCellValue(row.getCell(12));
        isValid(registryNumber, "registryNumber(m)");
        equipment.setRegistryNumber(registryNumber);
    }

    private void getFactoryNumber(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String factoryNumber = dataFormatter.formatCellValue(row.getCell(11));
        isValid(factoryNumber, "factoryNumber(l)");
        equipment.setFactoryNumber(factoryNumber);
    }

    private void getChildEquipment(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String childEquipmentName = dataFormatter.formatCellValue(row.getCell(10));
        isValid(childEquipmentName, "childEquipmentName(k)");
        ChildEquipment childEquipment = childEquipmentService.findByNameAndEquipmentType(childEquipmentName, EquipmentType.CRANE);
        equipment.setChildEquipmentId(childEquipment.getId());
    }

    private void getRegionAndAddress(DataFormatter dataFormatter, Row row, District district, Equipment equipment) throws Exception {
        String addressExcel = dataFormatter.formatCellValue(row.getCell(9));
        isValid(addressExcel, "address(j)");
        Region region = regionService.findById(district.getRegionId());
        String fullAddress = String.format("%s, %s, %s",
                region.getName(),
                district.getName(),
                addressExcel);
        equipment.setAddress(fullAddress);
    }

    private District getDistrict(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String soato = dataFormatter.formatCellValue(row.getCell(8));
        isValid(soato, "districtSoato(i)");
        District district = districtService.findBySoato(Integer.valueOf(soato));
        equipment.setDistrictId(district.getId());
        equipment.setRegionId(district.getRegionId());
        return district;
    }

    private void getHf(DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String hfRegistryNumber = dataFormatter.formatCellValue(row.getCell(4));
        if (hfRegistryNumber != null && !hfRegistryNumber.isBlank()) {
            HazardousFacility hf = hazardousFacilityService.findByRegistryNumber(hfRegistryNumber);
            equipment.setHazardousFacilityId(hf.getId());
        }
    }

    private void getLegal (DataFormatter dataFormatter, Row row, Equipment equipment) throws Exception {
        String legalTin = dataFormatter.formatCellValue(row.getCell(1));
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
            throw new Exception(fieldName +" bo'sh bo'lishi mumkin emas");
        }
    }

    private static void isValidDate(Cell cell, String fieldName) throws Exception {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            throw new Exception(fieldName + " bo'sh bo'lishi mumkin emas");
        }
        if (cell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(cell)) {
            throw new Exception(fieldName + " format yacheykasi date bo'lishi kerak");
        }
    }


}

