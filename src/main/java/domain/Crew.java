package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Crew {
    private final CrewName name;
    private final List<Attendance> attendances;

    public Crew(final String name, final List<Attendance> attendances) {
        this.name = new CrewName(name);
        this.attendances = attendances;
    }

    public static Crew of(final String name, final LocalDate inputLocalDate) {
        final List<Attendance> attendances = new ArrayList<>();
        final LocalDate christmas = LocalDate.of(2024, 12, 25);
        final int dayOfMonth = inputLocalDate.getDayOfMonth();
        for (int i = 1; i < dayOfMonth; i++) {
            final LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2024, 12, i), LocalTime.MAX);
            final LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(),
                    localDateTime.getDayOfMonth());
            if (localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY
                    || localDate.equals(christmas)) {
                continue;
            }
            attendances.add(Attendance.empty(localDateTime));
        }
        return new Crew(name, attendances);

    }

    public Attendance addAttendance(final String attendanceTime) {
        final Attendance attendance = new Attendance(attendanceTime);
        attendances.add(attendance);
        return attendance;
    }

    public boolean existTodayAttendance(final LocalDate localDate) {
        return attendances.stream().anyMatch(attendance -> attendance.matchDate(localDate));
    }


    public void updateAttendanceByDateTime(final String attendanceTime) {
        final Attendance updatedAttendance = Attendance.of(attendanceTime);
        attendances.remove(updatedAttendance);
        attendances.add(updatedAttendance);
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatistics() {
        final Map<AttendanceStatus, Integer> statistics = initializeStatistics();
        attendances.stream()
                .map(Attendance::calculateStatus)
                .forEach(status -> statistics.merge(status, 1, Integer::sum));
        return statistics;
    }

    private Map<AttendanceStatus, Integer> initializeStatistics() {
        final Map<AttendanceStatus, Integer> statistics = new LinkedHashMap<>();
        AttendanceStatus.sortedStatus().forEach(status -> statistics.put(status, 0));
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

    public boolean isSameName(final String name) {
        return this.name.isSameName(name);
    }

    public List<Attendance> getAttendances() {
        return attendances.stream()
                .map(Attendance::new)
                .toList();
    }
}
