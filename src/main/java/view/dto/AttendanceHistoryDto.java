package view.dto;

import domain.AttendanceStatus;
import domain.CrewAttendance;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public record AttendanceHistoryDto(LocalDate attendanceDate, LocalTime attendanceTime, String attendanceStatus,
                                   boolean isAbsence) {

    public static List<AttendanceHistoryDto> from(Map<LocalDate, CrewAttendance> dateCrewAttendance) {
        List<AttendanceHistoryDto> attendanceHistoryDtos = new ArrayList<>();
        for (Entry<LocalDate, CrewAttendance> crewAttendanceEntry : dateCrewAttendance.entrySet()) {
            LocalDate attendanceDate = crewAttendanceEntry.getKey();
            CrewAttendance crewAttendance = crewAttendanceEntry.getValue();
            String attendanceStatusMessage = crewAttendance.attendanceStatusMessage();
            LocalTime attendanceTime = crewAttendance.attendanceTime();
            boolean isAbsence = crewAttendance.attendanceStatus() == AttendanceStatus.ABSENCE;
            attendanceHistoryDtos.add(of(attendanceDate, attendanceTime, attendanceStatusMessage, isAbsence));
        }
        return attendanceHistoryDtos;
    }

    private static AttendanceHistoryDto of(LocalDate attendanceDate, LocalTime attendanceTime,
                                           String attendanceStatusMessage, boolean isAbsence) {
        return new AttendanceHistoryDto(attendanceDate, attendanceTime, attendanceStatusMessage, isAbsence);
    }
}
