package dto;

import domain.AbsencePolicyStatistics;
import java.util.List;

public record AttendanceHistoryDto(String name, List<AttendanceRecord> records,
                                   AbsencePolicyStatistics absencePolicyStatistics, String attendanceState) {
}
