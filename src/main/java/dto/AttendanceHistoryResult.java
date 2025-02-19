package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.AttendanceStatus;
import domain.History;
import domain.Manage;

public record AttendanceHistoryResult(
    List<InnerHistory> history,
    Map<AttendanceStatus, Integer> statusCounter,
    Manage manage
) {
    public static AttendanceHistoryResult of(List<History> history, Map<AttendanceStatus,
        Integer> statusCounter, Manage manage) {
        List<InnerHistory> innerHistory = new ArrayList<>();
        history.forEach(h -> {
            innerHistory.add(InnerHistory.of(h.date(), h.time(), h.status(), h.isChecked()));
        });
        return new AttendanceHistoryResult(innerHistory, statusCounter, manage);
    }

    public record InnerHistory(
        LocalDate date,
        LocalTime time,
        AttendanceStatus status
    ) {

        public static InnerHistory of(LocalDate date, LocalTime time, AttendanceStatus status, Boolean isChecked) {
            return new InnerHistory(
                date,
                isChecked ? null : time,
                status
            );
        }
    }
}
