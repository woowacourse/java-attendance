package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final String nickname;
    private final AttendanceDate attendanceDate;
    private LocalTime attendanceTime;

    public Attendance(String nickname, LocalDateTime attendanceDateTime) {
        this(nickname, attendanceDateTime.toLocalDate(), attendanceDateTime.toLocalTime());
    }

    public Attendance(String nickname, LocalDate attendanceDate, LocalTime attendanceTime) {
        this.nickname = nickname;
        this.attendanceDate = new AttendanceDate(attendanceDate);
        this.attendanceTime = attendanceTime;
    }

    public boolean isAlreadyAttend(Attendance newAttendance) {
        return nickname.equals(newAttendance.nickname) &&
                attendanceDate.equals(newAttendance.attendanceDate);
    }

    public boolean isAlreadyAttend(String nickname, AttendanceDate date) {
        return this.nickname.equals(nickname) &&
                attendanceDate.equals(date);
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

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getAttendanceDateTime() {
        return LocalDateTime.of(attendanceDate.getAttendanceDate(), attendanceTime);
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate.getAttendanceDate();
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
        return Objects.equals(nickname, that.nickname) && attendanceDate.equals(that.attendanceDate)
                && Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(nickname);
        result = 31 * result + attendanceDate.hashCode();
        result = 31 * result + Objects.hashCode(attendanceTime);
        return result;
    }
}
