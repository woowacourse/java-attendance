package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.Objects;
import java.util.Set;

public class Attendance {

    private static final Set<MonthDay> HOLIDAYS = Set.of(MonthDay.of(12, 25));

    private final String nickname;
    private final LocalDate attendanceDate;
    private LocalTime attendanceTime;

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

    public boolean isAlreadyAttend(String nickname, LocalDate date) {
        return this.nickname.equals(nickname) &&
                attendanceDate.equals(date);
    }

    private void validateAttendanceDate(LocalDate attendanceDate) {
        if (isWeekend(attendanceDate)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
        if (HOLIDAYS.contains(MonthDay.from(attendanceDate))) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }

    private static boolean isWeekend(LocalDate attendanceDate) {
        return attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean canAttend(LocalDate attendanceDate) {
        if (isWeekend(attendanceDate)) {
            return false;
        }
        if (HOLIDAYS.contains(MonthDay.from(attendanceDate))) {
            return false;
        }
        return true;
    }

    public void updateAttendanceTime(LocalTime updateTime) {
        this.attendanceTime = updateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return AttendanceStatus.calculate(attendanceDate.getDayOfWeek(), attendanceTime);
    }

    public boolean isEqualNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Attendance that = (Attendance) object;
        return Objects.equals(nickname, that.nickname) && Objects.equals(attendanceDate,
                that.attendanceDate) && Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(nickname);
        result = 31 * result + Objects.hashCode(attendanceDate);
        result = 31 * result + Objects.hashCode(attendanceTime);
        return result;
    }
}
