package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceRecords {
    private final Set<AttendanceRecord> attendanceRecords = new HashSet<>();

    public AttendanceRecords() {
    }

    public AttendanceRecords(List<AttendanceRecord> attendanceRecords) {
        this.attendanceRecords.addAll(new ArrayList<>(attendanceRecords));
    }

    public boolean exists(Crew crew, LocalDate date) {
        return attendanceRecords.stream()
                .anyMatch(record -> crew.equals(record.getCrew())
                        && date.equals(record.getDate()));
    }

    public void add(AttendanceRecord attendanceRecord) {
        if (exists(attendanceRecord.getCrew(), attendanceRecord.getDate())) {
            throw new IllegalArgumentException("이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
        attendanceRecords.add(attendanceRecord);
    }

    public AttendanceRecord find(Crew crew, LocalDate date) {
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

    public void overwriteAttendanceRecord(AttendanceRecord attendanceRecord) {
        attendanceRecords.removeIf(record -> record.equals(attendanceRecord));
        attendanceRecords.add(attendanceRecord);
    }
}
