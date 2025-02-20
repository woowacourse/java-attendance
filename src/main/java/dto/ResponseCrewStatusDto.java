package dto;

import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceState;
import util.DateTimeUtil;

public record ResponseCrewStatusDto(String crewDateTime, AttendanceState attendanceState) {
    public static ResponseCrewStatusDto from(AttendanceDate attendanceDate) {
        String crewDateTime = DateTimeUtil.convertLocalDateTimeToString(attendanceDate.checkAttendanceTime());
        AttendanceState attendanceState = attendanceDate.calculateAttendanceState();
        return new ResponseCrewStatusDto(crewDateTime, attendanceState);
    }
}
