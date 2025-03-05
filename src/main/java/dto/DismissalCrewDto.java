package dto;

import java.util.Map;
import model.AttendanceType;
import model.DismissalType;

public record DismissalCrewDto(
        String nickname, Map<AttendanceType, Integer> typeCountResult, DismissalType dismissalType
) {
}
