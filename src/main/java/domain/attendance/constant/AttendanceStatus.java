package domain.attendance.constant;

import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private final static int MONDAY_HOUR_LIMIT = 13;
    private final static int WEEKDAY_HOUR_LIMIT = 10;
    private final static int ABSENCE_LIMIT = 30;
    private final static int TARDINESS_LIMIT = 5;

    private final String value;

    private static final Map<AttendanceStatus, List<CampusTime>> MONDAY_STATUS = new EnumMap<>(AttendanceStatus.class);
    private static final Map<AttendanceStatus, List<CampusTime>> WEEKDAY_STATUS = new EnumMap<>(AttendanceStatus.class);

    static {
        MONDAY_STATUS.put(ATTENDANCE,
                List.of(CampusTime.startTime(), CampusTime.of(MONDAY_HOUR_LIMIT, TARDINESS_LIMIT + 1)));
        MONDAY_STATUS.put(TARDINESS, List.of(CampusTime.of(MONDAY_HOUR_LIMIT, TARDINESS_LIMIT),
                CampusTime.of(MONDAY_HOUR_LIMIT, ABSENCE_LIMIT + 1)));
        MONDAY_STATUS.put(ABSENCE, List.of(CampusTime.of(MONDAY_HOUR_LIMIT, ABSENCE_LIMIT), CampusTime.endTime()));

        WEEKDAY_STATUS.put(ATTENDANCE,
                List.of(CampusTime.startTime(), CampusTime.of(WEEKDAY_HOUR_LIMIT, TARDINESS_LIMIT + 1)));
        WEEKDAY_STATUS.put(TARDINESS, List.of(CampusTime.of(WEEKDAY_HOUR_LIMIT, TARDINESS_LIMIT),
                CampusTime.of(WEEKDAY_HOUR_LIMIT, ABSENCE_LIMIT + 1)));
        WEEKDAY_STATUS.put(ABSENCE, List.of(CampusTime.of(WEEKDAY_HOUR_LIMIT, ABSENCE_LIMIT), CampusTime.endTime()));
    }

    AttendanceStatus(String value) {
        this.value = value;
    }

    public static AttendanceStatus calculateByDateAndTime(final CampusDate date, final CampusTime time) {
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return calculateStatus(MONDAY_STATUS, time);
        }
        return calculateStatus(WEEKDAY_STATUS, time);
    }

    private static AttendanceStatus calculateStatus(final Map<AttendanceStatus, List<CampusTime>> status,
                                                    final CampusTime time) {
        return status.entrySet().stream()
                .filter(entry -> time.isAfter(entry.getValue().getFirst()) && time.isBefore(entry.getValue().getLast()))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 상태를 계산할 수 없습니다."));
    }

    public String getValue() {
        return value;
    }
}
