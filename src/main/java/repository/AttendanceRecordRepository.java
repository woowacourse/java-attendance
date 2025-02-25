package repository;

import domain.AttendanceRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecordRepository {
    private final static List<AttendanceRecord> ATTENDANCE_RECORDS = new ArrayList<>();

//    public static void put(AttendanceRecord attendanceRecord) {
//        if (exists(attendanceRecord.nickname(), attendanceRecord.date())) {
//            ATTENDANCE_RECORDS.removeIf(record ->
//                    attendanceRecord.nickname().equals(record.nickname())
//                            && attendanceRecord.date().equals(record.date()));
//        }
//        ATTENDANCE_RECORDS.add(attendanceRecord);
//    }

    public static void add(AttendanceRecord attendanceRecord) {
        if (exists(attendanceRecord.nickname(), attendanceRecord.date())) {
            throw new IllegalArgumentException("이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
        ATTENDANCE_RECORDS.add(attendanceRecord);
    }

    public static AttendanceRecord find(String nickname, LocalDate date) {
        return ATTENDANCE_RECORDS.stream()
                .filter(record -> nickname.equals(record.nickname())
                        && date.equals(record.date()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

    public static boolean exists(String nickname, LocalDate date) {
        return ATTENDANCE_RECORDS.stream()
                .anyMatch(record -> nickname.equals(record.nickname())
                        && date.equals(record.date()));
    }

    public static void clear() {
        ATTENDANCE_RECORDS.clear();
    }
}
