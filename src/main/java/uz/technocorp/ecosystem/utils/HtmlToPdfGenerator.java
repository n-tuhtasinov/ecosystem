package uz.technocorp.ecosystem.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * @author Sukhrob
 * @version 1.0
 * @created 21.04.2025
 * @since v1.0
 */
@Service
public class HtmlToPdfGenerator {

    private final TemplateEngine templateEngine;

    public HtmlToPdfGenerator() {
        this.templateEngine = new TemplateEngine();
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    public byte[] generatePdfWithParam(String htmlContent, Map<String, Object> parameters) {
        // Replacing placeholders in template content
        String processedHtml = processTemplate(htmlContent, parameters);

        return convertHtmlToPdf(processedHtml);
    }

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
            throw new ResourceNotFoundException("PDF yaratishda xatolik : " + e.getMessage());
        }
    }

    private String processTemplate(String htmlContent, Map<String, Object> parameters) {
        try {
            Context context = new Context();

            // Add all params to context
            for (Map.Entry<String, Object> param : parameters.entrySet()) {
                context.setVariable(param.getKey(), param.getValue());
            }
            // Process and change params
            return templateEngine.process(htmlContent, context);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("Parametrlarni almashtirishda xatolik : " + e.getMessage());
        }
    }

}
