package dto;

import domain.AbsenceLevel;
import domain.AttendanceHistory;
import java.util.List;
import java.util.Map;

public record HistoriesDto(
        String username,
        List<HistoryDto> histories,
        Map<String, Integer> attendanceAllResult,
        String classifyAbsenceLevel
) {

    public static HistoriesDto of(String username, List<AttendanceHistory> histories, Map<String, Integer> attendanceAllResult,
                                  AbsenceLevel classifyAbsenceLevel) {
        List<HistoryDto> historyDtoList = histories.stream().map(history -> {
            return new HistoryDto(history.getAttendanceTime(), history.getAttendanceResult());
        }).toList();
        return new HistoriesDto(username, historyDtoList, attendanceAllResult, classifyAbsenceLevel.getLevel());
    }
}
