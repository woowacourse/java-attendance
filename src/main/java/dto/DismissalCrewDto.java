package dto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.AttendanceType;
import model.CrewHistory;
import model.SubjectType;

public record DismissalCrewDto(String nickname, int absentCount, int lateCount, SubjectType subjectType)
        implements Comparable<DismissalCrewDto> {

    private static final Comparator<DismissalCrewDto> COMPARATOR =
            Comparator.comparing((DismissalCrewDto dismissalCrewDto) -> dismissalCrewDto.subjectType,
                            SubjectType.getComparator())
                    .thenComparing(dto -> SubjectType.calculateTotalLateCount(dto.lateCount, dto.absentCount),
                            Comparator.reverseOrder())
                    .thenComparing(dto -> dto.nickname);

    public static List<DismissalCrewDto> of(final LocalDate todayDate, final List<CrewHistory> crewHistories) {
        return crewHistories.stream()
                .map(crew -> of(todayDate, crew))
                .collect(Collectors.toList());
    }

    private static DismissalCrewDto of(final LocalDate todayDate, final CrewHistory crewHistory) {
        Map<AttendanceType, Integer> countedAttendanceType = crewHistory.countAttendanceType(todayDate);
        return new DismissalCrewDto(crewHistory.getNickname(), countedAttendanceType.get(AttendanceType.ABSENCE),
                countedAttendanceType.get(AttendanceType.LATE), SubjectType.from(countedAttendanceType));
    }

    @Override
    public int compareTo(final DismissalCrewDto other) {
        return COMPARATOR.compare(this, other);
    }
}
