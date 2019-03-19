package ee.tarakas.core.goals;

import ee.tarakas.core.goals.dto.GoalDTO;
import ee.tarakas.core.goals.dto.GoalMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/goals")
public class GoalController {

    private final GoalService goalService;
    private final GoalMapper goalMapper;

    GoalController(GoalService goalService, GoalMapper goalMapper) {
        this.goalService = goalService;
        this.goalMapper = goalMapper;
    }

    @GetMapping
    public List<GoalDTO> getAllGoals(@AuthenticationPrincipal String userId) {
        return goalMapper.goalsToGoalDTOs(goalService.getAllUserGoals(userId));
    }

    @PostMapping
    public GoalDTO saveGoal(@AuthenticationPrincipal String userId, @Valid @RequestBody GoalDTO goalDto) {
        Goal goal = goalMapper.goalDTOToGoal(goalDto);
        goal.setUserId(userId);
        goal = goalService.saveGoal(goal);
        return goalMapper.goalToGoalDTO(goal);
    }

    @DeleteMapping(value = "/{goalId}")
    public void deleteGoal(@AuthenticationPrincipal String userId, @PathVariable String goalId) {
        goalService.deleteGoal(userId, goalId);
    }

}
