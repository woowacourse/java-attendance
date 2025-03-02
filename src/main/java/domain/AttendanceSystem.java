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

    public int getAbsenceCount(Crew crew) {
        return attendanceBooks.get(crew).getAbsenceCount();
    }

    public int getTardyCount(Crew crew) {
        return attendanceBooks.get(crew).getTardyCount();
    }

    public int getAttendCount(Crew crew) {
        return attendanceBooks.get(crew).getAttendCount();
    }

    public RiskStatus getRisk(Crew crew) {
        return attendanceBooks.get(crew).getRiskStatus();
    }

    public Map<Crew, AttendanceBook> getRiskCrews() {
        return attendanceBooks.entrySet().stream()
                .filter(entry -> !entry.getValue().getRiskStatus().equals(RiskStatus.NONE))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new AttendanceBook(entry.getValue().getAttendanceBook(),
                                entry.getValue().getAttendanceStatuses().getAttendanceStatuses()))
                );
    }

    public AttendanceBook findByCrew(Crew crew) {
        if (hasNoCrew(crew)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
        return attendanceBooks.get(crew);
    }
}
