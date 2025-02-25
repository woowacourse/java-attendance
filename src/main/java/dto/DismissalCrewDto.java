package dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import domain.model.AttendanceCounter;
import domain.model.SubjectType;

public record DismissalCrewDto(String nickname, int absentCount, int lateCount, SubjectType subjectType) {

    public static List<DismissalCrewDto> of(final Map<String, AttendanceCounter> dismissalCrews) {
        return dismissalCrews.entrySet().stream()
                .map(entry -> new DismissalCrewDto(entry.getKey(), entry.getValue().getAbsentCount(),
                        entry.getValue().getLateCount(),
                        SubjectType.from(entry.getValue().getAbsentCount(), entry.getValue().getLateCount())))
                .collect(Collectors.toList());
    }
}
