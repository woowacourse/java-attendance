package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    //목적: 크루.

    private final String name;
    private final List<AttendTime> attendTimes;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Crew(String nickname, String attendTime) {
        this.name = nickname;
        this.attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime(attendTime));
    }

    public void addAttendTime(String inputTime){
        AttendTime attendTime = new AttendTime(inputTime);
        attendTimes.add(attendTime);
    }
    public String attend(final String inputTime) {
        AttendTime attendTime = new AttendTime(inputTime);
//        attendTimes.add(attendTime);
        return  attendTime.checkTime();
    }

    public String getName() {
        return name;
    }

    public List<AttendTime> getAttendTimes() {

        return attendTimes;
    }

}
