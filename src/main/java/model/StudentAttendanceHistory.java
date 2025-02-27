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

    public void addTime(LocalDateTime localDateTime) {
        attendanceHistory.add(localDateTime);
    }

    public LocalDateTime findSameDay(LocalDateTime wantToFindLocalDateTime) {
        return attendanceHistory.stream()
                .filter(localDateTime -> isSameDay(localDateTime, wantToFindLocalDateTime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석하지 않는 날짜입니다."));
    }

    public void modifyRecord(LocalDateTime wantToModifyLocalDateTime) {
        try {
            attendanceHistory.remove(findSameDay(wantToModifyLocalDateTime));
            addTime(wantToModifyLocalDateTime);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isSameDay(LocalDateTime firstDateTime, LocalDateTime secondDateTime) {
        return firstDateTime.toLocalDate().isEqual(secondDateTime.toLocalDate());
    }

    public void updateNoInformationInFile(LocalDateTime todayDate) {
        LocalDateTime standard = LocalDateTime.of(2024, 12, 1, 0, 0);
        while (!isSameDay(standard, todayDate)) {
            addTimeRecordIfValid(standard);
            standard = standard.plusDays(1);
        }
    }

    private boolean isExistSameDay(LocalDateTime wantToFindDay) {
        return attendanceHistory.stream()
                .anyMatch(localDateTime -> isSameDay(localDateTime,wantToFindDay));
    }

    private void addTimeRecordIfValid(LocalDateTime standard) {
        if (!isWeekend(standard) && !isExistSameDay(standard)) {
            attendanceHistory.add(standard);
        }
    }

    private boolean isWeekend(LocalDateTime localDateTime) {
        return (localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY) || localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY));
    }

    public boolean isAlreadyAttendanceDate(TodayDate todayDate) {
        return attendanceHistory.contains(todayDate.getTodayDateTIme());
    }

    public void sortHistoryBeforePrint() {
        Collections.sort(attendanceHistory);
    }

    public List<LocalDateTime> getAttendanceHistory() {
        return Collections.unmodifiableList(attendanceHistory);
    }
}
