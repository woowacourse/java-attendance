package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceHistory {

    private final List<AttendanceTime> history = new ArrayList<>();

    public void add(final AttendanceTime attendanceTime) {

        history.add(attendanceTime);
    }

    public List<AttendanceTime> getHistory() {

        return List.copyOf(history);
    }

    public AttendanceTime getAttendanceTime(final LocalDate date) {

        return history.stream()
                .filter(time -> time.isSameDay(date))
                .toList()
                .getFirst();
    }

    public boolean isAlreadyExists(final LocalDate date) {

        return history.stream().anyMatch(attendanceTime -> attendanceTime.isSameDay(date));
    }
}
