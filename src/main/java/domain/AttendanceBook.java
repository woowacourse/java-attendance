package domain;

import java.time.LocalDateTime;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Attendances> crewAttendances;

    public AttendanceBook(Map<String, Attendances> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public void addAttendanceForCrew(String nickname, LocalDateTime dateTime) {
        Attendances attendances = getCrewAttendances(nickname);
        attendances.addAttendance(dateTime);
    }

    private Attendances getCrewAttendances(String nickname) {
        if (!crewAttendances.containsKey(nickname)) {
            throw new IllegalArgumentException("존재하지 않는 닉네임입니다.");
        }
        return crewAttendances.get(nickname);
    }

    public Map<String, Attendances> getCrewAttendances() {
        return crewAttendances;
    }
}
