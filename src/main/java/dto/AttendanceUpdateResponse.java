package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import model.AttendanceType;

public record AttendanceUpdateResponse(
        LocalDate date,
        LocalTime previousTime,
        AttendanceType previousAttendanceType,
        LocalTime updateTime,
        AttendanceType updateAttendanceType
) {

    public static AttendanceUpdateResponse convertToAttendanceUpdateResponse(LocalDate date,
                                                                             LocalTime previousTime,
                                                                             AttendanceType previousAttendanceType,
                                                                             LocalTime updateTime,
                                                                             AttendanceType updateAttendanceType
    ) {
        return new AttendanceUpdateResponse(
                date,
                previousTime,
                previousAttendanceType,
                updateTime,
                updateAttendanceType
        );
    }
}
