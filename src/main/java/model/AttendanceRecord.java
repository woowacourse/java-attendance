package model;

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

    public LocalDateTime compareDayIsSame(LocalDateTime wantToFindLocalDateTime) {
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
        timeRecords.remove(compareDayIsSame(wantToModifyLocalDateTime));
        addTime(wantToModifyLocalDateTime);
        Collections.sort(timeRecords);
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        LocalDateTime dayDate1 = localDateTime1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = localDateTime2.truncatedTo(ChronoUnit.DAYS);

        return (dayDate1.compareTo(dayDate2) == 0);
    }

    public void updateNoInformationInFile(LocalDateTime todayDate) {
        LocalDateTime standard = LocalDateTime.of(2024,12,1,0,0);
        while (!compareDayIsSame(standard,todayDate)) {
            if (standard.getDayOfWeek().getValue() == 6 || standard.getDayOfWeek().getValue() == 7) {
                standard = standard.plusDays(1);
                continue;
            }
            if (compareDayIsSame(standard)==null) {
                timeRecords.add(standard);
                standard = standard.plusDays(1);
                continue;
            }
            standard = standard.plusDays(1);
        }
        Collections.sort(timeRecords);
    }

}
