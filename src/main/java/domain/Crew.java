package domain;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    public void addAttendTime(String inputTime) {
        AttendTime attendTime = new AttendTime(inputTime);
        attendTimes.add(attendTime);
    }

    public String attend(final String inputTime) {
        AttendTime attendTime = new AttendTime(inputTime);
//        attendTimes.add(attendTime);
        return attendTime.checkTime();
    }

    public String getName() {
        return name;
    }

    public List<AttendTime> getAttendTimes() {
        return attendTimes;
    }

    public AttendTime findAttendanceByDate(final int date) {
        return attendTimes.stream()
                .filter(attendTime -> attendTime.getAttendTime().getDayOfMonth() == date)
                .findAny()
                .orElse(null);
    }

    public void deleteAttendance(final int date) {
        IntStream.range(0, attendTimes.size())
                .forEach(i -> {
                    if (attendTimes.get(i).getAttendTime().getDayOfMonth() == date) {
                        attendTimes.remove(i);
                    }
                });
    }
}
