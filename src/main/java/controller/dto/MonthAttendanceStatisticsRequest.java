package controller.dto;

import java.time.LocalDate;

public record MonthAttendanceStatisticsRequest(
        String nickname,
        LocalDate from,
        LocalDate to
) {

}
