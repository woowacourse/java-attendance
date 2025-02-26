package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

public class StudentAttendanceHistory {

    private final List<LocalDateTime> attendanceHistory;

    public StudentAttendanceHistory(List<LocalDateTime> attendanceHistory) {
        this.attendanceHistory = attendanceHistory;
    }


    public List<LocalDateTime> getAttendanceHistory() {
        return attendanceHistory;
    }

    public void addTime(LocalDateTime localDateTime) {
        attendanceHistory.add(localDateTime);
        Collections.sort(attendanceHistory);
    }

    public LocalDateTime findSameDay(LocalDateTime wantToFindLocalDateTime) {
        for (LocalDateTime localDateTime : attendanceHistory) {
            LocalDateTime dayDate1 = localDateTime.truncatedTo(ChronoUnit.DAYS);
            LocalDateTime dayDate2 = wantToFindLocalDateTime.truncatedTo(ChronoUnit.DAYS);
            if (dayDate1.isEqual(dayDate2)) {
                return localDateTime;
            }
        }
        return null;
    }

    public void modifyRecord(LocalDateTime wantToModifyLocalDateTime) {
        attendanceHistory.remove(findSameDay(wantToModifyLocalDateTime));
        addTime(wantToModifyLocalDateTime);
        Collections.sort(attendanceHistory);
    }

    public boolean isExistSameDay(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        LocalDateTime dayDate1 = localDateTime1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = localDateTime2.truncatedTo(ChronoUnit.DAYS);

        return (dayDate1.isEqual(dayDate2));
    }

    public void updateNoInformationInFile(LocalDateTime todayDate) {
        LocalDateTime standard = LocalDateTime.of(2024, 12, 1, 0, 0);
        while (!isExistSameDay(standard, todayDate)) {
            addTimeRecordIfValid(standard);
            standard = standard.plusDays(1);
        }
        Collections.sort(attendanceHistory);
    }

    private void addTimeRecordIfValid(LocalDateTime standard) {
        if (!isWeekend(standard) && findSameDay(standard) == null) {
            attendanceHistory.add(standard);
        }
    }

    private boolean isWeekend(LocalDateTime localDateTime) {
        return (localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY) || localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY));
    }

    public boolean isAlreadyAttendanceDate(TodayDate todayDate) {
        return attendanceHistory.contains(todayDate.getTodayDateTIme());
    }
}
