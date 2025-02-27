package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Attendances> crewsAttendances = new HashMap<>();

    public void recordAttendance(String nickname, Attendance attendance) {
        if (crewsAttendances.containsKey(nickname)) {
            crewsAttendances.get(nickname).add(attendance);
            return;
        }
        crewsAttendances.put(nickname, new Attendances());
        crewsAttendances.get(nickname).add(attendance);
    }

    public Attendances getAttendances(String nickname) {
        if (crewsAttendances.containsKey(nickname)) {
            return crewsAttendances.get(nickname);
        }
        throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    public void recordAllAbsences() {
        crewsAttendances.forEach((nickname, attendances) -> attendances.recordAbsences());
    }

    public Map<String, Attendances> getPenaltyHistory() {
        Map<String, Attendances> penaltyHistory = new HashMap<>();
        crewsAttendances.forEach((nickname, attendances) -> {
            if (attendances.getPenaltyStatus() != null) {
                penaltyHistory.put(nickname, attendances);
            }
        });
        return penaltyHistory;
    }
}
