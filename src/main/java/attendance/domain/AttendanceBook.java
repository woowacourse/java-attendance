package attendance.domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, AttendanceRecord> attendanceBook;

    public AttendanceBook(final Map<Crew, AttendanceRecord> attendanceBook) {
        this.attendanceBook = new HashMap<>(attendanceBook);
    }
}
