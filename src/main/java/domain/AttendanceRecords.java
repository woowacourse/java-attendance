package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecords {
    private final List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public AttendanceRecords() {
    }

    public AttendanceRecords(List<AttendanceRecord> attendanceRecords) {
        this.attendanceRecords.addAll(attendanceRecords);
    }

    public void add(AttendanceRecord attendanceRecord) {
        if (existsByCrewAndDate(attendanceRecord.getCrew(), attendanceRecord.getDate())) {
            throw new IllegalArgumentException("이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
        attendanceRecords.add(attendanceRecord);
    }

    public void overwriteAttendanceRecord(AttendanceRecord after) {
        attendanceRecords.removeIf(record ->
                after.getCrew().equals(record.getCrew()) && after.getDate().equals(record.getDate()));
        attendanceRecords.add(after);
    }

    public boolean existsByCrewAndDate(Crew crew, LocalDate date) {
        return attendanceRecords.stream()
                .anyMatch(record -> crew.equals(record.getCrew())
                        && date.equals(record.getDate()));
    }

    public AttendanceRecord findByCrewAndDate(Crew crew, LocalDate date) {
        return attendanceRecords.stream()
                .filter(record -> crew.equals(record.getCrew())
                        && date.equals(record.getDate()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

    public List<Crew> findAllDistinctCrews() {
        return attendanceRecords.stream()
                .map(AttendanceRecord::getCrew)
                .distinct()
                .toList();
    }
}
