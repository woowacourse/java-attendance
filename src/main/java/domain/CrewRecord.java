package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class CrewRecord {

    private final Map<LocalDate, TimeStatus> records;

    public CrewRecord() {
        this.records = new HashMap<>();
    }

    public void attend(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();

        validatePossibleAttendance(localDate);
        records.put(localDate, new TimeStatus(localTime, localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)));
    }

    public void editRecord(LocalDateTime newDateTime) {
        LocalDate localDate = newDateTime.toLocalDate();
        LocalTime localTime = newDateTime.toLocalTime();
        records.put(localDate, new TimeStatus(localTime, localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)));
    }

    private void validatePossibleAttendance(LocalDate localDate) {
        if(records.containsKey(localDate)) {
            throw new IllegalArgumentException("출석을 이미 했습니다.");
        }
    }
}
