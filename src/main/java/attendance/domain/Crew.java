package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import attendance.exception.ExceptionMessage;

public class Crew {

    public static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 00);
    public static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 00);

    private final String name;
    private final Map<LocalDate, LocalTime> attendanceRecords = new HashMap<>();

    public Crew(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void attendance(LocalDate date, LocalTime time) {
        validateAttendanceDate(date);
        validateCampusTime(time);
        attendanceRecords.put(date, time);
    }

    private void validateAttendanceDate(LocalDate date) {
        if (attendanceRecords.containsKey(date)) {
            throw new IllegalArgumentException(ExceptionMessage.ALREADY_ATTENDANCE.getMessage(date));
        }
        if (DayOff.isDayOff(date)) {
            throw new IllegalArgumentException(ExceptionMessage.ATTENDANCE_ON_DAY_OFF.getMessage());
        }
    }

    private void validateCampusTime(LocalTime time) {
        if (time.isBefore(CAMPUS_OPEN_TIME) || time.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_CAMPUS_OPEN_TIME.getMessage(
                CAMPUS_OPEN_TIME, CAMPUS_CLOSE_TIME));
        }
    }

    public void modifyAttendance(LocalDate date, LocalTime time) {
        validateModifyAttendanceDate(date);
        validateCampusTime(time);
        attendanceRecords.put(date, time);
    }

    private void validateModifyAttendanceDate(LocalDate date) {
        if (!attendanceRecords.containsKey(date)) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_ATTENDANCE_DATA.getMessage(date));
        }
    }

    public LocalTime getAttendanceTimeOf(LocalDate date) {
        return attendanceRecords.get(date);
    }

    public AttendanceStatus getAttendanceStatusOf(LocalDate date) {
        if (isTruancy(date)) {
            return AttendanceStatus.ABSENCE;
        }
        return AttendanceStatus.from(date, attendanceRecords.get(date));
    }

    private boolean isTruancy(LocalDate date) {
        return !attendanceRecords.containsKey(date) && !DayOff.isDayOff(date);
    }

    public Risk getRisk(LocalDate today) {
        return Risk.of(getAttendanceStatistics(today));
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatistics(LocalDate today) {
        Map<AttendanceStatus, Integer> statistics = AttendanceStatus.getEmptyStatistics();

        IntStream.range(1, today.getDayOfMonth())
            .mapToObj(today::withDayOfMonth)
            .map(this::getAttendanceStatusOf)
            .forEach(status -> statistics.put(status, statistics.get(status) + 1));

        return statistics;
    }
}
