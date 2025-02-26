package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

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
        if (attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY || attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }
}
