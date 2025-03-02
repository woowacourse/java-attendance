package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class CrewRecords {
    private final Map<Crew, AttendanceRecords> records;

    public CrewRecords(Map<Crew, AttendanceRecords> records) {
        this.records = records;
    }

    public void addRecord(Crew crew, AttendanceRecord record) {
        validateCrew(crew);
        records.get(crew).add(record);
    }

    public void updateRecord(Crew crew, LocalDate oldDate, LocalTime newTime) {
        validateCrew(crew);
        records.get(crew).update(oldDate, newTime);
    }

    public AttendanceRecord getRecordOnDate(Crew crew, LocalDate date) {
        validateCrew(crew);
        return records.get(crew).getRecordOnDate(date);
    }

    public AttendanceRecords getAttendanceRecordsOf(Crew crew) {
        validateCrew(crew);
        return records.get(crew);
    }

    public WarningStatus getWarningStatus(Crew crew) {
        validateCrew(crew);
        AttendanceRecords attendanceRecords = records.get(crew);
        int tardyCount = attendanceRecords.getAttendanceCount(AttendanceStatus.TARDY);
        int absentCount = attendanceRecords.getAttendanceCount(AttendanceStatus.ABSENT);
        return WarningStatus.getStatus(tardyCount, absentCount);
    }

    private void validateCrew(Crew crew) {
        if (!records.containsKey(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다." + System.lineSeparator());
        }
    }
}
