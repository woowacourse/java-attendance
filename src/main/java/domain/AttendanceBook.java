package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AttendanceBook {
    private static final List<Integer> HOLIDAYS = List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29);

    private final Map<String, Attendances> crewsRecords;

    public AttendanceBook(Map<String, Attendances> crewsRecords) {
        this.crewsRecords = crewsRecords;
    }

    public Attendance addAttendanceForCrew(String nickname, LocalDateTime dateTime) {
        Attendances attendances = getCrewRecords(nickname);
        return attendances.addAttendance(dateTime);
    }

    public void updateAttendanceForCrew(String nickname, LocalDateTime dateTime, LocalDateTime today) {
        Attendances attendances = getCrewRecords(nickname);
        attendances.updateAttendance(dateTime, today.getDayOfMonth());
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

    public List<String> getRiskOfExpelledCrews(LocalDateTime today) {
        int weekDaysCount = calculateWeekDaysCount(today);

        return crewsRecords.entrySet().stream()
                .filter(entry -> entry.getValue().calculateCrewStatus(weekDaysCount) != CrewStatus.NORMAL)
                .map(Entry::getKey)
                .toList();
    }

    private int calculateWeekDaysCount(LocalDateTime today) {
        int weekDaysCount = 0;
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            if (!HOLIDAYS.contains(day)) {
                weekDaysCount++;
            }
        }
        return weekDaysCount;
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
