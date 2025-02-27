package dto;

import domain.Attendance;
import java.util.List;

public record AttendanceHistory(List<Attendance> sortedValue) {
}
