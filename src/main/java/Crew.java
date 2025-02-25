import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String nickname;
    private List<AttendTime> attendTimes;

    public Crew(String nickname) {
        this.nickname = nickname;
        this.attendTimes = new ArrayList<>();
    }

    public Crew(String nickname, LocalDateTime localDateTime) {
        this.nickname = nickname;
        this.attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime(localDateTime));
    }

    public void attend(LocalDateTime localDateTime) {
        attendTimes.add(new AttendTime(localDateTime));
    }

    public String getNickname() {
        return nickname;
    }

    public List<AttendTime> getAttendTimes() {
        return attendTimes;
    }
}
