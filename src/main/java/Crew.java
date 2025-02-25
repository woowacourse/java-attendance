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

    public AttendTime attend(LocalDateTime localDateTime) {
        AttendTime attendTime =new AttendTime(localDateTime);
        attendTimes.add(attendTime);
        return attendTime;
    }

    public String getNickname() {
        return nickname;
    }

    public List<AttendTime> getAttendTimes() {
        return attendTimes;
    }

//    public AttendTime findAttendanceByDate(int date) {
//        return attendTimes.stream()
//                .filter(attendTime -> attendTime.checkSameDate(date))
//                .findAny()
//                .orElseThrow(() -> new IllegalArgumentException("없는 날짜입니다."));
//    }
}
