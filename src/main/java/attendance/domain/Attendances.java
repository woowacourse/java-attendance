package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import attendance.exception.AttendanceArgumentException;

public record Attendances(Map<LocalDate, Attendance> attendances, SystemDateTime systemDateTime) {
    private static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";

    public Attendances(SystemDateTime systemDateTime) {
        this(new HashMap<>(), systemDateTime);
    }

    public void add(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        isValidateSchedule(date);
        attendances.put(date, new Attendance(dateTime));
    }

    private void isValidateSchedule(LocalDate date) {
        if (!systemDateTime.isWorkingDay(date)) {
            var formatted = DateTimeFormatter.ofPattern(CANNOT_ATTENDANCE_WEEKEND_FORMAT).format(date);
            throw new AttendanceArgumentException(formatted);
        }
    }

    public Attendance get(LocalDate date) {
        return attendances.get(date);
    }
}
