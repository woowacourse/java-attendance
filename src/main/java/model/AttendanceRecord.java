package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceRecord {

    List<LocalDateTime> timeRecords = new ArrayList<>();

    public AttendanceRecord(LocalDateTime localDateTime) {
        timeRecords.add(localDateTime);
    }

    public ArrayList<LocalDateTime> getTimeRecords() {
        return (ArrayList<LocalDateTime>) timeRecords;
    }

    public void addTime(LocalDateTime localDateTime) {
        timeRecords.add(localDateTime);
        Collections.sort(timeRecords);
    }

    public LocalDateTime findSameDay(LocalDateTime wantToFindLocalDateTime) {
        for (LocalDateTime localDateTime : timeRecords) {
            LocalDateTime dayDate1 = localDateTime.truncatedTo(ChronoUnit.DAYS);
            LocalDateTime dayDate2 = wantToFindLocalDateTime.truncatedTo(ChronoUnit.DAYS);
            if (dayDate1.compareTo(dayDate2) == 0) {
                return localDateTime;
            }
        }
        return null;
    }

    public void modifyRecord(LocalDateTime wantToModifyLocalDateTime) {
        timeRecords.remove(findSameDay(wantToModifyLocalDateTime));
        addTime(wantToModifyLocalDateTime);
        Collections.sort(timeRecords);
    }

    public boolean findSameDay(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        LocalDateTime dayDate1 = localDateTime1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = localDateTime2.truncatedTo(ChronoUnit.DAYS);

        return (dayDate1.compareTo(dayDate2) == 0);
    }

    public void updateNoInformationInFile(LocalDateTime todayDate) {
        LocalDateTime standard = LocalDateTime.of(2024, 12, 1, 0, 0);
        while (!findSameDay(standard,todayDate)) {
            if (!isWeekend(standard) && findSameDay(standard) == null) {
                timeRecords.add(standard);
            }
            standard = standard.plusDays(1);
        }
        Collections.sort(timeRecords);
    }

    private boolean isWeekend(LocalDateTime localDateTime) {
        return (localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY) || localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY));
    }

}
