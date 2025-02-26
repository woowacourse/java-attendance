package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Attendances> crewsRecords;

    public AttendanceBook(Map<String, Attendances> crewsRecords) {
        this.crewsRecords = crewsRecords;
    }

    public void addAttendanceForCrew(String nickname, LocalDateTime dateTime) {
        Attendances attendances = getCrewRecords(nickname);
        attendances.addAttendance(dateTime);
    }

    public List<Attendance> getPreviousCrewRecords(String nickname, LocalDateTime today) {
        validateCrewNickname(nickname);
        return crewsRecords.get(nickname)
                .getRecords()
                .stream()
                .filter(attendance -> attendance.isBefore(today))
                .sorted()
                .toList();
    }

    private Attendances getCrewRecords(String nickname) {
        validateCrewNickname(nickname);
        return crewsRecords.get(nickname);
    }

    private void validateCrewNickname(String nickname) {
        if (!crewsRecords.containsKey(nickname)) {
            throw new IllegalArgumentException("존재하지 않는 닉네임입니다.");
        }
    }

    public Map<String, Attendances> getCrewsRecords() {
        return crewsRecords;
    }
}
