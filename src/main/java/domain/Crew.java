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

    public DailyRecord findRecordByDate(LocalDate date) {
        return dailyRecords.get(date);
    }

    public DailyRecord addDailyRecord(LocalDateTime dateTime) {
        // TODO: 하루 데이터 저장
        return null;
    }

    public void initializeDailyRecords(List<LocalDateTime> crewRecords) {
        for(LocalDateTime dateTime : crewRecords) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            dailyRecords.put(date, new DailyRecord(date.getDayOfWeek(), time));
        }
    }
}
