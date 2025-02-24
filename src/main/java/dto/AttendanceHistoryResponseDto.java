package dto;

import java.util.List;
import java.util.Map;

import domain.AttendanceStatus;
import domain.Manage;

public record AttendanceHistoryResponseDto(
    String nickname,
    List<HistoryDto> histories,
    Map<AttendanceStatus, Integer> statusCounter,
    Manage manage
) {

}
