package dto;

import domain.AttendanceAnalyze;
import domain.AttendanceHistory;
import java.util.List;

public record AttendanceHistoriesDto(
        String username,
        List<AttendanceHistoryDto> histories,
        int attendanceCount,
        int lateCount,
        int absenceCount,
        String status
) {
    public static AttendanceHistoriesDto of(String username,
                                            AttendanceAnalyze analyze) {
        List<AttendanceHistory> histories = analyze.getAttendanceHistories();
        List<AttendanceHistoryDto> historiesDto = histories.stream().map(AttendanceHistoryDto::from).toList();
        int attendanceCount = analyze.getAttendanceCount();
        int lateCount = analyze.getLateCount();
        int absenceCount = analyze.getAbsenceCount();
        String status = analyze.getAttendanceStatus().getStatus();
        return new AttendanceHistoriesDto(username, historiesDto, attendanceCount, lateCount, absenceCount, status);
    }
}
