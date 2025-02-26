package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, AttendanceHistory> attendanceBook;

    public AttendanceBook() {
        this.attendanceBook = new HashMap<>();
    }

    public void registerCrew(final Crew crew) {
        validateAlreadyRegister(crew);
        attendanceBook.put(crew, new AttendanceHistory(crew));
    }

    private void validateAlreadyRegister(final Crew crew) {
        if (attendanceBook.containsKey(crew)) {
            throw new IllegalStateException();
        }
    }

    public AttendanceHistory findByCrew(final Crew crew) {
        if(!attendanceBook.containsKey(crew)){
            throw new IllegalArgumentException();
        }
        return attendanceBook.get(crew);
    }
}
