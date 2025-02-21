package attendance.dto;

import attendance.model.domain.attendance.AttendanceStatus;
import attendance.model.domain.attendance.CrewAttendance;
import attendance.model.domain.crew.Crew;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrewAttendanceLogResponse {

    private final String crewName;
    private final List<AttendanceLogResponse> attendanceLogResponses;
    private final String managementStatus;
    private final Map<String, Integer> attendanceStatusStatistics;

    private CrewAttendanceLogResponse(
            String crewName,
            List<AttendanceLogResponse> attendanceLogResponses,
            String managementStatus,
            Map<String, Integer> attendanceStatusStatistics
    ) {

        this.crewName = crewName;
        this.attendanceLogResponses = attendanceLogResponses;
        this.managementStatus = managementStatus;
        this.attendanceStatusStatistics = attendanceStatusStatistics;
    }

    public static CrewAttendanceLogResponse of(
            Crew crew,
            List<AttendanceLogResponse> attendanceLogResponse,
            CrewAttendance crewAttendance
    ) {

        return new CrewAttendanceLogResponse(
                crew.getName(),
                attendanceLogResponse,
                crewAttendance.getManagementStatusName(),
                getAttendanceStatusStatistics(attendanceLogResponse)
        );
    }

    public static Map<String, Integer> getAttendanceStatusStatistics(
            List<AttendanceLogResponse> attendanceLogResponses
    ) {
        return AttendanceStatus.getNames().stream()
                .collect(Collectors.toMap(
                        status -> status,
                        status -> Math.toIntExact(getAttendanceStatusCount(attendanceLogResponses, status)),
                        (oldStatus, newStatus) -> oldStatus,
                        LinkedHashMap::new)
                );
    }

    private static long getAttendanceStatusCount(List<AttendanceLogResponse> attendanceLogResponses, String status) {
        return attendanceLogResponses.stream()
                .map(AttendanceLogResponse::getAttendanceStatus)
                .filter(status::equals)
                .count();
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
        return attendanceStatusStatistics;
    }
}
