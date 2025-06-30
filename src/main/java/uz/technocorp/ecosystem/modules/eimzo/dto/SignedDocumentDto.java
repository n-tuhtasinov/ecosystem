package uz.technocorp.ecosystem.modules.eimzo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Suxrob
 * @version 1.0
 * @created 30.06.2025
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignedDocumentDto {

    @NotBlank(message = "DocId jo'natilmadi")
    private String documentId;


    @NotBlank(message = "Imzolangan ma'lumot jo'natilmadi")
    private String document;
}
