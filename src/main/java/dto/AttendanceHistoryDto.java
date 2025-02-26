package dto;

import domain.Crew;
import java.util.List;

public record AttendanceHistoryDto(Crew crew, List<AttendanceRecord> records,
                                   AttendanceStatus attendanceStatus) {
}
