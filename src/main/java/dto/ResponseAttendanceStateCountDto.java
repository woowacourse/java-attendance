package dto;

import domain.attendance.Attendance;
import domain.attendance.AttendanceWarning;

public record ResponseAttendanceStateCountDto(int attendanceCount,
                                              int tardyCount,
                                              int absenceCount,
                                              AttendanceWarning attendanceWarning) {
    public static ResponseAttendanceStateCountDto from(Attendance crewAttendance) {
        return new ResponseAttendanceStateCountDto(
                crewAttendance.countAttendance(),
                crewAttendance.countTardy(),
                crewAttendance.countAbsence(),
                AttendanceWarning.determineAttendanceWarning(crewAttendance.countAbsenceIncludingTardy()));
    }
}
