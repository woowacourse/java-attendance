package dto;

import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceState;
import presentation.DateTimeUtil;

public record ResponseCrewStatusDto(String crewDate, String crewTime, AttendanceState attendanceState) {
    public static ResponseCrewStatusDto from(AttendanceDate attendanceDate) {
        String crewDate = DateTimeUtil.convertLocalDateToString(attendanceDate.convertLocalDate());
        String crewTime = DateTimeUtil.convertLocalDateTimeToTimeString(attendanceDate.getAttendanceTime());
        AttendanceState attendanceState = attendanceDate.calculateAttendanceState();
        return new ResponseCrewStatusDto(crewDate, crewTime, attendanceState);
    }
}
