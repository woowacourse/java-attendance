package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Attendance {
    private static final LocalTime MONDAY_START = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_START = LocalTime.of(10, 0);
    private static final int LATE_STANDARD = 5;
    private static final int ABSENT_STANDARD = 30;

    Map<Crew, LocalDate> attendanceHistory = new HashMap<>();

    public String checkAttendance(Crew crew, LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime openTime;
        isHoliday(attendanceDate);
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            openTime = MONDAY_START;
        } else {
            openTime = DEFAULT_START;
        }
        LocalTime lateThreshold = openTime.plusMinutes(5);
        LocalTime absentThreshold = openTime.plusMinutes(30);
        if (attendanceTime.isAfter(absentThreshold)) {
            return "결석";
        }
        if (attendanceTime.isAfter(lateThreshold)) {
            return "지각";
        }
        return "출석";
    }

    private void isHoliday(LocalDate attendanceDate) {
        if (attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY || attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || Holiday.isHoliday(attendanceDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일");
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", formatter.format(attendanceDate)));
        }
    }
}
