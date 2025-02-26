package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.Set;

public class Attendance {

    private static final Set<MonthDay> HOLIDAYS = Set.of(MonthDay.of(12, 25));

    private final String nickname;
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(String nickname, LocalDateTime attendanceDateTime) {
        validateAttendanceDate(attendanceDateTime.toLocalDate());
        this.nickname = nickname;
        this.attendanceDate = attendanceDateTime.toLocalDate();
        this.attendanceTime = attendanceDateTime.toLocalTime();
    }

    public boolean isAlreadyAttend(Attendance newAttendance) {
        return nickname.equals(newAttendance.nickname) &&
                attendanceDate.equals(newAttendance.attendanceDate);
    }

    private void validateAttendanceDate(LocalDate attendanceDate) {
        if (isWeekend(attendanceDate)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
        if (HOLIDAYS.contains(MonthDay.from(attendanceDate))) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }

    private boolean isWeekend(LocalDate attendanceDate) {
        return attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public AttendanceStatus getAttendanceStatus() {
        return AttendanceStatus.calculate(attendanceDate.getDayOfWeek(), attendanceTime);
    }
}
