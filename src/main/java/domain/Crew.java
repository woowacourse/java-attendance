package domain;

import static domain.AttendanceStatus.ABSENCE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Crew {

    private final Map<LocalDate, DailyRecord> records;

    public Crew(List<LocalDateTime> localDateTimes) {
        this.records = initializeRecords(localDateTimes);
    }

    public Optional<DailyRecord> findRecordByDate(LocalDate localDate) {
        return Optional.ofNullable(records.get(localDate));
    }

    public AttendanceStatus findStatusByDate(LocalDate date) {
        return findRecordByDate(date)
            .map(DailyRecord::getStatus)
            .orElse(ABSENCE);
    }

    public int getAttendanceCount() {
        return records.size();
    }

    public DailyRecord attend(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        DailyRecord dailyRecord = createDailyRecord(localDateTime);

        records.put(localDate, dailyRecord);
        return dailyRecord;
    }

    public DailyRecord edit(LocalDateTime newDateTime) {
        LocalDate localDate = newDateTime.toLocalDate();
        DailyRecord oldRecord = records.get(localDate);
        DailyRecord newRecord = createDailyRecord(newDateTime);

        records.put(localDate, newRecord);
        return oldRecord;
    }

    public boolean isAlreadyAttended(LocalDate localDate) {
        return records.containsKey(localDate);
    }

    private Map<LocalDate, DailyRecord> initializeRecords(List<LocalDateTime> localDateTimes) {
        Map<LocalDate, DailyRecord> result = new HashMap<>();
        for (LocalDateTime localDateTime : localDateTimes) {
            LocalDate localDate = localDateTime.toLocalDate();
            DailyRecord record = createDailyRecord(localDateTime);
            result.put(localDate, record);
        }
        return result;
    }

    private DailyRecord createDailyRecord(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return new DailyRecord(localTime, dayOfWeek);
    }
}
