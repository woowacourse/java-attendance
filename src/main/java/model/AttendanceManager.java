package model;

import java.util.Map;

public class AttendanceManager {

    final private Map<Crew, AttendanceBook> attendanceBooks;

    public AttendanceManager(final Map<Crew, AttendanceBook> attendanceBookMap) {
        this.attendanceBooks = attendanceBookMap;
    }

    public Map<Crew, AttendanceBook> getAttendanceBooks() {
        return attendanceBooks;
    }
}
