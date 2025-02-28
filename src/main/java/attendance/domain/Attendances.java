package attendance.domain;

import java.time.LocalDate;
import java.util.List;
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

    public long getStatusCount(AttendanceStatus status, String crewName, LocalDate localDate) {
        return findAttendancesByCrewName(crewName, localDate.getYear(), localDate.getMonthValue()).stream()
                .filter(attendance -> attendance.checkStatus().equals(status))
                .count();
    }
}
