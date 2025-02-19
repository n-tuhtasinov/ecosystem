package uz.technocorp.ecosystem.modules.document.dto;

import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 17.02.2025
 * @since v1.0
 */
public record DocumentDto(Integer documentTypeId, UUID appealId, String path) {

}
