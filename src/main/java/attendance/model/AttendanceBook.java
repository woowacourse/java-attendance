package attendance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, List<AttendanceTime>> attendances = new HashMap<>();

    public void add(final String name, final AttendanceTime attendanceTime) {

        if (!attendances.containsKey(name)) {
            attendances.put(name, new ArrayList<>());
        }
        attendances.get(name).add(attendanceTime);
    }

    public AttendanceTime getAttendance(final String name, final LocalDate date) {

        return attendances.get(name)
                .stream()
                .filter(time -> time.isSameDay(date))
                .toList()
                .getFirst();
    }

}
