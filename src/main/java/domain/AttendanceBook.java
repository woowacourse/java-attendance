package domain;

import static global.utils.DateTimeUtil.convertDateWithDayOfWeekFormat;
import static global.utils.DateTimeUtil.getFixedRunningDate;
import static global.utils.DateTimeUtil.isDateInAvailableAttendance;
import static global.utils.DateTimeUtil.isOutOfRunningTime;
import static global.utils.DateTimeUtil.isWeekday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<Crew, AttendanceRecord> attendance;

    public AttendanceBook() {
        attendance = new HashMap<>();
    }

    public void initAttendance(Crew crew, LocalDateTime dateTime) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        if (attendance.containsKey(crew)) {
            attendanceRecord = attendance.get(crew);
        }
        attendanceRecord.applyAttendanceDate(dateTime);
        attendance.put(crew, attendanceRecord);
    }

    public boolean containsCrew(Crew crew) {
        return attendance.containsKey(crew);
    }

    public AttendanceRecord findAttendanceRecord(Crew crew) {
        return attendance.get(crew);
    }

    public AttendanceDate findAttendanceDateByDate(Crew crew, LocalDate date) {
        return findAttendanceRecord(crew).getAttendanceDate(date);
    }

    public boolean hasAttendanceDate(Crew crew, LocalDate date) {
        return findAttendanceRecord(crew).hasAttendanceDate(date);
    }

    public void attend(Crew crew, LocalDate date, LocalTime time) {
        validateIsInRunningTime(time);
        findAttendanceRecord(crew).applyAttendanceDate(date, time);
    }

    public void edit(Crew crew, LocalDate date, LocalTime time) {
        validateIsInRunningTime(time);
        findAttendanceRecord(crew).applyAttendanceDate(date, time);
    }

    public int getAttendanceCount(Crew crew) {
        return findAttendanceRecord(crew).calculateAttendanceCount();
    }

    public int getTardyCount(Crew crew) {
        return findAttendanceRecord(crew).calculateTardyCount();
    }

    public int getAbsenceCount(Crew crew) {
        return findAttendanceRecord(crew).calculateAbsenceCount();
    }

    public RiskStatusResult getRiskStatusResult(Crew crew) {
        return new RiskStatusResult(crew, getAttendanceCount(crew), getTardyCount(crew), getAbsenceCount(crew));
    }

    public List<RiskStatusResult> getRiskStatusResults() {
        return attendance.keySet().stream()
                .map(this::getRiskStatusResult)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void validateBeforeAdd(Crew crew, LocalDate date) {
        findAttendanceRecord(crew).validateBeforeAdd(date);
    }

    public void validateBeforeAdd(Crew crew) {
        validateBeforeAdd(crew, getFixedRunningDate());
    }

    public void validateBeforeEdit(Crew crew, LocalDate date) {
        findAttendanceRecord(crew).validateBeforeEdit(date);
    }

    public void validateIsWeekday(LocalDate date) {
        if (!isWeekday(date)) {
            throw new IllegalArgumentException(String.format("%s은 등교일이 아닙니다.", convertDateWithDayOfWeekFormat(date)));
        }
    }

    public void validateIsInRunningTime(LocalTime time) {
        if (isOutOfRunningTime(time)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public void validateIsAvailableAttendance(LocalDate date) {
        if (!isDateInAvailableAttendance(date)) {
            throw new IllegalArgumentException("허용되지 않는 날짜입니다.");
        }
    }
}
