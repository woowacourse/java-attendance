package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        // TODO: 외부에서 한 크루에 대한 데이터를 받으면 이를 날짜별로 분리하여 초기화
    }
}
