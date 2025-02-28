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

    public int countRecord() {
        return dailyRecords.size();
    }

    public boolean hasDate(LocalDate date) {
        return dailyRecords.containsKey(date);
    }

    public DailyRecord findRecordByDate(LocalDate date) {
        return dailyRecords.get(date);
    }

    public DailyRecord addDailyRecord(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        DailyRecord newRecord = new DailyRecord(date.getDayOfWeek(), time);
        dailyRecords.put(date, newRecord);
        return newRecord;
    }

    public DailyRecord updateDailyRecord(LocalDateTime editedDateTime) {
        LocalDate date = editedDateTime.toLocalDate();
        LocalTime time = editedDateTime.toLocalTime();

        DailyRecord newRecord = new DailyRecord(date.getDayOfWeek(), time);
        dailyRecords.put(date, newRecord);
        return newRecord;
    }

    public void initializeDailyRecords(List<LocalDateTime> crewRecords) {
        for(LocalDateTime dateTime : crewRecords) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            dailyRecords.put(date, new DailyRecord(date.getDayOfWeek(), time));
        }
    }
}