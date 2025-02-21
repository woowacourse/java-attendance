package dto;

import domain.Attendance;
import java.util.List;

public record AttendanceData(List<Attendance> value) {
}
