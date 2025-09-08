package uz.technocorp.ecosystem.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.CustomException;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author Suxrob
 * @version 1.0
 * @created 05.09.2025
 * @since v1.0
 */
@Service
public class GenericExcelExporter {
    public <T> void exportToExcel(
            HttpServletResponse response,
            String fileNamePrefix,
            List<String> headers,
            List<T> data,
            List<Function<T, Object>> valueMappers
    ) {
        configureResponse(response, fileNamePrefix);

        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100)) {
            Sheet sheet = workbook.createSheet(fileNamePrefix);

            // 1. Sarlavha uchun shrift (Font) yaratamiz
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);

            // 2. Sarlavha uchun stil (CellStyle) yaratamiz va shriftni unga bog'laymiz
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Sarlavha qatorini yaratish
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));

                // 3. Yaratilgan stilni har bir sarlavha yacheykasiga qo'llaymiz (YANGI QO'SHILDI)
                cell.setCellStyle(headerStyle);
            }

            // Ma'lumotlar qatorini to'ldirish (BU QISM O'ZGARIShSIZ QOLADI)
            int rowNum = 1;
            for (T item : data) {
                Row dataRow = sheet.createRow(rowNum++);
                for (int i = 0; i < valueMappers.size(); i++) {
                    Object value = valueMappers.get(i).apply(item);
                    Cell cell = dataRow.createCell(i);

                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else if (value != null) {
                        cell.setCellValue(value.toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            // Ustun kengligini avtomatik sozlash (agar kerak bo'lsa va XSSFWorkbook ishlatilsa)
            // Biz SXSSFWorkbook ishlatayotganimiz uchun bu qismni qo'shmaymiz.

            // Response'ga yozish
            try (OutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new CustomException("Excel faylini eksport qilishda xatolik: " + e.getMessage());
        }
    }

    private void configureResponse(HttpServletResponse response, String fileNamePrefix) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileNamePrefix + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
    }
}
