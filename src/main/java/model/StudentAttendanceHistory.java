package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentAttendanceHistory {

    private final List<AttendanceDateTime> attendanceHistory;

    public StudentAttendanceHistory(List<AttendanceDateTime> attendanceHistory) {
        this.attendanceHistory = new ArrayList<>(attendanceHistory);
    }

    public void addTime(AttendanceDateTime attendanceDateTime) {
        attendanceHistory.add(attendanceDateTime);
    }

    public AttendanceDateTime findSameDay(AttendanceDateTime wantToFindLocalDateTime) {
        return attendanceHistory.stream()
                .filter(attendanceDateTime -> isSameDay(attendanceDateTime, wantToFindLocalDateTime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석하지 않는 날짜입니다."));
    }

    public void modifyRecord(AttendanceDateTime wantToModifyLocalDateTime) {
        try {
            attendanceHistory.remove(findSameDay(wantToModifyLocalDateTime));
            addTime(wantToModifyLocalDateTime);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isSameDay(AttendanceDateTime firstDateTime, AttendanceDateTime secondDateTime) {
        return firstDateTime.isSameAttendanceDateTime(secondDateTime);
    }

    public void fillMissingAttendanceRecords(AttendanceDateTime todayDate) {
        LocalDateTime startOfDecember = LocalDateTime.of(2024, 12, 1, 0, 0);
        AttendanceDateTime standard = new AttendanceDateTime(startOfDecember);
        while (!isSameDay(standard, todayDate)) {
            addTimeRecordIfValid(standard);
            standard = standard.addOneDay();
        }
    }

    private boolean isExistSameDay(AttendanceDateTime wantToFindDay) {
        return attendanceHistory.stream()
                .anyMatch(attendanceDateTime -> isSameDay(attendanceDateTime,wantToFindDay));
    }

    private void addTimeRecordIfValid(AttendanceDateTime standard) {
        if (!isWeekend(standard) && !isExistSameDay(standard)) {
            attendanceHistory.add(standard);
        }
    }

    private boolean isWeekend(AttendanceDateTime attendanceDateTime) {
        return (attendanceDateTime.isChristmas() || attendanceDateTime.isWeekend());
    }

    public boolean isAlreadyAttendanceDate(TodayDate todayDate) {
        return this.isExistSameDay(todayDate.toAttendanceDateTime());
    }

    public void sortHistoryBeforePrint() {
        Collections.sort(attendanceHistory);
    }

    public List<AttendanceDateTime> getAttendanceHistory() {
        return Collections.unmodifiableList(attendanceHistory);
    }
}
