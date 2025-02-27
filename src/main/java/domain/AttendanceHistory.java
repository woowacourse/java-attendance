package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceHistory {

    private static final int CHRISTMAS_MONTH = 12;
    private static final int CHRISTMAS_MONTH_OF_DAY = 25;
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    ;

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
        validateAttendanceDay(afterTime);
        validateAttendanceTime(afterTime);
        final AttendanceRecord prevRecord = attendanceHistory.get(afterTime.toLocalDate());
        final AttendanceRecord newRecord = new AttendanceRecord(afterTime);
        attendanceHistory.put(afterTime.toLocalDate(), newRecord);
        return prevRecord;
    }

    private static void validateAttendanceTime(final LocalDateTime afterTime) {
        final LocalTime time = afterTime.toLocalTime();
        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            throw new IllegalArgumentException();
        }
    }

    public AttendanceRecord findByDate(final LocalDate date) {
        validateAttendanceDay(LocalDateTime.of(date, LocalTime.NOON));
        if (!attendanceHistory.containsKey(date)) {
            return AttendanceRecord.empty(date);
        }
        return attendanceHistory.get(date);
    }

    public Crew getCrew() {
        return crew;
    }

    public List<AttendanceRecord> findAllUntilBeforeToday(final LocalDate targetDate) {
        return null;
    }
}
