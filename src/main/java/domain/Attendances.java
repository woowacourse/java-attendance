package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendances {
    private Map<Crew, List<Attendance>> attendances = new HashMap<>();

    public void addAttendance(Crew crew, Attendance attendance) {
        if (!attendances.containsKey(crew)) {
            attendances.put(crew, new ArrayList<>(List.of(attendance)));
            return;
        }

        attendances.get(crew).add(attendance);
    }

    public List<Attendance> getByCrew(Crew crew) {
        return attendances.get(crew);
    }
}
