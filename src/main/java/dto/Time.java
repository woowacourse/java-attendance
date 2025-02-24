package dto;

import domain.AttendanceState;
import java.time.LocalTime;

public record Time(
        LocalTime time,
        AttendanceState state) {
}
