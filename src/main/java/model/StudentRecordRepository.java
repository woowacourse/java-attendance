package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StudentRecordRepository {

    private final Map<String, Record> studentRecord = new HashMap<>();

    public StudentRecordRepository() {
    }

    public Map<String, Record> getStudentRecord() {
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
        studentRecord.put(name, new Record(localDateTime));
    }

    public void modifyRecord(String studentName, LocalDateTime localDateTime) {
        studentRecord.get(studentName).modifyRecord(localDateTime);
    }

    public void updateEveryStudentNoInformationInFile(LocalDateTime todayDateTime) {
        for (String name : studentRecord.keySet()) {
            studentRecord.get(name).updateNoInformationInFile(todayDateTime);
        }
    }

    public Record getStudentRecordByName(String name) {
        return studentRecord.get(name);
    }

}
