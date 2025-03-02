package domain;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AttendanceRecordGenerator {
    public static Map<LocalDate, AttendanceRecord> generate(final LocalDate localDate) {
        final List<LocalDate> attendanceDates = createAttendanceDates(localDate);
        Map<LocalDate, AttendanceRecord> attendanceRecords = new LinkedHashMap<>();
        attendanceDates.forEach(attendanceDate -> attendanceRecords.put(attendanceDate, null));
        return attendanceRecords;
    }

    private static List<LocalDate> createAttendanceDates(final LocalDate localDate) {
        return IntStream.rangeClosed(localDate.getDayOfMonth(), localDate.lengthOfMonth())
                .mapToObj(localDate::withDayOfMonth)
                .filter(date -> !ClassDayOff.isDayOff(date))
                .toList();
    }
}
