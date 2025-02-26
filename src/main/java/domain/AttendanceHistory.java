package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceHistory {

    private final Map<LocalDate, AttendanceRecord> attendanceHistory;

    public AttendanceHistory() {
        this.attendanceHistory = new HashMap<>();
    }

    public AttendanceRecord attendance(final LocalDateTime attendanceDateTime) {
        final LocalDate date = attendanceDateTime.toLocalDate();
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (DayOfWeek.SATURDAY == dayOfWeek || DayOfWeek.SUNDAY == dayOfWeek || (date.getMonthValue() == 12
                && date.getDayOfMonth() == 25)) {
            throw new IllegalArgumentException();
        }
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
        attendanceHistory.put(attendanceDateTime.toLocalDate(), attendanceRecord);
        return attendanceRecord;
    }
}
