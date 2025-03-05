package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class AttendanceHistory {
    private static final LocalTime MONDAY_START = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_START = LocalTime.of(10, 0);
    private static final int LATE_STANDARD = 5;
    private static final int ABSENT_STANDARD = 30;

    private final Map<Crew, Attendances> attendanceHistory;

    public AttendanceHistory(Map<Crew, Attendances> attendanceHistory) {
        this.attendanceHistory = attendanceHistory;
    }

    public AttendanceResult checkAttendance(Crew crew, LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime openTime;
        isHoliday(attendanceDate);
        duplicateAttendance(attendanceHistory.get(crew), attendanceDate);
        validateOperatingTime(attendanceTime);
        attendanceHistory.put(crew, attendanceHistory.get(crew).add(attendanceDateTime));
        return AttendanceResult.getAttendanceResult(attendanceDateTime);

    }

    private void duplicateAttendance(Attendances attendances, LocalDate attendanceDate) {
        if (attendances.haveAttendanceDate(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다.");
        }
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

    public LocalDateTime editAttendance(Crew crew, LocalDateTime newAttendanceTime) {
        isExistName(crew);
        Attendances attendanceDateTimes = attendanceHistory.get(crew);
        LocalDateTime oldAttendanceDateTime = attendanceDateTimes.edit(newAttendanceTime.toLocalDate());
        attendanceDateTimes.add(newAttendanceTime);
        return oldAttendanceDateTime;
    }

    private void isExistName(Crew crew) {
        if (!attendanceHistory.containsKey(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 이름입니다.");
        }
    }
}
