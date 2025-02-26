package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceHistory {

    private static final int CHRISTMAS_MONTH = 12;
    private static final int CHRISTMAS_MONTH_OF_DAY = 25;

    private final Crew crew;
    private final Map<LocalDate, AttendanceRecord> attendanceHistory;

    public AttendanceHistory(final Crew crew) {
        this.attendanceHistory = new HashMap<>();
        this.crew = crew;
    }

    public AttendanceRecord attendance(final LocalDateTime attendanceDateTime) {
        validateAttendanceDay(attendanceDateTime);
        validateAlreadyAttendance(attendanceDateTime);
        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);
        attendanceHistory.put(attendanceDateTime.toLocalDate(), attendanceRecord);
        return attendanceRecord;
    }

    private void validateAlreadyAttendance(final LocalDateTime attendanceDateTime) {
        if (attendanceHistory.containsKey(attendanceDateTime.toLocalDate())) {
            throw new IllegalStateException();
        }
    }

    private void validateAttendanceDay(final LocalDateTime attendanceDateTime) {
        final LocalDate date = attendanceDateTime.toLocalDate();
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (DayOfWeek.SATURDAY == dayOfWeek || DayOfWeek.SUNDAY == dayOfWeek
                || (date.getMonthValue() == CHRISTMAS_MONTH && date.getDayOfMonth() == CHRISTMAS_MONTH_OF_DAY)) {
            throw new IllegalArgumentException();
        }
    }

    public AttendanceRecord updateTimeByDate(final LocalDateTime afterTime) {
        return null;
    }

    public AttendanceRecord findByDate(final LocalDate date) {
        return null;
    }

    public Crew getCrew() {
        return crew;
    }
}
