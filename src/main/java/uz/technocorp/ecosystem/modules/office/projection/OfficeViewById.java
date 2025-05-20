package uz.technocorp.ecosystem.modules.office.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 10.03.2025
 * @since v1.0
 */
public interface OfficeViewById {

    Integer getId();

    String getName();

    Integer getRegionId();
}
