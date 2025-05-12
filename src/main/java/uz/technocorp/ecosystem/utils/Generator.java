package uz.technocorp.ecosystem.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 21.04.2025
 * @since v1.0
 */
@Service
public class Generator {

    public byte[] convertHtmlToPdf(String htmlContent) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(PageSize.A4);

            ConverterProperties converterProperties = new ConverterProperties();

            // Create PDF from HTML
            HtmlConverter.convertToPdf(htmlContent, pdf, converterProperties);

            pdf.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error during convert HTML to PDF : " + e.getMessage());
        }
    }

    public String generateQRCodeBase64(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            throw new RuntimeException("QR code yaratishda xato: " + e.getMessage(), e);
        }
    }
}
