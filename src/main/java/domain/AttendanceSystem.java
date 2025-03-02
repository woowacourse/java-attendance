package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceSystem {
    private final Map<Crew, AttendanceBook> attendanceBooks;

    public AttendanceSystem() {
        attendanceBooks = new HashMap<>();
    }

    private boolean hasNoCrew(Crew crew) {
        return !attendanceBooks.containsKey(crew);
    }

    public void editAttendance(Crew crew, AttendanceDate date, AttendanceTime time) {
        if (hasNoCrew(crew)) {
            attendanceBooks.put(crew, new AttendanceBook());
        }
        attendanceBooks.get(crew).attendance(date, time);
    }

    public Map<Crew, AttendanceBook> getRiskCrews() {
        return attendanceBooks.entrySet().stream()
                .filter(this::crewHasRisk)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new AttendanceBook(entry.getValue().getAttendanceBook(),
                                entry.getValue().getAttendanceStatuses().getAttendanceStatuses()))
                );
    }

    private boolean crewHasRisk(Map.Entry<Crew, AttendanceBook> entry) {
        return !entry.getValue().getRiskStatus().equals(RiskStatus.NONE);
    }

    public AttendanceBook findByCrew(Crew crew) {
        if (hasNoCrew(crew)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
        return attendanceBooks.get(crew);
    }
}
