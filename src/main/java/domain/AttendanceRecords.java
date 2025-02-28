package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecords {
    private final List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public AttendanceRecords() {
    }

    public AttendanceRecords(List<AttendanceRecord> attendanceRecords) {
        this.attendanceRecords.addAll(new ArrayList<>(attendanceRecords));
    }

//    public void put(AttendanceRecord attendanceRecord) {
//        if (exists(attendanceRecord.getCrew(), attendanceRecord.getDate())) {
//            attendanceRecords.removeIf(record ->
//                    attendanceRecord.getNickname().equals(record.getNickname())
//                            && attendanceRecord.getDate().equals(record.getDate()));
//        }
//        attendanceRecords.add(attendanceRecord);
//    }

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

    public AttendanceRecord find(String nickname, LocalDate date) {
        return attendanceRecords.stream()
                .filter(record -> nickname.equals(record.getNickname())
                        && date.equals(record.getDate()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

//    public boolean exists(Crew crew, LocalDate date, LocalTime time) {
//        return attendanceRecords.stream()
//                .anyMatch(record -> crew.equals(record.getCrew())
//                        && date.equals(record.getDate())
//                        && time.equals(record.getTime()));
//    }

    public List<Crew> findAllDistinctCrews() {
        return attendanceRecords.stream()
                .map(AttendanceRecord::getCrew)
                .distinct()
                .toList();
    }
}
