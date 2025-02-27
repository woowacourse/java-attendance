package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance {
    private static final int TARDY_THRESHOLD_MINUTE = 5;
    private static final int ABSENT_THRESHOLD_MINUTE = 30;
    private static final LocalTime MONDAY_OPEN = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_OPEN = LocalTime.of(10, 0);
    private static final List<LocalDate> HOLIDAYS = List.of(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 3),
            LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 6), LocalDate.of(2025, 6, 6));

    Map<Crew, LocalDate> attendanceHistory = new HashMap<>();

    public String checkAttendance(Crew crew, LocalDateTime attendanceDateTime) {
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        validateDuplicateAttendance(crew, attendanceDate);
        validateDayOff(attendanceDate);
        attendanceHistory.put(crew, attendanceDate);
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return checkAttendanceByDay(attendanceTime, MONDAY_OPEN);
        }
        return checkAttendanceByDay(attendanceTime, DEFAULT_OPEN);
    }

    private void validateDayOff(LocalDate attendanceDate) {
        if (attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY || attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || HOLIDAYS.contains(attendanceDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일");
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", formatter.format(attendanceDate)));
        }
    }

    private void validateDuplicateAttendance(Crew crew, LocalDate attendanceDateTime) {
        if (!attendanceHistory.containsKey(crew)) {
            return;
        }
        if (attendanceHistory.get(crew).isEqual(attendanceDateTime)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
        }
    }

    private String checkAttendanceByDay(LocalTime attendanceTime, LocalTime openTime) {
        if (attendanceTime.isAfter(openTime.plusMinutes(ABSENT_THRESHOLD_MINUTE))) {
            return "결석";
        }
        if (attendanceTime.isAfter(openTime.plusMinutes(TARDY_THRESHOLD_MINUTE))) {
            return "지각";
        }
        return "출석";
    }
}
