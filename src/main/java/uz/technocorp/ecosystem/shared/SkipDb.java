package uz.technocorp.ecosystem.shared;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 04.06.2025
 * @since v1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SkipDb {
}
