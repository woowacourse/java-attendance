package dto;

import domain.Attendance;
import java.util.List;

public record AttendanceLog(List<Attendance> sortedValues) {
}
