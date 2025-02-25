package attendance.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceLog {

    private final Nickname nickname;
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public AttendanceLog(Nickname nickname, LocalDate attendanceDate, LocalTime attendanceTime) {
        this.nickname = requireNonNull(nickname, "닉네임은 null일 수 없습니다.");
        this.attendanceDate = requireNonNull(attendanceDate, "출석 날짜는 null일 수 없습니다.");
        this.attendanceTime = attendanceTime;
    }

    public AttendanceLog(Nickname nickname, LocalDate attendanceDate) {
        this.nickname = requireNonNull(nickname, "닉네임은 null일 수 없습니다.");
        this.attendanceDate = requireNonNull(attendanceDate, "출석 날짜는 null일 수 없습니다.");
        this.attendanceTime = null;
    }

    public boolean isAbsent() {
        return attendanceTime == null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceLog that = (AttendanceLog) o;
        return Objects.equals(nickname, that.nickname)
                && Objects.equals(attendanceDate, that.attendanceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, attendanceDate);
    }
}
