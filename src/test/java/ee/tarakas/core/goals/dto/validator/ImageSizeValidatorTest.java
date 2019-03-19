package ee.tarakas.core.goals.dto.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class ImageSizeValidatorTest {

    private static ImageSizeValidator imageSizeValidator = new ImageSizeValidator();
    private static byte[] testImageAsBytes;

    @BeforeAll
    static void setUp() throws Exception {
        final Resource resource = new ClassPathResource("images/spring-boot-project-logo.png");
        testImageAsBytes = Files.readAllBytes(resource.getFile().toPath());
    }

    @Test
    void whenImageMeetsSizeRequirements_validatorReturnsTrue() {
        imageSizeValidator.initialize(ValidatorTestUtil.getImageSizeAnnotation(300, 300));
        boolean isValid = imageSizeValidator.isValid(testImageAsBytes, null);
        assertTrue(isValid);
    }

    @Test
    void whenImageDoesNotMeetSizeRequirements_validatorReturnsFalse() {
        imageSizeValidator.initialize(ValidatorTestUtil.getImageSizeAnnotation(200, 200));
        boolean isValid = imageSizeValidator.isValid(testImageAsBytes, null);
        assertFalse(isValid);
    }

}