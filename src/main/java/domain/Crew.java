package domain;

import domain.constants.AttendanceStatus;
import domain.constants.ExpulsionStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Crew implements Comparable<Crew> {
    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2024, 12, 25);
    private final CrewName name;
    private final List<Attendance> attendances;

    public Crew(final String name, final List<Attendance> attendances) {
        this.name = new CrewName(name);
        this.attendances = new ArrayList<>(attendances);
    }

    public static Crew of(final String name, final LocalDate today) {
        List<Attendance> attendances = IntStream.range(1, today.getDayOfMonth())
                .mapToObj(today::withDayOfMonth)
                .filter(Crew::isAvailableForAttendance)
                .map(Attendance::empty)
                .toList();
        return new Crew(name, attendances);
    }

    private static boolean isAvailableForAttendance(final LocalDate today) {
        if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY
                || today.equals(CHRISTMAS_DATE)) {
            return true;
        }
        return false;
    }

    public Attendance addAttendance(final String attendanceTime) {
        final Attendance attendance = new Attendance(attendanceTime);
        attendances.add(attendance);
        return attendance;
    }

    public Attendance addAttendance(final LocalDateTime localDateTime) {
        final Attendance attendance = new Attendance(localDateTime, false);
        attendances.add(attendance);
        return attendance;
    }

    public boolean isAlreadyTodayAttendance(final LocalDate localDate) {
        return attendances.stream().anyMatch(attendance -> attendance.matchDate(localDate));
    }

    public void updateAttendanceByDateTime(final String attendanceTime) {
        final Attendance updatedAttendance = Attendance.of(attendanceTime);
        attendances.remove(updatedAttendance);
        attendances.add(updatedAttendance);
    }

    public UpdatedAttendanceSnapshot updateAttendanceByDateAndTime(
            final LocalTime attendanceTime,
            final LocalDate localDate
    ) {
        final LocalDateTime newAttendanceDateAndTime = LocalDateTime.of(localDate, attendanceTime);
        final Attendance updatedAttendance = Attendance.of(newAttendanceDateAndTime);
        final Attendance beforeAttendance = findAttendanceByDate(localDate);
        attendances.remove(updatedAttendance);
        attendances.add(updatedAttendance);
        return new UpdatedAttendanceSnapshot(beforeAttendance, updatedAttendance);
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
