package dto;

import domain.AttendanceDate;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceDetails(
        LocalDate localDate,
        LocalTime localTime,
        int attendanceStatusCode
) {
    public static AttendanceDetails of(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime,
                                       final AttendanceStatus attendanceStatus) {
        return new AttendanceDetails(attendanceDate.getLocalDate(), attendanceTime.getLocaltime(), attendanceStatus.getCode());
    }
}
