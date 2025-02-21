package attendance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public void add(Crew crew) {
        crews.add(crew);
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public Crew findCrew(String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public boolean containsCrew(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.getName().equals(name));
    }

    public AttendanceDetail getCrewAttendanceDetail(String name, LocalDate attendanceDate) {
        return findCrew(name).findAttendanceDetail(attendanceDate);
    }

}
