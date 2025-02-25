package attendance;

import java.time.LocalDateTime;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Attendances> crewAttendances;

    public AttendanceBook(Map<String, Attendances> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public Attendance attend(String nickname, LocalDateTime dateTime) {
        if (!crewAttendances.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        Attendances attendances = crewAttendances.get(nickname);
        Attendance attendance = attendances.add(dateTime);
        crewAttendances.put(nickname, attendances);
        return attendance;
    }
}
