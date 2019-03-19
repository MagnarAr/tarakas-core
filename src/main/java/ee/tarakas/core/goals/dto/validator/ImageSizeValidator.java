package ee.tarakas.core.goals.dto.validator;

import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageSizeValidator implements ConstraintValidator<ImageSize, byte[]> {

    private long height;
    private long width;

    @Override
    public void initialize(ImageSize constraintAnnotation) {
        height = constraintAnnotation.height();
        width = constraintAnnotation.width();
    }

    @Override
    public boolean isValid(byte[] value, ConstraintValidatorContext context) {
        if (ArrayUtils.isEmpty(value)) {
            return true;
        }
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(value));
            return img.getWidth() == width && img.getHeight() == height;
        } catch (IOException e) {
            return false;
        }
    }

}
