package domain;

import common.SystemDate;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AttendanceRecordGenerator {

    private AttendanceRecordGenerator() {
    }

    public static Map<LocalDate, AttendanceRecord> generate() {
        final List<LocalDate> attendanceDates = createAttendanceDates();
        Map<LocalDate, AttendanceRecord> attendanceRecords = new LinkedHashMap<>();
        attendanceDates.forEach(attendanceDate -> attendanceRecords.put(attendanceDate, AttendanceRecord.of(attendanceDate)));
        return attendanceRecords;
    }

    private static List<LocalDate> createAttendanceDates() {
        final LocalDate startDate = SystemDate.START_DATE.getDate();
        return IntStream.rangeClosed(startDate.getDayOfMonth(), startDate.lengthOfMonth())
                .mapToObj(startDate::withDayOfMonth)
                .filter(date -> !ClassDayOff.isDayOff(date))
                .toList();
    }
}
