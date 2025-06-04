package uz.technocorp.ecosystem.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import uz.technocorp.ecosystem.shared.SkipDb;

import java.lang.reflect.Field;
import java.util.*;

@UtilityClass
public class JsonUtils {

    private final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public JsonNode makeJsonSkipFields(Object dto) {
        Map<String, Object> result = new HashMap<>();

        // get all fields both in child and parent class
        List<Field> allFields = getAllFields(dto.getClass());

        for (Field field : allFields) {
            try {
                field.setAccessible(true);

                if (!field.isAnnotationPresent(SkipDb.class)) {
                    Object value = field.get(dto);
                    result.put(field.getName(), value);
                }
            } catch (IllegalAccessException ignore) {
            }
        }
        return MAPPER.valueToTree(result);
    }

    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass(); // Parent classga o'tish
        }

        return fields;
    }
}