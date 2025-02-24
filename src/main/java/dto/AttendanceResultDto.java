package dto;

import domain.AttendanceState;
import java.time.LocalDateTime;

public record AttendanceResultDto(LocalDateTime localDateTime, AttendanceState attendanceState) {

    public boolean isSame(final AttendanceState state) {
        return attendanceState.equals(state);
    }
}
