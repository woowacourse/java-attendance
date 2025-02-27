package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import attendance.constant.AttendanceConstant;

public class Crew {

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
        validateAttendanceTime(time);
        attendanceRecords.put(date, time);
    }

    private void validateAttendanceDate(LocalDate date) {
        if (attendanceRecords.containsKey(date)) {
            throw new IllegalArgumentException("이미 출석한 경우 다시 출석할 수 없습니다. 출석 수정 기능을 이용해 주세요.");
        }
        if (DayOff.isDayOff(date)) {
            throw new IllegalArgumentException("주말 및 공휴일에는 등교가 불가능합니다.");
        }
    }

    private void validateAttendanceTime(LocalTime time) {
        if (time.isBefore(AttendanceConstant.CAMPUS_OPEN_TIME) ||
            time.isAfter(AttendanceConstant.CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException("캠퍼스 운영시간 내에만 출석할 수 있습니다.");
        }
    }

    public void modifyAttendance(LocalDate date, LocalTime time) {
        validateModifyAttendanceDate(date);
        attendanceRecords.put(date, time);
    }

    private void validateModifyAttendanceDate(LocalDate date) {
        if (!attendanceRecords.containsKey(date)) {
            throw new IllegalArgumentException("출석 기록이 존재하지 않습니다.");
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
