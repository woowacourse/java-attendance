import domain.AttendanceRecord;
import domain.DateProvider;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Crew {

    private final String nickname;
    private final AttendanceRecord attendanceRecord;

    public Crew(String nickname, LocalDateTime attendanceTime, DateProvider dateProvider) {
        this.nickname = nickname;
        this.attendanceRecord = new AttendanceRecord(dateProvider);
        addAttendanceTime(attendanceTime);
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime attend(LocalTime todayTime) {
        return attendanceRecord.attend(todayTime);
    }

    public void addAttendanceTime(LocalDateTime attendanceTime) {
        attendanceRecord.add(attendanceTime);
    }
}
