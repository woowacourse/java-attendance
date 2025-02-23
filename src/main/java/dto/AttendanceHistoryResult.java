package dto;

import java.util.List;
import java.util.Map;

import domain.AttendanceStatus;
import domain.Manage;

public record AttendanceHistoryResult(
    String nickname,
    List<HistoryDto> histories,
    Map<AttendanceStatus, Integer> statusCounter,
    Manage manage
) {

}
