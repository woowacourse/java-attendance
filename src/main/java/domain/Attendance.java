package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Attendance {
    private final Map<Crew, LocalDate> attendanceHistory = new HashMap<>();

    public AttendanceStatus checkAttendance(Crew crew, LocalDateTime attendanceDateTime) {
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        validateDuplicateAttendance(crew, attendanceDate);
        validateDayOff(attendanceDate);
        validateOperatingTime(attendanceTime);
        attendanceHistory.put(crew, attendanceDate);
        return AttendanceStatus.of(attendanceDateTime);
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

    private void validateOperatingTime(LocalTime attendanceTime) {
        if (!CampusHour.isOperatingTime(attendanceTime)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }
}
