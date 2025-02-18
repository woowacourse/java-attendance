package attendance.model;

import java.util.HashSet;
import java.util.Set;

public class Attendances {

    private final CrewGroup crewGroup;
    private final Set<Attendance> attendances;

    public Attendances(CrewGroup crewGroup, Set<Attendance> attendances) {
        this.crewGroup = crewGroup;
        this.attendances = new HashSet<>(attendances);
    }

    public void validateAttendance(String nickname) {
        boolean isNotExistsCrew = !crewGroup.contains(nickname);
        if (isNotExistsCrew) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public void add(Attendance attendance) {
        if (attendances.contains(attendance)) {
            throw new IllegalArgumentException("크루는 같은 날에 또 출석할 수 없습니다.");
        }
        attendances.add(attendance);
    }
}
