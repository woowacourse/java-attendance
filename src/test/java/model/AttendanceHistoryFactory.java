package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AttendanceHistoryFactory {

    public static AttendanceHistory make(final Stream<LocalDateTime> dateTimes) {
        return new AttendanceHistory(
                dateTimes.collect(Collectors.toMap(
                        dateTime -> new AttendanceDate(LocalDate.from(dateTime)),
                        dateTime -> new AttendanceTime(LocalTime.from(dateTime)))
                )
        );
    }
}
