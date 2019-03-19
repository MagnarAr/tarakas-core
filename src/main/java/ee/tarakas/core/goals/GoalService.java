package ee.tarakas.core.goals;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    List<Goal> getAllUserGoals(String userId) {
        return goalRepository.findAllByUserId(userId);
    }

    Goal saveGoal(Goal goal) {
        goal.setPrice(goal.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
        goal.setCollectedAmount(goal.getCollectedAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        return goalRepository.save(goal);
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
