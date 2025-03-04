package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return dailyRecords.getOrDefault(date, new DailyRecord(date.getDayOfWeek(), LocalTime.MIN));
    }

    public Map<LocalDate, DailyRecord> findRecordsOfDate(LocalDate startDate, LocalDate endDate) {
        return Stream.iterate(startDate, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(startDate, endDate))
            .filter(date -> !Holiday.isHoliday(date))
            .collect(Collectors.toMap(date -> date, this::findRecordByDate, (e1, e2) -> e1,
                LinkedHashMap::new));
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
        for (LocalDateTime dateTime : crewRecords) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            dailyRecords.put(date, new DailyRecord(date.getDayOfWeek(), time));
        }
    }
}