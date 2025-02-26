package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceHistory {

    private static final int CHRISTMAS_MONTH = 12;
    private static final int CHRISTMAS_MONTH_OF_DAY = 25;

    private final Map<LocalDate, AttendanceRecord> attendanceHistory;

    public AttendanceHistory() {
        this.attendanceHistory = new HashMap<>();
    }

    public AttendanceRecord attendance(final LocalDateTime attendanceDateTime) {
        validateAttendanceDay(attendanceDateTime);
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
        attendanceHistory.put(attendanceDateTime.toLocalDate(), attendanceRecord);
        return attendanceRecord;
    }

    private void validateAttendanceDay(final LocalDateTime attendanceDateTime) {
        final LocalDate date = attendanceDateTime.toLocalDate();
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (DayOfWeek.SATURDAY == dayOfWeek || DayOfWeek.SUNDAY == dayOfWeek
                || (date.getMonthValue() == CHRISTMAS_MONTH && date.getDayOfMonth() == CHRISTMAS_MONTH_OF_DAY)) {
            throw new IllegalArgumentException();
        }
    }
}
