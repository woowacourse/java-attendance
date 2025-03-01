package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, Attendances> crewsAttendances = new HashMap<>();

    private static void updatePenaltyHistory(Crew crew, Attendances attendances,
                                             Map<Crew, Attendances> penaltyHistory) {
        if (attendances.getPenaltyStatus() != null) {
            penaltyHistory.put(crew, attendances);
        }
    }

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

    public void recordAllAbsences() {
        crewsAttendances.forEach((crew, attendances) -> attendances.recordAbsences());
    }

    public Map<Crew, Attendances> getPenaltyHistory() {
        Map<Crew, Attendances> penaltyHistory = new HashMap<>();
        crewsAttendances.forEach((nickname, attendances) ->
                updatePenaltyHistory(nickname, attendances, penaltyHistory)
        );
        return penaltyHistory;
    }

    public void isAlreadyAttended(Crew crew, LocalDate date) {
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
