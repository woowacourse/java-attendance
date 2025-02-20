package attendance.dto;

import attendance.model.domain.attendance.AttendanceStatus;
import attendance.model.domain.attendance.CrewAttendance;
import attendance.model.domain.crew.Crew;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendanceLogResponse {

    private final String crewName;
    private final List<AttendanceLogResponse> attendanceLogResponses;
    private final String managementStatus;

    private CrewAttendanceLogResponse(String crewName, List<AttendanceLogResponse> attendanceLogResponses,
                                      String managementStatus) {
        this.crewName = crewName;
        this.attendanceLogResponses = attendanceLogResponses;
        this.managementStatus = managementStatus;
    }

    public static CrewAttendanceLogResponse of(
            Crew crew,
            List<AttendanceLogResponse> attendanceLogResponse,
            CrewAttendance crewAttendance
    ) {
        return new CrewAttendanceLogResponse(crew.getName(), attendanceLogResponse,
                crewAttendance.getManagementStatusName());
    }

    public String getCrewName() {
        return crewName;
    }

    public List<AttendanceLogResponse> getAttendanceLogResponses() {
        return attendanceLogResponses;
    }

    public String getManagementStatus() {
        return managementStatus;
    }

    public Map<String, Integer> getAttendanceStatusStatistics() {
        LinkedHashMap<String, Integer> statistics = new LinkedHashMap<>();

        AttendanceStatus.getNames().forEach(status ->
                statistics.put(status, Math.toIntExact(getAttendanceStatusCount(status)))
        );

        return statistics;
    }

    private long getAttendanceStatusCount(String status) {
        return attendanceLogResponses.stream()
                .map(AttendanceLogResponse::getAttendanceStatus)
                .filter(status::equals)
                .count();
    }
}
