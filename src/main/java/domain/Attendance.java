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

    public AttendanceResult checkAttendance(Crew crew, LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime openTime;
        isHoliday(attendanceDate);
        validateOperatingTime(attendanceTime);
        attendanceHistory.put(crew, attendanceDate);
        return AttendanceResult.getAttendanceResult(attendanceDateTime);

    }

    private void isHoliday(LocalDate attendanceDate) {
        if (attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY || attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || Holiday.isHoliday(attendanceDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일");
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", formatter.format(attendanceDate)));
        }
    }

    private void validateOperatingTime(LocalTime attendanceTime) {
        if (!AttendanceTimePolicy.isOperatingTime(attendanceTime)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }
}
