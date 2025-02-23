package domain;

import domain.constants.AttendanceStatus;
import domain.constants.ExpulsionStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Crew {
    private static final LocalDate CHRISTMAS_DATE = LocalDate.of(2024, 12, 25);
    private final CrewName name;
    private final List<Attendance> attendances;

    public Crew(final String name, final List<Attendance> attendances) {
        this.name = new CrewName(name);
        this.attendances = new ArrayList<>(attendances);
    }

    public static Crew of(final String name, final LocalDate today) {
        final List<Attendance> attendances = IntStream.range(1, today.getDayOfMonth())
                .mapToObj(today::withDayOfMonth)
                .filter(Crew::canDateAttendance)
                .map(Attendance::empty)
                .toList();
        return new Crew(name, attendances);
    }

    private static boolean canDateAttendance(final LocalDate today) {
        if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY
                || today.equals(CHRISTMAS_DATE)) {
            return true;
        }
        return false;
    }

    public Attendance addAttendance(final String attendanceTime) {
        final Attendance attendance = Attendance.of(attendanceTime);
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

    public ExpulsionStatus calculateExpulsionStatus() {
        final int absence = calculateExpulsionCount();
        return ExpulsionStatus.of(absence);
    }

    public int calculateExpulsionCount() {
        final Map<AttendanceStatus, Integer> statusCount = calculateAttendanceStatistics();
        return statusCount.get(AttendanceStatus.LATE) / 3 + statusCount.get(AttendanceStatus.ABSENCE);
    }

    public Attendance findAttendanceByDate(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.matchDate(date))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int compareByExpulsionCount(final Crew c) {
        final int expulsionCount1 = this.calculateExpulsionCount();
        final int expulsionCount2 = c.calculateExpulsionCount();
        if (expulsionCount1 == expulsionCount2) {
            return this.name.compareTo(c.name);
        }
        return Integer.compare(expulsionCount2, expulsionCount1);
    }

    private Map<AttendanceStatus, Integer> initializeStatistics() {
        final Map<AttendanceStatus, Integer> statistics = new LinkedHashMap<>();
        AttendanceStatus.sortedStatus().forEach(status -> statistics.put(status, 0));
        return statistics;
    }

    public CrewName getName() {
        return name;
    }

    public boolean isSameName(final String name) {
        return this.name.isSameName(name);
    }

    public List<Attendance> getAttendances() {
        return new ArrayList<>(attendances);
    }
}
