package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecords {
    private final List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public AttendanceRecords() {
    }

    public AttendanceRecords(List<AttendanceRecord> attendanceRecords) {
        attendanceRecords.addAll(attendanceRecords);
    }

    public void put(AttendanceRecord attendanceRecord) {
        if (exists(attendanceRecord.nickname(), attendanceRecord.date())) {
            attendanceRecords.removeIf(record ->
                    attendanceRecord.nickname().equals(record.nickname())
                            && attendanceRecord.date().equals(record.date()));
        }
        attendanceRecords.add(attendanceRecord);
    }

    public void add(AttendanceRecord attendanceRecord) {
        if (exists(attendanceRecord.nickname(), attendanceRecord.date())) {
            throw new IllegalArgumentException("이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
        attendanceRecords.add(attendanceRecord);
    }

    public AttendanceRecord find(String nickname, LocalDate date) {
        return attendanceRecords.stream()
                .filter(record -> nickname.equals(record.nickname())
                        && date.equals(record.date()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

    public boolean exists(String nickname, LocalDate date) {
        return attendanceRecords.stream()
                .anyMatch(record -> nickname.equals(record.nickname())
                        && date.equals(record.date()));
    }

    public boolean exists(String nickname, LocalDate date, LocalTime time) {
        return attendanceRecords.stream()
                .anyMatch(record -> nickname.equals(record.nickname())
                        && date.equals(record.date())
                        && time.equals(record.time()));
    }

    public List<String> findNicknames() {
        return attendanceRecords.stream()
                .map(AttendanceRecord::nickname)
                .toList();
    }
}
