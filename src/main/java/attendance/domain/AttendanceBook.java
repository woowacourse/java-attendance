package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class AttendanceBook {
    private final String crewName;
    private final Map<LocalDate, Attendance> timestamps;

    public AttendanceBook(String crewName) {
        this.crewName = crewName;
        this.timestamps = new HashMap<>();
    }

    public LocalDateTime attend(LocalDate date, LocalTime time) {
        if (isAttendedDate(date)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 있습니다. 출석 수정 기능을 이용해주세요.");
        }
        AttendanceChecker.checkCampusOpen(date, time);

        updateTimeStamp(date, time);
        return LocalDateTime.of(date, time);
    }

    public Attendance modify(LocalDate date, LocalTime time) {
        AttendanceChecker.checkCampusOpen(date, time);

        Attendance prevAttendance = timestamps.get(date);

        updateTimeStamp(date, time);
        return prevAttendance;
    }

    public Map<AttendanceStatus, Integer> getTotalStatusCount(int today) {
        Map<AttendanceStatus, Integer> statusCount = new EnumMap<>(AttendanceStatus.class);

        Arrays.stream(AttendanceStatus.values())
                .forEach(status -> statusCount.put(status, getTotalStatusCount(status)));

        statusCount.put(ABSENCE, statusCount.get(ABSENCE) + countBlankAttendanceAbsence(today));

        return statusCount;
    }

    private int getTotalStatusCount(AttendanceStatus status) {
        return (int) timestamps.values().stream()
                .filter(attendance -> attendance.status() == status)
                .count();
    }

    private int countBlankAttendanceAbsence(int today) {
        LocalDate now = LocalDate.now();
        return (int) IntStream.range(1, today - 1)
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> !timestamps.containsKey(date))
                .filter(date -> AttendanceChecker.isCampusOpenDate(date))
                .count();
    }

    private void updateTimeStamp(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(time, date);

        timestamps.put(date, attendance);
    }

    public boolean isNameMatched(String crewName) {
        return this.crewName.equals(crewName);
    }

    private boolean isAttendedDate(LocalDate attendDate) {
        return timestamps.containsKey(attendDate);
    }
}
