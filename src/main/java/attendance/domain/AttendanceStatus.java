package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public enum AttendanceStatus {
    DAY_OFF("휴무일", null),
    ATTENDANCE("출석", null),
    LATENESS("지각", 5),
    ABSENCE("결석", 30),
    ;

    private static final int LATE_TO_ABSENCE_RATIO = 3;

    private final String name;
    private final Integer minLateTime;

    AttendanceStatus(String name, Integer minLateTime) {
        this.name = name;
        this.minLateTime = minLateTime;
    }

    public static AttendanceStatus from(LocalDate date, LocalTime time) {
        if (DayOff.isDayOff(date)) {
            return AttendanceStatus.DAY_OFF;
        }
        int lateTime = LectureTime.from(date).getLateTimeOf(time);
        return Arrays.stream(values())
            .filter(status -> status.minLateTime != null)
            .filter(status -> status.minLateTime < lateTime)
            .max(Comparator.comparing(status -> status.minLateTime))
            .orElse(ATTENDANCE);
    }

    public static Map<AttendanceStatus, Integer> getEmptyStatistics() {
        return Arrays.stream(values())
            .collect(Collectors.toMap(status -> status, status -> 0));
    }

    public static int getConvertedAbsence(Map<AttendanceStatus, Integer> statistics) {
        int lateness = statistics.getOrDefault(AttendanceStatus.LATENESS, 0);
        int absence = statistics.getOrDefault(AttendanceStatus.ABSENCE, 0);
        return (lateness + absence * LATE_TO_ABSENCE_RATIO) / LATE_TO_ABSENCE_RATIO;
    }

    public static int getSortWeight(Map<AttendanceStatus, Integer> statistics) {
        int lateness = statistics.getOrDefault(AttendanceStatus.LATENESS, 0);
        int absence = statistics.getOrDefault(AttendanceStatus.ABSENCE, 0);
        return lateness + absence * LATE_TO_ABSENCE_RATIO;
    }

    public String getName() {
        return name;
    }
}
