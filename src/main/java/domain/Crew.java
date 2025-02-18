package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final List<Attandance> attendance = new ArrayList<>();

    public void addAttendance(LocalDateTime date) {
        attendance.add(new Attandance(date));
    }
}
