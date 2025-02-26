package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private final String nickname;
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(String nickname, LocalDateTime attendanceDateTime) {
        this.nickname = nickname;
        this.attendanceDate = attendanceDateTime.toLocalDate();
        this.attendanceTime = attendanceDateTime.toLocalTime();
    }

    public boolean isAlreadyAttend(Attendance newAttendance) {
        return nickname.equals(newAttendance.nickname) &&
                attendanceDate.equals(newAttendance.attendanceDate);
    }
}
