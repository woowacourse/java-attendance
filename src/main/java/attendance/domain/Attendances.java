package attendance.domain;

import java.time.LocalDate;
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
                .orElse(null);
    }
}
