package ee.tarakas.core.goals;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class GoalService {

    private final static int MINIMUM_PRICE = 0;
    private final static int MAXIMUM_PRICE = 1000000;
    private final static int IMAGE_WIDTH = 300;
    private final static int IMAGE_HEIGHT = 300;

    private final GoalRepository goalRepository;

    GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    Goal saveGoal(Goal goal) {
        if (goal.getPrice().doubleValue() < MINIMUM_PRICE) {
            throw new IllegalArgumentException("PRICE_CANNOT_BE_NEGATIVE");
        }
        if (goal.getPrice().doubleValue() > MAXIMUM_PRICE) {
            throw new IllegalArgumentException("PRICE_TOO_HIGH");
        }
        if (goal.getName().isEmpty()) {
            throw new IllegalArgumentException("NAME_CANNOT_BE_EMPTY");
        }
        if (goal.getCollectedAmount().doubleValue() < 0 || goal.getCollectedAmount().doubleValue() > goal.getPrice().doubleValue()) {
            throw new IllegalArgumentException("INVALID_COLLECTED_AMOUNT");
        }
        checkGoalImageSize(goal);
        goal.setPrice(goal.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
        goal.setCollectedAmount(goal.getCollectedAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        return goalRepository.save(goal);
    }

    private void checkGoalImageSize(Goal goal) {
        if (!ArrayUtils.isEmpty(goal.getImageBase64Bytes())) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(goal.getImageBase64Bytes()));
                if (img.getWidth() != IMAGE_WIDTH && img.getHeight() != IMAGE_HEIGHT) {
                    throw new IllegalArgumentException("INVALID_IMAGE_SIZE");
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("INVALID_IMAGE_SIZE");
            }
        }
    }

    List<Goal> getAllUserGoals(String userId) {
        return goalRepository.findByUserId(userId);
    }

    void deleteGoal(String userId, String goalId) {
        goalRepository.findById(goalId).ifPresent(goal -> {
            if (goal.getUserId().equals(userId)) {
                goalRepository.delete(goal);
            } else {
                throw new IllegalArgumentException("NO_SUCH_GOAL");
            }
        });
    }
}
