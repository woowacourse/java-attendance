package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Attendance {
    private static final int TARDY_THRESHOLD_MINUTE = 5;
    private static final int ABSENT_THRESHOLD_MINUTE = 30;
    private static final LocalTime MONDAY_OPEN = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_OPEN = LocalTime.of(10, 0);

    Map<Crew, LocalDate> attendanceHistory = new HashMap<>();

    public String checkAttendance(Crew crew, LocalDateTime attendanceDateTime) {
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        validateDuplicateAttendance(crew, attendanceDate);
        validateDayOff(attendanceDate);
        validateOperatingTime(attendanceTime);
        attendanceHistory.put(crew, attendanceDate);
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return checkAttendanceByDay(attendanceTime, MONDAY_OPEN);
        }
        return checkAttendanceByDay(attendanceTime, DEFAULT_OPEN);
    }

    private void validateOperatingTime(LocalTime attendanceTime) {
        if (attendanceTime.isBefore(LocalTime.of(8, 0)) || attendanceTime.isAfter(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
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

    private void validateDayOff(LocalDate attendanceDate) {
        if (attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY || attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || LegalHoliday.isHoliday(attendanceDate) || Vacation.isVacation(attendanceDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일");
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", formatter.format(attendanceDate)));
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
