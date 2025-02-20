package service;

import domain.Crew;
import domain.CrewGroup;
import domain.attendance.Attendance;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceService {
    public CrewGroup createCrewGroup(Map<String, List<LocalDateTime>> crewInitAttendanceDates) {
        List<String> crewNames = crewInitAttendanceDates.keySet().stream().toList();
        CrewGroup crewGroup = CrewGroup.from(crewNames);
        for (String crewName : crewNames) {
            Crew crew = crewGroup.findCrew(crewName);
            Attendance attendance = crew.getAttendance();
            attendance.fillAttendanceDate();
            crewInitAttendanceDates.get(crewName).forEach(attendance::editAttendanceDateTime);
        }
        return crewGroup;
    }
}
