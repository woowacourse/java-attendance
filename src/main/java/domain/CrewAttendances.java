package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import strategy.NowDateStrategy;

public class CrewAttendances {
    private final Map<CrewName, DateCrewAttendanceManager> crewAttendances;
    private final NowDateStrategy nowDateStrategy;

    public CrewAttendances(NowDateStrategy nowDateStrategy) {
        this.nowDateStrategy = nowDateStrategy;
        this.crewAttendances = new HashMap<>();
    }

    public void addAttendance(String nickname, LocalTime attendanceTime) {
        DateCrewAttendanceManager dateCrewAttendanceManager = dateCrewAttendanceManager(nickname);
        dateCrewAttendanceManager.addAttendance(attendanceTime);
    }

    public CrewAttendance crewAttendance(String nickname, LocalDate date) {
        DateCrewAttendanceManager dateCrewAttendanceManager = dateCrewAttendanceManager(nickname);
        return dateCrewAttendanceManager.crewAttendance(date);
    }

    private DateCrewAttendanceManager dateCrewAttendanceManager(String nickname) {
        CrewName crewName = new CrewName(nickname);
        crewAttendances.putIfAbsent(crewName, new DateCrewAttendanceManager(nowDateStrategy));
        return crewAttendances.get(crewName);
    }
}
