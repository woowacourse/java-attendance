package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Attendances> crewsAttendances = new HashMap<>();

    private static void updatePenaltyHistory(String nickname, Attendances attendances,
                                             Map<String, Attendances> penaltyHistory) {
        if (attendances.getPenaltyStatus() != null) {
            penaltyHistory.put(nickname, attendances);
        }
    }

    public void recordAttendance(String nickname, Attendance attendance) {
        if (crewsAttendances.containsKey(nickname)) {
            crewsAttendances.get(nickname).add(attendance);
            return;
        }
        crewsAttendances.put(nickname, new Attendances());
        crewsAttendances.get(nickname).add(attendance);
    }

    public Attendances getAttendances(String nickname) {
        validateNickname(nickname);
        return crewsAttendances.get(nickname);
    }

    public void recordAllAbsences() {
        crewsAttendances.forEach((nickname, attendances) -> attendances.recordAbsences());
    }

    public Map<String, Attendances> getPenaltyHistory() {
        Map<String, Attendances> penaltyHistory = new HashMap<>();
        crewsAttendances.forEach((nickname, attendances) ->
                updatePenaltyHistory(nickname, attendances, penaltyHistory)
        );
        return penaltyHistory;
    }

    public void isAlreadyAttended(String nickname, LocalDate date) {
        if (crewsAttendances.get(nickname).isAlreadyAttended(date)) {
            throw new IllegalStateException("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
        }
    }

    private void validateNickname(String nickname) {
        if (!crewsAttendances.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }
}
