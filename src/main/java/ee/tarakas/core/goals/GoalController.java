package ee.tarakas.core.goals;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/goals")
public class GoalController {

    private final GoalService goalService;

    GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public List<Goal> getAllGoals(@AuthenticationPrincipal String userId) {
        return goalService.getAllUserGoals(userId);
    }

    // Adds new or updates existing goal
    @PostMapping
    public Goal saveGoal(@AuthenticationPrincipal String userId, @RequestBody Goal goal) {
        goal.setUserId(userId);
        return goalService.saveGoal(goal);
    }

    // Adds new or updates existing goal
    @DeleteMapping(value = "/{goalId}")
    public void deleteGoal(@AuthenticationPrincipal String userId, @PathVariable String goalId) {
        goalService.deleteGoal(userId, goalId);
    }

}
