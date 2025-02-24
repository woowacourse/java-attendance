package domain;

import static domain.AttendanceStatus.ABSENCE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crew {

    private final Map<LocalDate, DailyRecord> records;

    public Crew(List<LocalDateTime> localDateTimes) {
        this.records = initializeRecords(localDateTimes);
    }

    public DailyRecord findTimeByDate(LocalDate localDate) {
        if(records.containsKey(localDate)) {
            return records.get(localDate);
        }
        return null;
    }

    public AttendanceStatus findStatusByDate(LocalDate date) {
        DailyRecord status = findTimeByDate(date);
        if (status == null) {
            return ABSENCE;
        }
        return status.getStatus();
    }

    public int getAttendanceCount() {
        return records.size();
    }

    public DailyRecord attend(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        DailyRecord dailyRecord = createTimeAndStatus(localDateTime);

        records.put(localDate, dailyRecord);
        return dailyRecord;
    }

    public DailyRecord edit(LocalDateTime newDateTime) {
        LocalDate localDate = newDateTime.toLocalDate();
        DailyRecord oldStatus = records.get(localDate);
        DailyRecord newStatus = createTimeAndStatus(newDateTime);

        records.put(localDate, newStatus);
        return oldStatus;
    }

    public boolean isAlreadyAttended(LocalDate localDate) {
        return records.containsKey(localDate);
    }

    private Map<LocalDate, DailyRecord> initializeRecords(List<LocalDateTime> localDateTimes) {
        Map<LocalDate, DailyRecord> result = new HashMap<>();
        for (LocalDateTime localDateTime : localDateTimes) {
            LocalDate localDate = localDateTime.toLocalDate();
            DailyRecord timeStatus = createTimeAndStatus(localDateTime);
            result.put(localDate, timeStatus);
        }
        return result;
    }

    private DailyRecord createTimeAndStatus(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return new DailyRecord(localTime, dayOfWeek);
    }
}
