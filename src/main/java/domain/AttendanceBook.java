package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, AttendanceHistory> attendanceBook;

    public AttendanceBook() {
        this.attendanceBook = new HashMap<>();
    }

    public void registerCrew(final Crew crew) {
        if (attendanceBook.containsKey(crew)) {
            throw new IllegalStateException();
        }
        attendanceBook.put(crew, new AttendanceHistory(crew));
    }

    public AttendanceHistory findByCrew(final Crew crew) {
        return attendanceBook.get(crew);
    }
}
