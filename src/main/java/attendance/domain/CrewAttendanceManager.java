package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CrewAttendanceManager {

    private final Map<Crew, AttendanceHistories> crewAttendanceInfo;

    private CrewAttendanceManager() {
        this.crewAttendanceInfo = new HashMap<>();
    }

    public static CrewAttendanceManager create() {
        return new CrewAttendanceManager();
    }

    public void insertAbsenceIfNotExistsAttendance(LocalDate localDate) {
        for (Crew crew : crewAttendanceInfo.keySet()) {
            AttendanceHistories attendanceHistories = crewAttendanceInfo.get(crew);
            attendanceHistories.calculateHistories(localDate);
        }
    }

    public void addCrewAttendanceInfo(Crew crew, AttendanceHistory attendanceHistory) {
        if (!crewAttendanceInfo.containsKey(crew)) {
            crewAttendanceInfo.put(crew, AttendanceHistories.create());
        }
        AttendanceHistories attendanceHistories = crewAttendanceInfo.get(crew);
        attendanceHistories.addAttendanceHistory(attendanceHistory);
    }

    public AttendanceHistories findAttendanceHistoriesByCrew(Crew crew) {
        return crewAttendanceInfo.get(crew);
    }
}
