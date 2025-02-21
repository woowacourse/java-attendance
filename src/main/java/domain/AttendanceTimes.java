package domain;

import constant.CampusConstant;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceTimes {

    private static final int LATE_TO_ABSENT_UNIT = 3;

    private List<AttendanceTime> attendanceTimes;

    public AttendanceTimes(List<LocalDateTime> attendanceTimes, LocalDate nowDate) {
        this.attendanceTimes = attendanceTimes.stream()
                .map(AttendanceTime::new)
                .collect(Collectors.toList());

        LocalDate startDate = CampusConstant.DECEMBER_START_DATE;
        LocalDate endDate = nowDate;
        if (endDate.isAfter(CampusConstant.DECEMBER_END_DATE)) {
            endDate = CampusConstant.JANUARY_START_DATE;
        }

        initializeUnattended(startDate, endDate);
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

        return absentCount + lateCount / LATE_TO_ABSENT_UNIT;
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
        int count = (int) this.attendanceTimes.stream()
                .filter(a -> a.checkAttended(attendanceDate))
                .count();

        return count > 0;
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

    private void initializeUnattended(LocalDate startDate, LocalDate endDate) {
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
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isEqual(CampusConstant.CHRISTMAS);
    }
}
