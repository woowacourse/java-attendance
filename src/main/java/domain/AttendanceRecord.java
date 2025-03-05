package domain;

import static global.utils.DateTimeUtil.getFirstDayOfMonth;
import static global.utils.DateTimeUtil.getFixedRunningDate;
import static global.utils.DateTimeUtil.isWeekday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecord {
    private final List<AttendanceDate> attendanceDates;

    public AttendanceRecord() {
        attendanceDates = new ArrayList<>();
        initRecord();
    }

    public void initRecord() {
        LocalDate targetDate = getFirstDayOfMonth(getFixedRunningDate());
        while (targetDate.isBefore(getFixedRunningDate())) {
            applyAttendanceDateIfIsWeekday(targetDate);
            targetDate = targetDate.plusDays(1);
        }
    }

    private void applyAttendanceDateIfIsWeekday(LocalDate date) {
        if (isWeekday(date)) {
            applyAttendanceDate(date);
        }
    }

    private void applyAttendanceDate(LocalDate date) {
        if (isAlreadyAttend(date)) {
            int index = attendanceDates.indexOf(getAttendanceDate(date));
            attendanceDates.set(index, new AttendanceDate(date));
            return;
        }
        attendanceDates.add(new AttendanceDate(date));
    }

    public void applyAttendanceDate(LocalDateTime dateTime) {
        if (isAlreadyAttend(dateTime.toLocalDate())) {
            int index = attendanceDates.indexOf(getAttendanceDate(dateTime.toLocalDate()));
            attendanceDates.set(index, new AttendanceDate(dateTime));
            return;
        }
        attendanceDates.add(new AttendanceDate(dateTime));
    }

    public void applyAttendanceDate(LocalDate date, LocalTime time) {
        if (isAlreadyAttend(date)) {
            int index = attendanceDates.indexOf(getAttendanceDate(date));
            attendanceDates.set(index, new AttendanceDate(date, time));
            return;
        }
        attendanceDates.add(new AttendanceDate(date , time));
    }

    public boolean hasAttendanceDate(LocalDate date) {
        return !getAttendanceDate(date).getStatus().equals(AttendanceStatus.NONE);
    }

    public boolean isAlreadyAttend(LocalDate date) {
        return attendanceDates.stream()
                .anyMatch(attendanceDate -> attendanceDate.isSameDate(date));
    }

    public AttendanceDate getAttendanceDate(LocalDate date) {
        return attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.isSameDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록을 찾을 수 없습니다."));
    }

    public List<AttendanceDate> getAttendanceDates() {
        return new ArrayList<>(attendanceDates);
    }

    public int calculateAttendanceCount() {
        return (int) attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.getStatus().equals(AttendanceStatus.ATTENDANCE))
                .count();
    }

    public int calculateTardyCount() {
        return (int) attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.getStatus().equals(AttendanceStatus.TARDY))
                .count();
    }

    public int calculateAbsenceCount() {
        return (int) attendanceDates.stream()
                .filter(attendanceDate -> attendanceDate.getStatus().equals(AttendanceStatus.ABSENCE) || attendanceDate.getStatus().equals(AttendanceStatus.NONE))
                .count();
    }

    public void validateBeforeAdd(LocalDate date) {
        if (hasAttendanceDate(date)) {
            throw new IllegalArgumentException("이미 출석하여, 다시 출석할 수 없습니다. 수정 기능을 이용하십시오.");
        }
    }

    public void validateBeforeEdit(LocalDate date) {
        if (!(isAlreadyAttend(date) && hasAttendanceDate(date))) {
            throw new IllegalArgumentException("출석 기록이 없어 수정할 수 없습니다.");
        }
    }
}
