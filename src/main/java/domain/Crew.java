package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Crew {
    private final CrewName name;
    private final List<Attendance> attendances;

    public Crew(final String name) {
        this.name = new CrewName(name);
        this.attendances = new ArrayList<>();
    }

    public void addAttendance(final String attendanceTime) {
        attendances.add(new Attendance(attendanceTime));
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatistics() {
        final Map<AttendanceStatus, Integer> statistics = initializeStatistics();
        attendances.stream()
                .map(Attendance::calculateStatus)
                .forEach(status -> statistics.merge(status, 1, Integer::sum));
        return statistics;
    }

    private Map<AttendanceStatus, Integer> initializeStatistics() {
        final Map<AttendanceStatus, Integer> statistics = new HashMap<>();
        Arrays.stream(AttendanceStatus.values())
                .forEach(status -> statistics.put(status, 0));
        return statistics;
    }

    public ExpulsionStatus calculateExpulsionStatus() {
        final Map<AttendanceStatus, Integer> statusCount = calculateAttendanceStatistics();
        final int absence = statusCount.get(AttendanceStatus.LATE) / 3 + statusCount.get(AttendanceStatus.ABSENCE);
        return ExpulsionStatus.of(absence);
    }

    public CrewName getName() {
        return name;
    }

    public List<Attendance> getAttendances() {
        return attendances.stream()
                .map(Attendance::new)
                .toList();
    }
}
