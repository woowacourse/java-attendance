package domain;

import dto.AttendanceData;
import dto.ModifyResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import util.DateTimeManager;

public class AttendanceHistory {
    private static final String ALREADY_ATTENDANCE = "이미 출석되었습니다.";
    private static final String NO_ATTENDANCE = "기존의 출석 기록이 없습니다.";

    private final List<Attendance> attendanceInfo;

    public AttendanceHistory() {
        this.attendanceInfo = new ArrayList<>();
    }

    public int getAbsentCount() {
        return getOriginalAbsentCount() + (getLateCount() / 3);
    }

    public int getLateCount() {
        return getStateCount(Status.LATE);
    }

    public int getOriginalAbsentCount() {
        return getStateCount(Status.ABSENCE);
    }

    public AttendanceData getAttendanceHistory(LocalDate lastDate) {
        updateUntil(lastDate);
        return new AttendanceData(Collections.unmodifiableList(attendanceInfo));
    }

    public Attendance addAttendance(LocalDateTime localDateTime) {
        validateNotExisted(localDateTime);
        Attendance attendance = new Attendance(localDateTime);
        attendanceInfo.add(attendance);
        return attendance;
    }

    public ModifyResult update(LocalDateTime newDateAndTime) {
        Attendance oldAttendance = getAlreadyExistAttendance(newDateAndTime);
        validateExisted(oldAttendance);
        attendanceInfo.remove(oldAttendance);
        Attendance attendance = new Attendance(newDateAndTime);
        attendanceInfo.add(attendance);
        return new ModifyResult(oldAttendance, attendance);
    }

    public void updateUntil(LocalDate lastDate) {
        for (int i = 1; i <= lastDate.getDayOfMonth(); i++) {
            LocalDate date = LocalDate.of(2024, 12, i);
            if (DateTimeManager.isHoliday(date)) {
                continue;
            }
            if (!containsDayOfMonth(i)) {
                attendanceInfo.add(new Attendance(
                        LocalDateTime.of(date,
                        LocalTime.of(15, 0))));
            }
        }
    }

    private Attendance getAlreadyExistAttendance(LocalDateTime localDateTime) {
        Optional<Attendance> sameDateAttendance = attendanceInfo.stream()
                .filter(attendance ->
                        attendance.isEqualDate(localDateTime))
                .findFirst();
        return sameDateAttendance.orElse(null);
    }

    private int getStateCount(Status status) {
        return (int) attendanceInfo.stream()
                .filter(a -> a.getStatus().equals(status))
                .count();
    }

    private boolean containsDayOfMonth(int i) {
        return attendanceInfo.stream()
                .anyMatch(a -> a.getDayOfMonth() == i);
    }

    private void validateNotExisted(LocalDateTime localDateTime) {
        Attendance isAlreadyExisted = getAlreadyExistAttendance(localDateTime);
        if (isAlreadyExisted != null) {
            throw new IllegalArgumentException(ALREADY_ATTENDANCE);
        }
    }

    private void validateExisted(Attendance oldAttendance) {
        if (oldAttendance == null) {
            throw new IllegalArgumentException(NO_ATTENDANCE);
        }
    }
}
