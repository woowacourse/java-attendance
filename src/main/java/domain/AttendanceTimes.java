package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceTimes {

    private List<AttendanceTime> attendanceTimes;

    public AttendanceTimes(List<LocalDateTime> attendanceTimes, LocalDate nowDate) {
        this.attendanceTimes = attendanceTimes.stream()
                .map(AttendanceTime::new)
                .collect(Collectors.toList());

        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = nowDate;
        if (endDate.isAfter(LocalDate.of(2024, 12, 31))) {
            endDate = LocalDate.of(2025, 1, 1);
        }

        initializeUnattend(startDate, endDate);
    }

    private void initializeUnattend(LocalDate startDate, LocalDate endDate) {
        Set<LocalDate> attendanceDates = this.attendanceTimes.stream()
                .map(attendanceTime -> attendanceTime.getAttendanceDateTime().toLocalDate())
                .collect(Collectors.toSet());

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            addUnattended(attendanceDates, date);
        }
    }

    private void addUnattended(Set<LocalDate> attendanceDates, LocalDate date) {
        if (!(attendanceDates.contains(date) || isClosed(date))) {
            attendanceTimes.add(new AttendanceTime(date, AttendanceStatus.UNATTEND));
        }
    }

    private boolean isClosed(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(LocalDate.of(2024, 12, 25));
    }

    public void addAttendance(AttendanceTime attendanceTime) {
        this.attendanceTimes.add(attendanceTime);
    }

    public int getAbsentCount() {
        int absentCount = (int) attendanceTimes.stream()
                .filter(e -> e.getAttendanceStatus().equals(AttendanceStatus.ABSENT) || e.getAttendanceStatus().equals(AttendanceStatus.UNATTEND))
                .count();

        int lateCount = (int) attendanceTimes.stream()
                .filter(e -> e.getAttendanceStatus().equals(AttendanceStatus.LATE))
                .count();

        return absentCount + lateCount / 3;
    }

    public int getLateCount() {
        return (int) attendanceTimes.stream()
                .filter(e -> e.getAttendanceStatus().equals(AttendanceStatus.LATE))
                .count();
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatuses() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new HashMap<>();
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            attendanceStatuses.put(attendanceStatus, 0);
        }
        for (AttendanceTime attendanceTime : attendanceTimes) {
            attendanceStatuses.put(attendanceTime.getAttendanceStatus(), attendanceStatuses.get(attendanceTime.getAttendanceStatus()) + 1);
        }
        return attendanceStatuses;
    }

    public boolean checkAttended(LocalDate attendanceDate) {
        for (AttendanceTime attendances : attendanceTimes) {
            if (attendances.checkAttended(attendanceDate)) {
                return true;
            }
        }
        return false;
    }

    public List<AttendanceTime> getAttendanceTimes() {
        return this.attendanceTimes;
    }

    public AttendanceTime getAttendanceTime(LocalDate attendanceDate) {
        return attendanceTimes.stream()
                .filter(attendance -> attendance.getAttendanceDateTime().toLocalDate().equals(attendanceDate))
                .filter(attendance -> attendance.getAttendanceStatus() != AttendanceStatus.UNATTEND)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석하지 않은 날짜입니다."));
    }
}
