package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Risk;

public record AttendanceHistoryResponse(
    String name,
    List<InnerAttendanceHistory> histories,
    Map<AttendanceStatus, Integer> statistics,
    Risk risk
) {

    public static AttendanceHistoryResponse of(LocalDate today, Crew crew) {
        return new AttendanceHistoryResponse(
            crew.getName(),
            IntStream.range(1, today.getDayOfMonth())
                .mapToObj(today::withDayOfMonth)
                .map(day -> InnerAttendanceHistory.of(day, crew))
                .toList(),
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
