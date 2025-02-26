package service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ModifyAttendanceRecordResponse(
        String date,
        TimeStatus before,
        TimeStatus after
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일");

    public static ModifyAttendanceRecordResponse of(LocalDate date, TimeStatus before, TimeStatus after) {
        return new ModifyAttendanceRecordResponse(date.format(DATE_FORMATTER), before, after);
    }

    public record TimeStatus(
            String time,
            String status
    ) {
        private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

        public static TimeStatus createAbsentTimeStatus() {
            return new TimeStatus("--:--", "결석");
        }

        public static TimeStatus of(LocalTime time, String status) {
            return new TimeStatus(time.format(TIME_FORMATTER), status);
        }
    }
}
