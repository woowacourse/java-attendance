package model;

import java.time.LocalDateTime;

public class Student {

    private final String name;

    private final StudentAttendanceHistory studentAttendanceHistory;

    public Student(String name, StudentAttendanceHistory studentAttendanceHistory) {
        this.name = name;
        this.studentAttendanceHistory = studentAttendanceHistory;
    }

    public boolean isSameName(String studentName) {
        return studentName.equals(name);
    }

    public void addTime(LocalDateTime localDateTime) {
        studentAttendanceHistory.addTime(localDateTime);
    }

    public LocalDateTime findSameDay(LocalDateTime wantToFindLocalDateTime) {
        return studentAttendanceHistory.findSameDay(wantToFindLocalDateTime);
    }

    public void modifyRecord(LocalDateTime wantToModifyLocalDateTime) {
        studentAttendanceHistory.modifyRecord(wantToModifyLocalDateTime);
    }

    public void updateNoInformationInFile(LocalDateTime todayDate) {
        studentAttendanceHistory.updateNoInformationInFile(todayDate);
    }

    public void validateAlreadyAttendanceDate(TodayDate todayDate) {
        if (studentAttendanceHistory.isAlreadyAttendanceDate(todayDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석한 요일입니다. 수정하고 싶으시면 수정 메뉴를 이용해 주세요.");
        }
    }

    public StudentAttendanceHistory getStudentAttendanceHistory() {
        return studentAttendanceHistory;
    }

    public void sortStudentAttendanceHistory() {
        studentAttendanceHistory.sortHistoryBeforePrint();
    }

    public String getName() {
        return name;
    }
}
