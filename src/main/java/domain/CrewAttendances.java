package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import strategy.NowDateStrategy;

public class CrewAttendances {
    private final Map<String, DateCrewAttendanceManager> crewAttendances;
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
        crewAttendances.putIfAbsent(nickname, new DateCrewAttendanceManager(nowDateStrategy));
        return crewAttendances.get(nickname);
    }
}
