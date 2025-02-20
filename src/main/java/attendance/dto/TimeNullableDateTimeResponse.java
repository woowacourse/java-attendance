package attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class TimeNullableDateTimeResponse {

    private final LocalDate date;
    private final LocalTime time;

    private TimeNullableDateTimeResponse(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public static TimeNullableDateTimeResponse fromDateTime(LocalDateTime dateTime) {
        return new TimeNullableDateTimeResponse(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public static TimeNullableDateTimeResponse fromDate(LocalDate date) {
        return new TimeNullableDateTimeResponse(date, null);
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<LocalTime> getTime() {
        return Optional.ofNullable(time);
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.ofNullable(time).map(time -> LocalDateTime.of(date, time));
    }
}
