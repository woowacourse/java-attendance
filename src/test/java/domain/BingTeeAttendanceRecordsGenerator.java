package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class BingTeeAttendanceRecordsGenerator implements CrewAttendanceRecordsGenerator {
    private final Map<Crew, AttendanceRecords> bingTeeAttendanceRecords = new HashMap<>();
    private final AttendanceRecords attendanceRecords = new AttendanceRecords();
    private final Crew crew = new Crew("빙티");

    @Override
    public Map<Crew, AttendanceRecords> generate(LocalDate today) {
        // 출석
        fillAbsence();
        fillTardy();
        fillPresents();
        bingTeeAttendanceRecords.put(crew, attendanceRecords);
        return bingTeeAttendanceRecords;
    }

    private void fillPresents() {
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0)));
    }

    private void fillTardy() {
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 15)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 15)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 9), LocalTime.of(13, 15)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 10), LocalTime.of(10, 15)));
    }

    private void fillAbsence() {
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 11), LocalTime.of(10, 31)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31)));
    }
}
