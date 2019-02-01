package ee.tarakas.core.goals;

import ee.tarakas.core.common.ErrorCode;
import ee.tarakas.core.common.ErrorResponse;
import ee.tarakas.core.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured(value = "ROLE_USER")
@RequestMapping(value = "/api/goals")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public List<Goal> getAllGoals(@AuthenticationPrincipal UserContext userDetails) {
        return goalService.getAllUserGoals(userDetails.getUserId());
    }

    // Adds new or updates existing goal
    @PostMapping
    public Goal saveGoal(@AuthenticationPrincipal UserContext userDetails, @RequestBody Goal goal) {
        goal.setUserId(userDetails.getUserId());
        return goalService.saveGoal(goal);
    }

    // Adds new or updates existing goal
    @DeleteMapping(value = "/{goalId}")
    public void deleteGoal(@AuthenticationPrincipal UserContext userDetails, @PathVariable String goalId) {
        goalService.deleteGoal(userDetails.getUserId(), goalId);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ErrorResponse.of(e.getMessage(), ErrorCode.GLOBAL, HttpStatus.BAD_REQUEST);
    }
}
