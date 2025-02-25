package repository;

import domain.AttendanceRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecordRepository {
    private final static List<AttendanceRecord> ATTENDANCE_RECORDS = new ArrayList<>();

    public static void add(AttendanceRecord attendanceRecord) {
        ATTENDANCE_RECORDS.add(attendanceRecord);
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
