package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import util.Day;

public class Attendances {
    public static final int ABSENT_HOUR = 23;
    public static final int ABSENT_MINUTE = 59;

    private final List<Attendance> attendanceLog = new ArrayList<>();

    public void addAttendance(Attendance attendance) {
        attendanceLog.add(attendance);
    }

    public AttendanceAlertLevel calculateAttendanceAlertLevel() {
        return AttendanceAlertLevel.calculateAttendanceAlertLevel(countAbsent(), countLate());
    }

    public int countPresent() {
        return Math.toIntExact(attendanceLog.stream()
                .filter(attendance -> attendance.calculateAttendanceStatus().equals(AttendanceStatus.PRESENT))
                .count());
    }

    public int countLate() {
        return Math.toIntExact(attendanceLog.stream()
                .filter(attendance -> attendance.calculateAttendanceStatus().equals(AttendanceStatus.LATE))
                .count());
    }

    public int countAbsent() {
        return Math.toIntExact(attendanceLog.stream()
                .filter(attendance -> attendance.calculateAttendanceStatus().equals(AttendanceStatus.ABSENT))
                .count());
    }

    public Optional<Attendance> getSpecificAttendance(LocalDate specificDate) {
        return attendanceLog.stream()
                .filter(attendance -> attendance.isSameDay(specificDate))
                .findAny();
    }

    public void addAbsent(LocalDateTime today) {
        int dayOfMonth = today.getDayOfMonth();
        List<Integer> attendanceDays = attendanceLog.stream()
                .map(Attendance::getDay)
                .toList();
        List<Integer> weekDays = new ArrayList<>();

        for (int day = 1; day < dayOfMonth; day++) {
            weekDays.add(day);
        }

        weekDays = weekDays.stream()
                .filter(day -> !Day.isHoliday(day, today))
                .collect(Collectors.toList());

        weekDays.removeAll(attendanceDays);
        for (int day : weekDays) {
            attendanceLog.add(
                    new Attendance(
                            LocalDateTime.of(today.getYear(), today.getMonth(), day, ABSENT_HOUR, ABSENT_MINUTE)));
        }
    }

    public Attendance changeAttendance(LocalDate date, Time time) {
        Attendance targetAttendance = getSpecificAttendance(date).get();
        targetAttendance.updateAttendance(time);
        return targetAttendance;
    }

    public List<Attendance> getAttendanceLog() {
        return attendanceLog;
    }
}
