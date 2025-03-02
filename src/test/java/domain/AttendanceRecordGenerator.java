package domain;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class AttendanceRecordGenerator {
    public static Map<LocalDate, AttendanceRecord> generate(final LocalDate localDate) {
        return new LinkedHashMap<>();
    }
}
