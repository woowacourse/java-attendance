package dto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
                .toList();
    }

    private static DismissalCrewDto of(final LocalDate todayDate, final CrewHistory crewHistory) {
        Map<AttendanceType, Integer> countedAttendanceType = crewHistory.countAttendanceType(todayDate);
        return new DismissalCrewDto(crewHistory.getNickname(), countedAttendanceType.get(AttendanceType.결석),
                countedAttendanceType.get(AttendanceType.지각), SubjectType.from(countedAttendanceType));
    }

    @Override
    public int compareTo(final DismissalCrewDto other) {
        return COMPARATOR.compare(this, other);
    }
}
