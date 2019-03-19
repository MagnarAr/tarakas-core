package ee.tarakas.core.goals.dto;

import ee.tarakas.core.goals.Goal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {

    @Mapping(source = "imageBase64Bytes", target = "imageSource")
    GoalDTO goalToGoalDTO(Goal goal);

    @Mappings({
        @Mapping(source = "imageSource", target = "imageBase64Bytes"),
        @Mapping(target = "userId", ignore = true)
    })
    Goal goalDTOToGoal(GoalDTO goal);

    List<GoalDTO> goalsToGoalDTOs(List<Goal> goalList);

    default BigDecimal bigDecimalConverter(BigDecimal before) {
        return before == null ? null : before.setScale(2,BigDecimal.ROUND_HALF_UP);
    }

}
