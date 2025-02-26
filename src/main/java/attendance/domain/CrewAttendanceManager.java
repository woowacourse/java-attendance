package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CrewAttendanceManager {

    private final Map<Crew, AttendanceHistories> crewAttendanceInfo;

    private CrewAttendanceManager(Map<String, LocalDateTime> fileReadResult) {
        this.crewAttendanceInfo = new HashMap<>();
        initCrewAttendanceInfo(fileReadResult);
    }

    public static CrewAttendanceManager create(Map<String, LocalDateTime> fileReadResult) {
        return new CrewAttendanceManager(fileReadResult);
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

    private void initCrewAttendanceInfo(Map<String, LocalDateTime> fileReadResult) {
        for (String name : fileReadResult.keySet()) {
            Crew crew = Crew.from(name);
            LocalDateTime fileReadDateTime = fileReadResult.get(name);
            AttendanceHistory attendanceHistory = AttendanceHistory.from(fileReadDateTime);
            addCrewAttendanceInfo(crew, attendanceHistory);
        }
    }
}
