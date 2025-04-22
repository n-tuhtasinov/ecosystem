package uz.technocorp.ecosystem.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;

import java.io.ByteArrayOutputStream;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 21.04.2025
 * @since v1.0
 */
@Service
public class HtmlToPdfGenerator {

    public byte[] convertHtmlToPdf(String htmlContent) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(PageSize.A4);

            ConverterProperties converterProperties = new ConverterProperties();

            // Create PDF from HTML
            HtmlConverter.convertToPdf(htmlContent, pdf, converterProperties);

            pdf.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ResourceNotFoundException("HTML dan PDF ga o'girishda xatolik : " + e.getMessage());
        }
    }
}
