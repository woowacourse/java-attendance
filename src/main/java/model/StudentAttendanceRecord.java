package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StudentAttendanceRecord {

    private final Map<String, AttendanceRecord> studentRecord = new HashMap<>();

    public StudentAttendanceRecord() {
    }

    public Map<String, AttendanceRecord> getStudentRecord() {
        return studentRecord;
    }

    public boolean isExistStudentByName(String name) {
        return (studentRecord.containsKey(name));
    }

    public void addRecord(String name, LocalDateTime localDateTime) {
        if (isExistStudentByName(name)) {
            studentRecord.get(name).addTime(localDateTime);
            return;
        }
        studentRecord.put(name, new AttendanceRecord(localDateTime));
    }

    public void modifyRecord(String studentName, LocalDateTime localDateTime) {
        studentRecord.get(studentName).modifyRecord(localDateTime);
    }

    public void updateEveryStudentNoInformationInFile(LocalDateTime todayDateTime) {
        for (String name : studentRecord.keySet()) {
            studentRecord.get(name).updateNoInformationInFile(todayDateTime);
        }
    }

    public AttendanceRecord getStudentRecordByName(String name) {
        return studentRecord.get(name);
    }

    public void isAlreadyAttendanceDate(String name, TodayDate todayDate) {
        if (studentRecord.get(name).isSameDay(todayDate.getTodayDateTIme())!=null) {
            throw new IllegalArgumentException("[ERROR] 이미 출석한 요일입니다. 수정하고 싶으시면 수정 메뉴를 이용해 주세요.");
        }
    }

}
