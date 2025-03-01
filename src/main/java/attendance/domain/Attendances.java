package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Attendances {

    private final Set<Attendance> attendances;

    public Attendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean add(Attendance attendance) {
        if (attendances.add(attendance)) {
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 수정 기능을 이용해 주세요.");
    }

    public Attendance findByCrewNameAndLocalDate(String crewName, LocalDate localDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameLocalDate(crewName, localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 존재하지 않습니다."));
    }

    public List<Attendance> findAttendancesByCrewName(String crewName, int year, int month) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrewName(crewName))
                .filter(attendance -> attendance.isSameYearAndMonth(year, month))
                .toList();
    }

    public long getStatusCount(Map<LocalDate, Attendance> monthlyAttendances, AttendanceStatus attendanceStatus) {
        return monthlyAttendances.values().stream()
                .filter(attendance -> (attendance == null && attendanceStatus == AttendanceStatus.ABSENT) ||
                        (attendance != null && attendance.checkStatus().equals(attendanceStatus)))
                .count();
    }

    public Map<LocalDate, Attendance> getMonthlyAttendanceMap(String crewName, int year, int month) {
        Map<LocalDate, Attendance> monthlyAttendances = new HashMap<>();

        List<Attendance> crewAttendances = findAttendancesByCrewName(crewName, year, month);

        for (Attendance attendance : crewAttendances) {
            LocalDate attendanceDate = attendance.getAttendanceTime().getLocalDate();
            monthlyAttendances.put(attendanceDate, attendance);
        }

        LocalDate today = LocalDate.now();
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        LocalDate endDate;
        if (today.isBefore(lastDayOfMonth)) {
            endDate = today.minusDays(1);
        } else {
            endDate = lastDayOfMonth;
        }

        for (LocalDate date = firstDay; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (isWeekday(date) && !monthlyAttendances.containsKey(date)) {
                monthlyAttendances.put(date, null);
            }
        }

        return monthlyAttendances;
    }

    private boolean isWeekday(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5;
    }

}
