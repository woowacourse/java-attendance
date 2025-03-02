import domain.AttendanceRecord;
import domain.DateProvider;

public class Crew {

    private final String nickname;
    private final AttendanceRecord attendanceRecord;

    public Crew(String nickname, String time, DateProvider dateProvider) {
        this.nickname = nickname;
        this.attendanceRecord = new AttendanceRecord(dateProvider);
        this.attendanceRecord.attend(time);
    }

    public String getNickname() {
        return nickname;
    }
}
