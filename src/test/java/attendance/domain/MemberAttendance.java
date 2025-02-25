package attendance.domain;

import java.util.List;

public class MemberAttendance {
    private final Crew crew;
    private final List<Attendance> attendances;

    public MemberAttendance(Crew crew, List<Attendance> attendances) {
        this.crew = null;
        this.attendances = null;
    }

    public Crew getCrew() {
        return crew;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
