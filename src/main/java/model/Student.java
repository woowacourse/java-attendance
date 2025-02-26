package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {

    String name;

    List<LocalDateTime> timeRecords;

    public Student(String name, List<LocalDateTime> timeRecords) {
        this.name = name;
        this.timeRecords = timeRecords;
    }

    public String getName() {
        return name;
    }

    public List<LocalDateTime> getTimeRecords() {
        return timeRecords;
    }

    public void addTime(LocalDateTime localDateTime) {
        timeRecords.add(localDateTime);
        Collections.sort(timeRecords);
    }

    public LocalDateTime findSameDay(LocalDateTime wantToFindLocalDateTime) {
        for (LocalDateTime localDateTime : timeRecords) {
            LocalDateTime dayDate1 = localDateTime.truncatedTo(ChronoUnit.DAYS);
            LocalDateTime dayDate2 = wantToFindLocalDateTime.truncatedTo(ChronoUnit.DAYS);
            if (dayDate1.isEqual(dayDate2)) {
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

    public boolean isExistSameDay(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        LocalDateTime dayDate1 = localDateTime1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = localDateTime2.truncatedTo(ChronoUnit.DAYS);

        return (dayDate1.isEqual(dayDate2));
    }

    public void updateNoInformationInFile(LocalDateTime todayDate) {
        LocalDateTime standard = LocalDateTime.of(2024, 12, 1, 0, 0);
        while (!isExistSameDay(standard,todayDate)) {
            addTimeRecordIfValid(standard);
            standard = standard.plusDays(1);
        }
        Collections.sort(timeRecords);
    }

    private void addTimeRecordIfValid(LocalDateTime standard) {
        if (!isWeekend(standard) && findSameDay(standard) == null) {
            timeRecords.add(standard);
        }
    }

    private boolean isWeekend(LocalDateTime localDateTime) {
        return (localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY) || localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY));
    }

    public void isAlreadyAttendanceDate(TodayDate todayDate) {
        if (timeRecords.contains(todayDate.getTodayDateTIme())) {
            throw new IllegalArgumentException("[ERROR] 이미 출석한 요일입니다. 수정하고 싶으시면 수정 메뉴를 이용해 주세요.");
        }
    }

}
