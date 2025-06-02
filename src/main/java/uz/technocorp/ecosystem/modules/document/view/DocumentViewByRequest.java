package uz.technocorp.ecosystem.modules.document.view;

import org.springframework.beans.factory.annotation.Value;
import uz.technocorp.ecosystem.modules.document.dto.Signer;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 18.02.2025
 * @since v1.0
 */
public interface DocumentViewByRequest {

    LocalDate getCreatedAt();
    UUID getDocumentId();
    String getDocumentType();
    Boolean getIsSigned();
    String getPath();
    @Value("#{@documentServiceImpl.convertToList(target.signers)}")
    List<Signer> getSigners();
}
