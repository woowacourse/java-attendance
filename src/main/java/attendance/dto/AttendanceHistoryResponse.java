package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Risk;

public record AttendanceHistoryResponse(
    String name,
    List<InnerAttendanceHistory> histories,
    Map<AttendanceStatus, Integer> statistics,
    Risk risk
) {

    // TODO 리팩토링
    public static AttendanceHistoryResponse of(LocalDate today, Crew crew) {
        List<AttendanceHistoryResponse.InnerAttendanceHistory> histories = new ArrayList<>();
        for (LocalDate day = today.withDayOfMonth(1); day.isBefore(today); day = day.plusDays(1)) {
            histories.add(AttendanceHistoryResponse.InnerAttendanceHistory.of(day, crew));
        }
        return new AttendanceHistoryResponse(
            crew.getName(),
            histories,
            crew.getAttendanceStatistics(today),
            crew.getRisk(today));
    }

    public record InnerAttendanceHistory(
        LocalDate date,
        LocalTime time,
        AttendanceStatus status
    ) {

        public static InnerAttendanceHistory of(LocalDate date, Crew crew) {
            return new InnerAttendanceHistory(
                date,
                crew.getAttendanceTimeOf(date),
                crew.getAttendanceStatusOf(date));
        }
    }
}
