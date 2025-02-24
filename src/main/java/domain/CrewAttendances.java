package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class CrewAttendances {
    private final Map<String, DateCrewAttendanceManager> crewAttendances;

    public CrewAttendances() {
        this.crewAttendances = new HashMap<>();
    }

    public void addAttendance(String nickname, LocalTime attendanceTime) {
        DateCrewAttendanceManager dateCrewAttendanceManager = getDateCrewAttendanceManager(nickname);
        LocalDate attendanceDate = LocalDate.of(2024, 12, 3);
        dateCrewAttendanceManager.addAttendance(attendanceTime, attendanceDate);
    }

    public CrewAttendance crewAttendance(String nickname, LocalDate date) {
        DateCrewAttendanceManager dateCrewAttendanceManager = getDateCrewAttendanceManager(nickname);
        return dateCrewAttendanceManager.crewAttendance(date);
    }

    private DateCrewAttendanceManager getDateCrewAttendanceManager(String nickname) {
        crewAttendances.putIfAbsent(nickname, new DateCrewAttendanceManager());
        return crewAttendances.get(nickname);
    }
}
