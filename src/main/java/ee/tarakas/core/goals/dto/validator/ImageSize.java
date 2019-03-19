package ee.tarakas.core.goals.dto.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ImageSizeValidator.class)
public @interface ImageSize {

    long DEFAULT_IMAGE_WIDTH = 300;
    long DEFAULT_IMAGE_HEIGHT = 300;

    String message() default "Invalid image size";

    Class<?>[] groups() default { };

    Class[] payload() default { };

    /**
     * @return allowed height for image
     */
    long height() default DEFAULT_IMAGE_HEIGHT;

    /**
     * @return allowed width for image
     */
    long width() default DEFAULT_IMAGE_WIDTH;

}
