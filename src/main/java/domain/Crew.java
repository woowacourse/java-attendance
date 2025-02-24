package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import util.DateTimeParser;

public class Crew implements Comparable<Crew> {
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
            addAttendance(i, christmas, attendances);
        }
        return new Crew(name, attendances);

    }

    private static void addAttendance(final int i, final LocalDate christmas, final List<Attendance> attendances) {
        final LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2024, 12, i), LocalTime.MAX);
        final LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth());
        if (localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY
                || localDate.equals(christmas)) {
            return;
        }
        attendances.add(Attendance.empty(localDateTime));
    }

    public Attendance addAttendance(final LocalDateTime attendanceTime) {
        final Attendance attendance = new Attendance(attendanceTime);
        attendances.add(attendance);
        return attendance;
    }

    public boolean existTodayAttendance(final LocalDate localDate) {
        return attendances.stream().anyMatch(attendance -> attendance.matchDate(localDate));
    }


    public void updateAttendanceByDateTime(final String attendanceTime) {
        final LocalDate localDate = DateTimeParser.parseToLocalDateTime(attendanceTime).toLocalDate();
        removePreAttendanceByDateTime(localDate);
        final Attendance updatedAttendance = Attendance.of(attendanceTime);
        attendances.add(updatedAttendance);
    }

    public void updateAttendanceByDateAndTime(final LocalTime attendanceTime, final LocalDate localDate) {
        final LocalDateTime newAttendanceDateAndTime = LocalDateTime.of(localDate, attendanceTime);
        removePreAttendanceByDateTime(localDate);
        final Attendance updatedAttendance = Attendance.of(newAttendanceDateAndTime);
        attendances.add(updatedAttendance);
    }

    private void removePreAttendanceByDateTime(final LocalDate localDate) {
        attendances.stream()
                .filter(attendance -> attendance.matchDate(localDate))
                .findAny()
                .ifPresent(attendances::remove);
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
        final int absence = countExpulsionStatus();
        return ExpulsionStatus.of(absence);
    }

    public int countExpulsionStatus() {
        final Map<AttendanceStatus, Integer> statusCount = calculateAttendanceStatistics();
        return statusCount.get(AttendanceStatus.LATE) / 3 + statusCount.get(AttendanceStatus.ABSENCE);
    }

    public Attendance findAttendanceByDate(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.matchDate(date))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
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

    @Override
    public int compareTo(final Crew o) {
        final int count1 = this.countExpulsionStatus();
        final int count2 = o.countExpulsionStatus();
        if (count1 != count2) {
            return count2 - count1;
        }

        return this.name.compareTo(o.name);
    }
}
