package dto;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import java.util.List;

public record ResponseCrewAttendanceStateDto(String crewName,
                                             List<ResponseCrewStatusDto> crewAttendanceStateDtos,
                                             ResponseAttendanceStateCountDto attendanceStateCountDto) {
    public static ResponseCrewAttendanceStateDto of(String crewName, Attendance crewAttendance) {
        List<ResponseCrewStatusDto> crewAttendanceStateDtos = crewAttendance.getAttendanceDates().stream()
                .map(attendanceDate -> ResponseCrewStatusDto.from(attendanceDate)).toList();

        ResponseAttendanceStateCountDto attendanceStateCountDto = ResponseAttendanceStateCountDto.from(crewAttendance);

        return new ResponseCrewAttendanceStateDto(crewName, crewAttendanceStateDtos, attendanceStateCountDto);
    }
}
