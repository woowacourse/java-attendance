package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Attendances {

    private final Set<Attendance> attendances;

    public Attendances(final Set<Attendance> attendances) {
        this.attendances = new HashSet<>(attendances);
    }

    public boolean add(final Attendance attendance) {
        if (attendances.add(attendance)) {
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 수정 기능을 이용해 주세요.");
    }

    public Attendance findByCrewNameAndLocalDate(final String crewName, final LocalDate localDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameNameAndLocalDate(crewName, localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 존재하지 않습니다."));
    }

    public List<Attendance> findAttendancesByCrewName(final String crewName, final int year, final int month) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrewName(crewName))
                .filter(attendance -> attendance.isSameYearAndMonth(year, month))
                .toList();
    }

    public long getStatusCount(final Map<LocalDate, Attendance> monthlyAttendances,
                               final AttendanceStatus attendanceStatus) {
        return monthlyAttendances.values().stream()
                .filter(attendance -> (attendance == null && attendanceStatus == AttendanceStatus.ABSENT) ||
                        (attendance != null && attendance.checkStatus().equals(attendanceStatus)))
                .count();
    }

    public Map<LocalDate, Attendance> getMonthlyAttendanceMap(final String crewName, final int year, final int month) {
        Map<LocalDate, Attendance> monthlyAttendances = getCrewAttendancesAsMap(crewName, year, month);
        fillMissingWeekdayAttendances(monthlyAttendances, year, month);
        return monthlyAttendances;
    }

    private Map<LocalDate, Attendance> getCrewAttendancesAsMap(final String crewName, final int year, final int month) {
        List<Attendance> crewAttendances = findAttendancesByCrewName(crewName, year, month);

        return crewAttendances.stream()
                .collect(Collectors.toMap(
                        attendance -> attendance.getAttendanceTime().getLocalDate(),
                        attendance -> attendance
                ));
    }

    private void fillMissingWeekdayAttendances(final Map<LocalDate, Attendance> monthlyAttendances, final int year,
                                               final int month) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate endDate = determineEndDate(firstDay);

        Stream.iterate(firstDay, date -> !date.isAfter(endDate), date -> date.plusDays(1))
                .filter(this::isWeekday)
                .filter(date -> !monthlyAttendances.containsKey(date))
                .forEach(date -> monthlyAttendances.put(date, null));
    }

    private LocalDate determineEndDate(final LocalDate firstDay) {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfMonth = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        if (today.isBefore(lastDayOfMonth)) {
            return today.minusDays(1);
        }
        return lastDayOfMonth;
    }

    private boolean isWeekday(final LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }
}
