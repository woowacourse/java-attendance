package attendance.domain;

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
}
