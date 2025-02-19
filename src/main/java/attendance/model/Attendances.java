package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Attendances {

    private final CrewGroup crewGroup;
    private final Set<Attendance> attendances;

    public Attendances(CrewGroup crewGroup, Set<Attendance> attendances) {
        this.crewGroup = crewGroup;
        this.attendances = new HashSet<>(attendances);
    }

    public void validateExistNickname(String nickname) {
        boolean isNotExistsCrew = !crewGroup.contains(nickname);
        if (isNotExistsCrew) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public void add(Attendance attendance) {
        if (attendances.contains(attendance)) {
            throw new IllegalArgumentException("크루는 같은 날에 또 출석할 수 없습니다.");
        }
        attendances.add(attendance);
    }

    public void update(Attendance attendance) {
        attendances.add(attendance);
    }

    public Optional<Attendance> findByCrewAndDate(Crew crew, LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.equals(new Attendance(crew, LocalDateTime.of(date, LocalTime.MIN))))
                .findFirst();
    }
}
