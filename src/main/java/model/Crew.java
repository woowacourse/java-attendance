package model;

import java.time.LocalDate;
import java.util.Map;

public class Crew {

    private final Map<LocalDate, DailyRecord> dailyRecords;

    public Crew(Map<LocalDate, DailyRecord> dailyRecords) {
        this.dailyRecords = dailyRecords;
    }
}
