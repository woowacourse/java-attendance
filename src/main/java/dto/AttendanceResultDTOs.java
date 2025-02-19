package dto;

import java.util.List;

public record AttendanceResultDTOs(
        String name,
        List<AttendanceResultDTO> attendanceResults,
        int attendCount,
        int lateCount,
        int absentCount,
        String interviewee
) {
}
