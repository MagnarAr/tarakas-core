package ee.tarakas.core.goals.dto.validator;

import javax.validation.Payload;
import java.lang.annotation.Annotation;

class ValidatorTestUtil {

    static ImageSize getImageSizeAnnotation(long width, long height) {
        return new ImageSize() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return ImageSize.class;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public long height() {
                return height;
            }

            @Override
            public long width() {
                return width;
            }
        };
    }

}
