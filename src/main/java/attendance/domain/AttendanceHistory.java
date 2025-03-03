package attendance.domain;

import java.util.Iterator;
import java.util.List;

public record AttendanceHistory(List<Attendance> history) implements Iterable<Attendance> {
    @Override
    public Iterator<Attendance> iterator() {
        return history.iterator();
    }
}
