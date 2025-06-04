package uz.technocorp.ecosystem.modules.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import uz.technocorp.ecosystem.shared.BaseEntity;

import java.util.Map;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 04.06.2025
 * @since v1.0
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity extends BaseEntity {

    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> params;
}
