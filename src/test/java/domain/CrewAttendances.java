package domain;

import attendance.domain.Attendance;
import java.util.List;

public class CrewAttendances {

    private final String nickname;
    private final List<Attendance> attendances;

    public CrewAttendances(String nickname, List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = attendances;
    }
}
