package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceStorage {
    private final Set<Crew> crews;
    private final List<AttendanceHistory> attendanceHistories;

    public AttendanceStorage() {
        this.crews = new HashSet<>();
        this.attendanceHistories = new ArrayList<>();
    }

    public void addCrew(Crew crew) {
        crews.add(crew);
    }

    public void addHistory(AttendanceHistory attendanceHistory) {
        attendanceHistories.add(attendanceHistory);
    }

    public boolean containsSameNickname(String nickname) {
        return crews.contains(Crew.from(nickname));
    }
}
