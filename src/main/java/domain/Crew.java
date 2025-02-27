package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crew {

    private final Map<LocalDate, DailyRecord> dailyRecords;

    public Crew() {
        this.dailyRecords = new HashMap<>();
    }

    public DailyRecord findRecordByDate(LocalDate date) {
        return dailyRecords.get(date);
    }

    public void initializeDailyRecords(List<LocalDateTime> crewRecords) {
        for(LocalDateTime dateTime : crewRecords) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            dailyRecords.put(date, new DailyRecord(date.getDayOfWeek(), time));
        }
    }
}
