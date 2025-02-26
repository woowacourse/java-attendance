package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Attendances> crewsAttendances = new HashMap<>();

    public void recordAttendance(String nickname, Attendance attendance) {
        if (crewsAttendances.containsKey(nickname)) {
            crewsAttendances.get(nickname).add(attendance);
            return;
        }
        crewsAttendances.put(nickname, new Attendances());
        crewsAttendances.get(nickname).add(attendance);
    }

    public Attendances getAttendances(String nickname) {
        return crewsAttendances.get(nickname);
    }
}
