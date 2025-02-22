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
            final String crewName,
            final List<AttendanceLogResponse> attendanceLogResponses,
            final String managementStatus,
            final Map<String, Integer> attendanceStatusStatistics
    ) {

        this.crewName = crewName;
        this.attendanceLogResponses = attendanceLogResponses;
        this.managementStatus = managementStatus;
        this.attendanceStatusStatistics = attendanceStatusStatistics;
    }

    public static CrewAttendanceLogResponse of(
            final Crew crew,
            final List<AttendanceLogResponse> attendanceLogResponse,
            final CrewAttendance crewAttendance
    ) {

        return new CrewAttendanceLogResponse(
                crew.getName(),
                attendanceLogResponse,
                crewAttendance.getManagementStatusName(),
                getAttendanceStatusStatistics(attendanceLogResponse)
        );
    }

    private static Map<String, Integer> getAttendanceStatusStatistics(
            final List<AttendanceLogResponse> attendanceLogResponses
    ) {

        return AttendanceStatus.getNames().stream()
                .collect(Collectors.toMap(
                        status -> status,
                        status -> Math.toIntExact(getAttendanceStatusCount(attendanceLogResponses, status)),
                        (oldStatus, newStatus) -> oldStatus,
                        LinkedHashMap::new)
                );
    }

    private static long getAttendanceStatusCount(
            final List<AttendanceLogResponse> attendanceLogResponses,
            final String status
    ) {

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
