package domain;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<Crew, Attendances> crewsAttendances = new HashMap<>();

    public void recordAttendance(Crew crew, Attendance attendance) {
        if (crewsAttendances.containsKey(crew)) {
            crewsAttendances.get(crew).add(attendance);
            return;
        }
        crewsAttendances.put(crew, new Attendances());
        crewsAttendances.get(crew).add(attendance);
    }

    public Attendances getAttendances(Crew crew) {
        validateCrew(crew);
        return crewsAttendances.get(crew);
    }

    public void recordAllAbsences(Clock clock) {
        crewsAttendances.forEach((crew, attendances) -> attendances.recordAbsences(clock));
    }

    public Map<Crew, Attendances> getPenaltyHistory(Clock clock) {
        return Collections.unmodifiableMap(
                crewsAttendances.entrySet().stream()
                        .filter(crew -> crew.getValue().getPenaltyStatus(clock) != null)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().toImmutable()
                        ))
        );
    }

    public void isAlreadyAttended(Crew crew, LocalDate date) {
        validateCrew(crew);
        if (crewsAttendances.get(crew).isAlreadyAttended(date)) {
            throw new IllegalStateException("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
        }
    }

    private void validateCrew(Crew crew) {
        if (!crewsAttendances.containsKey(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 크루입니다.");
        }
    }
}
