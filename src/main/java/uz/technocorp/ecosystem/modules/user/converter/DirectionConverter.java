package uz.technocorp.ecosystem.modules.user.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 29.01.2025
 * @since v1.0
 * @description convert the list of String to String while saving and reverse while getting
 */
@Converter(autoApply = true)
public class DirectionConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> directions) {
        if (directions == null || directions.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < directions.size(); i++) {
            builder.append(directions.get(i));
            if (i != directions.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return List.of();
        }
        return List.of(s.split(","));
    }
}
