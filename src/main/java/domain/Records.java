package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Records {

    private final Map<LocalDate, TimeAndStatus> records;

    public Records(List<LocalDateTime> localDateTimes) {
        this.records = initializeRecords(localDateTimes);
    }

    public TimeAndStatus findByDate(LocalDate localDate) {
        return records.get(localDate);
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
        TimeAndStatus timeAndStatus = createTimeAndStatus(newDateTime);
        records.put(localDate, timeAndStatus);

        return timeAndStatus;
    }

    public boolean hasSameDate(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
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
        String dayOfWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        return new TimeAndStatus(localTime, dayOfWeek);
    }
}
