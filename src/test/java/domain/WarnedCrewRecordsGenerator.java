package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class WarnedCrewRecordsGenerator implements CrewAttendanceRecordsGenerator {
    private final Map<Crew, AttendanceRecords> warnedCrewRecords = new HashMap<>();

    @Override
    public Map<Crew, AttendanceRecords> generate(LocalDate today) {
        fillWarningCrew();
        fillExpelledCrew();
        fillOneOnOneCrew1();
        fillOneOnOneCrew2();
        fillOneOnOneCrew3();
        return warnedCrewRecords;
    }

    private void fillWarningCrew() {
        Crew crew = new Crew("경고크루");
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 2)));
        attendanceRecords.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 3)));
        warnedCrewRecords.put(crew, attendanceRecords);
    }

    private void fillExpelledCrew() {
        Crew crew = new Crew("제적크루");
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 2)));
        attendanceRecords.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 3)));
        attendanceRecords.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 4)));
        attendanceRecords.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 5)));
        attendanceRecords.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 6)));
        attendanceRecords.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 9)));
        warnedCrewRecords.put(crew, attendanceRecords);
    }

    private void fillOneOnOneCrew1() {
        Crew crew1 = new Crew("면담크루1"); // 결석 3회
        AttendanceRecords attendanceRecords1 = new AttendanceRecords();
        attendanceRecords1.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 2)));
        attendanceRecords1.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 3)));
        attendanceRecords1.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 4)));
        warnedCrewRecords.put(crew1, attendanceRecords1);
    }

    private void fillOneOnOneCrew2() {
        Crew crew2 = new Crew("면담크루2"); // 결석 4회
        AttendanceRecords attendanceRecords2 = new AttendanceRecords();
        attendanceRecords2.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 2)));
        attendanceRecords2.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 3)));
        attendanceRecords2.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 4)));
        attendanceRecords2.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 5)));
        warnedCrewRecords.put(crew2, attendanceRecords2);
    }

    private void fillOneOnOneCrew3() {
        Crew crew3 = new Crew("면담크루3"); // 결석 3회 + 지각 3회
        AttendanceRecords attendanceRecords3 = new AttendanceRecords();
        attendanceRecords3.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 2)));
        attendanceRecords3.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 3)));
        attendanceRecords3.addRecord(AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 4)));
        attendanceRecords3.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)));
        attendanceRecords3.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 10)));
        attendanceRecords3.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 9), LocalTime.of(13, 10)));
        warnedCrewRecords.put(crew3, attendanceRecords3);
    }
}
