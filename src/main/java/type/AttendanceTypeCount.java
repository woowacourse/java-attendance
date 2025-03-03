package type;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceTypeCount {
    private final Map<AttendanceType, Integer> attendanceTypeCount;

    private AttendanceTypeCount(Map<AttendanceType, Integer> attendanceTypeCount) {
        this.attendanceTypeCount = attendanceTypeCount;
    }

    // TODO: 필요성에 대해 다시 생각하기. 테스트 전파일까?
    public static AttendanceTypeCount from(int absenceCount, int lateCount) {
        return new AttendanceTypeCount(
                Map.of(AttendanceType.ABSENCE, absenceCount,
                        AttendanceType.LATE, lateCount)
        );
    }

    public static AttendanceTypeCount createFrom(Map<LocalDateTime, AttendanceType> attendanceTypeOfDates) {
        Map<AttendanceType, Integer> attendanceTypeCounts = attendanceTypeOfDates.values().stream()
                .collect(Collectors.toMap(
                        attendanceType -> attendanceType,
                        attendanceType -> 1,
                        Integer::sum
                ));
        return new AttendanceTypeCount(attendanceTypeCounts);
    }

    public int getAbsenceCount() {
        return attendanceTypeCount.getOrDefault(AttendanceType.ABSENCE, 0) + attendanceTypeCount.getOrDefault(
                AttendanceType.NO_DATA, 0);
    }

    public int getLateCount() {
        return attendanceTypeCount.getOrDefault(AttendanceType.LATE, 0);
    }

    public int getAdjustedAbsenceCount() {
        return getAbsenceCount() + getLateCount() / 3;
    }

    public int getCountByType(AttendanceType attendanceType) {
        if (attendanceType.equals(AttendanceType.ABSENCE)) {
            return getAbsenceCount();
        }
        return attendanceTypeCount.getOrDefault(attendanceType, 0);
    }

}
