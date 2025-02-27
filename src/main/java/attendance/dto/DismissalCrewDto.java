package attendance.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import attendance.domain.model.AttendanceCounter;
import attendance.domain.model.WarningLevel;

public record DismissalCrewDto(String nickname, int absentCount, int lateCount, WarningLevel warningLevel) {

    public static List<DismissalCrewDto> of(final Map<String, AttendanceCounter> dismissalCrews) {
        return dismissalCrews.entrySet().stream()
                .map(entry -> new DismissalCrewDto(entry.getKey(), entry.getValue().getAbsentCount(),
                        entry.getValue().getLateCount(),
                        WarningLevel.from(entry.getValue().getAbsentCount(), entry.getValue().getLateCount())))
                .collect(Collectors.toList());
    }
}
