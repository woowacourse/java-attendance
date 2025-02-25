package attendance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private String nickname;
    private List<Attendance> attendances;

    public Crew(String nickname) {
        this.nickname = nickname;
        this.attendances = new ArrayList<Attendance>();
    }

    public Attendance addAttendance(LocalDateTime attendanceDateTime) {
        Attendance attendance = new Attendance(attendanceDateTime);
        attendances.add(attendance);
        return attendance;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
