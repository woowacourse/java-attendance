package domain;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AttendanceRecordGenerator {

    private static final LocalDate START_DATE = LocalDate.of(2024, 12, 1);

    private AttendanceRecordGenerator() {
    }

    public static Map<LocalDate, AttendanceRecord> generate() {
        final List<LocalDate> attendanceDates = createAttendanceDates();
        Map<LocalDate, AttendanceRecord> attendanceRecords = new LinkedHashMap<>();
        attendanceDates.forEach(attendanceDate -> attendanceRecords.put(attendanceDate, null));
        return attendanceRecords;
    }

    private static List<LocalDate> createAttendanceDates() {
        return IntStream.rangeClosed(START_DATE.getDayOfMonth(), START_DATE.lengthOfMonth())
                .mapToObj(START_DATE::withDayOfMonth)
                .filter(date -> !ClassDayOff.isDayOff(date))
                .toList();
    }
}
