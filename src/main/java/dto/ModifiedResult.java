package dto;

import domain.AttendanceStatus;
import domain.Crew;
import java.time.LocalDate;
import util.Formatter;

public record ModifiedResult(
        LocalDate date,
        TimeAttendanceStatus before,
        TimeAttendanceStatus after
) {

    public record TimeAttendanceStatus(
            String time,
            AttendanceStatus status
    ) {
        public static TimeAttendanceStatus of(Crew crew, LocalDate date) {
            if (!crew.attendanceTimeExists(date)) {
                return new TimeAttendanceStatus("--:--", AttendanceStatus.ABSENT);
            }

            return new TimeAttendanceStatus(
                    crew.getAttendanceTimeByDate(date).time().format(Formatter.TIME_FORMATTER),
                    crew.getAttendanceStatusByDate(date)
            );
        }
    }
}
