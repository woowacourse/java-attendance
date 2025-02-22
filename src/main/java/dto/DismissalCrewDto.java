package dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import model.AttendanceType;
import model.CrewHistory;
import model.SubjectType;

public record DismissalCrewDto(String nickname, int absentCount, int lateCount, SubjectType subjectType) implements
        Comparable<DismissalCrewDto> {

    @Override
    public int compareTo(final DismissalCrewDto o) {
        if (this.subjectType != o.subjectType) {
            return SubjectType.compare(this.subjectType, o.subjectType);
        }
        return compareLateCountAndNickname(o);
    }

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

    private int compareLateCountAndNickname(DismissalCrewDto other) {
        int thisTotalLateCount = SubjectType.calculateTotalLateCount(this.lateCount, this.absentCount);
        int targetTotalLateCount = SubjectType.calculateTotalLateCount(other.lateCount, other.absentCount);
        if (thisTotalLateCount != targetTotalLateCount) {
            return Integer.compare(targetTotalLateCount, thisTotalLateCount);
        }
        return this.nickname.compareTo(other.nickname);
    }
}
