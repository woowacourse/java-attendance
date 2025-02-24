package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crew {

    private final Map<LocalDate, TimeAndStatus> records;

    public Crew(List<LocalDateTime> localDateTimes) {
        this.records = initializeRecords(localDateTimes);
    }

    public TimeAndStatus findTimeByDate(LocalDate localDate) {
        if(records.containsKey(localDate)) {
            return records.get(localDate);
        }
        return null;
    }

    public int getAttendanceCount() {
        return records.size();
    }

    public TimeAndStatus attend(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        TimeAndStatus timeAndStatus = createTimeAndStatus(localDateTime);

        records.put(localDate, timeAndStatus);
        return timeAndStatus;
    }

    public TimeAndStatus edit(LocalDateTime newDateTime) {
        LocalDate localDate = newDateTime.toLocalDate();
        TimeAndStatus oldStatus = records.get(localDate);
        TimeAndStatus newStatus = createTimeAndStatus(newDateTime);

        records.put(localDate, newStatus);
        return oldStatus;
    }

    public boolean isAlreadyAttended(LocalDate localDate) {
        return records.containsKey(localDate);
    }

    private Map<LocalDate, TimeAndStatus> initializeRecords(List<LocalDateTime> localDateTimes) {
        Map<LocalDate, TimeAndStatus> result = new HashMap<>();
        for (LocalDateTime localDateTime : localDateTimes) {
            LocalDate localDate = localDateTime.toLocalDate();
            TimeAndStatus timeStatus = createTimeAndStatus(localDateTime);
            result.put(localDate, timeStatus);
        }
        return result;
    }

    private TimeAndStatus createTimeAndStatus(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return new TimeAndStatus(localTime, dayOfWeek);
    }
}
