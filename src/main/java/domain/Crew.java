package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private final String name;
    private final List<LocalDateTime> attendTimes;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Crew(String nickname, String attendTime) {
        this.name = nickname;
        this.attendTimes = new ArrayList<>();
        attendTimes.add(LocalDateTime.parse(attendTime, formatter));
    }

    public void attend(final String s) {
        attendTimes.add(LocalDateTime.parse(s, formatter));
    }

    public String getName() {
        return name;
    }

    public List<LocalDateTime> getAttendTimes() {
        return attendTimes;
    }

}
