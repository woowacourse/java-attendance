package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import model.AttendanceType;
import util.AttendanceDateTimeFormatter;

public record AttendanceHistoryDto(
        LocalDate date, LocalTime time, AttendanceType attendanceType
) {
    @Override
    public String toString() {
        return AttendanceDateTimeFormatter.formatLocalDate(date)
                + " "
                + AttendanceDateTimeFormatter.formatLocalTime(time)
                + " ("
                + attendanceType.getName()
                + ")";
    }

    public String toStringWithoutDate() {
        return AttendanceDateTimeFormatter.formatLocalTime(time)
                + " ("
                + attendanceType.getName()
                + ")";
    }

}
