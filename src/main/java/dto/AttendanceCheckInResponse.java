package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import model.AttendanceType;

public record AttendanceCheckInResponse(
        LocalDate checkInDate,
        LocalTime checkInTime,
        AttendanceType attendanceType
) {

    public static AttendanceCheckInResponse convertToAttendanceCheckInResponse(LocalDate checkInDate,
                                                                               LocalTime checkInTime,
                                                                               AttendanceType attendanceType
    ) {
        return new AttendanceCheckInResponse(checkInDate, checkInTime, attendanceType);
    }
}
