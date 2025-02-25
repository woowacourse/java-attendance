package dto;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.AttendanceCounter;
import model.SubjectType;

public record DismissalCrewDto(String nickname, int absentCount, int lateCount, SubjectType subjectType)
        implements Comparable<DismissalCrewDto> {

    private static final Comparator<DismissalCrewDto> COMPARATOR =
            Comparator.comparing((DismissalCrewDto dismissalCrewDto) -> dismissalCrewDto.subjectType,
                            SubjectType.getComparator())
                    .thenComparing(dto -> SubjectType.calculateTotalLateCount(dto.lateCount, dto.absentCount),
                            Comparator.reverseOrder())
                    .thenComparing(dto -> dto.nickname);

    public static List<DismissalCrewDto> of(final Map<String, AttendanceCounter> dismissalCrews) {
        return dismissalCrews.entrySet().stream()
                .map(entry -> new DismissalCrewDto(entry.getKey(), entry.getValue().getAbsentCount(),
                        entry.getValue().getLateCount(), SubjectType.from(entry.getValue().getAbsentCount(), entry.getValue().getLateCount())))
                .collect(Collectors.toList());
    }

    @Override
    public int compareTo(final DismissalCrewDto other) {
        return COMPARATOR.compare(this, other);
    }
}
