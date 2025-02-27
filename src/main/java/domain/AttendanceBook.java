package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<String, Attendances> crewsRecords;

    public AttendanceBook(Map<String, Attendances> crewsRecords) {
        this.crewsRecords = crewsRecords;
    }

    public Attendance addAttendanceForCrew(String nickname, LocalDateTime dateTime) {
        Attendances attendances = getCrewRecords(nickname);
        return attendances.addAttendance(dateTime);
    }

    public Attendance updateAttendanceForCrew(String nickname, LocalDateTime dateTime, LocalDate today) {
        Attendances attendances = getCrewRecords(nickname);
        return attendances.updateAttendance(dateTime, today.getDayOfMonth());
    }

    public Map<String, Attendances> getRiskOfExpelledCrews(LocalDate today) {
        return crewsRecords.entrySet().stream()
                .filter(entry -> entry.getValue().calculateCrewStatus(today) != CrewStatus.NORMAL)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Attendance getAttendanceByNicknameAndDate(String nickname, int day) {
        Attendances attendances = getCrewRecords(nickname);
        return attendances.getAttendanceByDay(day);
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

    public Attendances getAttendanceByNickname(String nickname) {
        return crewsRecords.get(nickname);
    }

    public Map<String, Attendances> getCrewsRecords() {
        return crewsRecords;
    }
}
