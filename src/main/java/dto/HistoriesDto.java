package dto;

import domain.AbsenceLevel;
import domain.AttendanceHistory;
import domain.AttendanceResult;
import java.util.List;
import java.util.Map;

public record HistoriesDto(
        String username,
        List<HistoryDto> histories,
        Map<AttendanceResult, Integer> attendanceAllResult,
        String classifyAbsenceLevel
) {

    public static HistoriesDto of(String username, List<AttendanceHistory> histories,
                                  Map<AttendanceResult, Integer> attendanceAllResult,
                                  AbsenceLevel classifyAbsenceLevel) {
        List<HistoryDto> historyDtoList =
                histories.stream()
                        .map(history ->
                                new HistoryDto(history.getAttendanceTime(),
                                        history.getAttendanceResult().getResult()))
                        .toList();
        return new HistoriesDto(username, historyDtoList, attendanceAllResult, classifyAbsenceLevel.getLevel());
    }
}
