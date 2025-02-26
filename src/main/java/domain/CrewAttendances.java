package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import strategy.CurrentDateGenerateStrategy;

public class CrewAttendances {
    private final Map<CrewName, DateCrewAttendanceManager> crewAttendances;
    private final CurrentDateGenerateStrategy currentDateGenerateStrategy;

    public CrewAttendances(CurrentDateGenerateStrategy currentDateGenerateStrategy) {
        this.currentDateGenerateStrategy = currentDateGenerateStrategy;
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
        crewAttendances.putIfAbsent(crewName, new DateCrewAttendanceManager(currentDateGenerateStrategy));
        return crewAttendances.get(crewName);
    }

    public void modifyAttendance(String nickname, LocalDate modifyDate, LocalTime modifyTime) {
        DateCrewAttendanceManager dateCrewAttendanceManager = dateCrewAttendanceManager(nickname);
        dateCrewAttendanceManager.modifyAttendance(modifyDate, modifyTime);
    }

    public List<CrewAttendanceHistory> crewAttendancesHistory() {
        return null;
    }
}
