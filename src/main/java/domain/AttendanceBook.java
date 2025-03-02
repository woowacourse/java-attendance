package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static global.utils.DateTimeUtil.*;

public class AttendanceBook {
    private final Map<String, AttendanceRecord> attendance;

    public AttendanceBook() {
        attendance = new HashMap<>();
    }

    public void initAttendance(String name, LocalDateTime dateTime) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        if (attendance.containsKey(name)) {
            attendanceRecord = attendance.get(name);
        }
        attendanceRecord.applyAttendanceDate(dateTime);
        attendance.put(name, attendanceRecord);
    }

    public boolean hasCrew(String name) {
        return attendance.containsKey(name);
    }

    public AttendanceRecord findAttendanceRecordByName(String name) {
        return attendance.get(name);
    }

    public AttendanceDate findAttendanceDateByNameAndDate(String name, LocalDate date) {
        return findAttendanceRecordByName(name).getAttendanceDate(date);
    }

    public boolean hasAttendanceDate(String name, LocalDate date) {
        return findAttendanceRecordByName(name).hasAttendanceDate(date);
    }

    public void attend(String name, LocalDate date, LocalTime time) {
        findAttendanceRecordByName(name).applyAttendanceDate(date , time);
    }

    public void edit(String name, LocalDate date, LocalTime time) {
        findAttendanceRecordByName(name).applyAttendanceDate(date, time);
    }

    public int getAttendanceCount(String name) {
        return findAttendanceRecordByName(name).calculateAttendanceCount();
    }

    public int getTardyCount(String name) {
        return findAttendanceRecordByName(name).calculateTardyCount();
    }

    public int getAbsenceCount(String name) {
        return findAttendanceRecordByName(name).calculateAbsenceCount();
    }

    public RiskStatusResult getRiskStatusResult(String name) {
        return new RiskStatusResult(name, getAttendanceCount(name), getTardyCount(name), getAbsenceCount(name));
    }

    public List<RiskStatusResult> getRiskStatusResults() {
        return attendance.keySet().stream()
                .map(this::getRiskStatusResult)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void validateHasCrew(String name) {
        if (!hasCrew(name)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public void validateBeforeAdd(String name, LocalDate date) {
        findAttendanceRecordByName(name).validateBeforeAdd(date);
    }

    public void validateBeforeEdit(String name, LocalDate date) {
        findAttendanceRecordByName(name).validateBeforeEdit(date);
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
