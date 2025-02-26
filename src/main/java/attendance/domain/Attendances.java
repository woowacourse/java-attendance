package attendance.domain;

import attendance.exception.AttendanceArgumentException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Attendances {
    private static final String DUPLICATE_ATTENDANCE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";
    private static final String NOT_EXIST_ATTENDANCE = "해당 날짜에 출석이 존재하지 않습니다.";
    private static final int MONDAY = 1;
    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime NORMAL_START_TIME = LocalTime.of(10, 0);
    private static final int LATE_MINUTE = 5;
    private static final int ABSENCE_MINUTE = 30;

    private final Map<LocalDate, Attendance> attendances = new HashMap<>();

    private AttendanceStatus determineAttendanceStatus(LocalDate currentDate, LocalTime currentTime) {
        LocalTime startTime = determineAttendanceStartTime(currentDate);
        if (currentTime.isAfter(startTime.plusMinutes(ABSENCE_MINUTE))) {
            return AttendanceStatus.ABSENCE;
        }
        if (currentTime.isAfter(startTime.plusMinutes(LATE_MINUTE))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private LocalTime determineAttendanceStartTime(LocalDate currentDate) {
        if (currentDate.getDayOfWeek().getValue() == MONDAY) {
            return MONDAY_START_TIME;
        }
        return NORMAL_START_TIME;
    }

    public void addAttendance(LocalTime currentTime, LocalDate currentDate) {
        AttendanceStatus attendanceStatus = determineAttendanceStatus(currentDate, currentTime);
        Attendance attendance = new Attendance(attendanceStatus, currentTime);
        if (attendances.containsKey(currentDate)) {
            throw new AttendanceArgumentException(DUPLICATE_ATTENDANCE_DATE);
        }
        attendances.put(currentDate, attendance);
    }

    public LocalTime getAttendanceTime(LocalDate datetime) {
        return attendances.get(datetime).time();
    }

    public AttendanceStatus getAttendanceStatus(LocalDate date) {
        return attendances.get(date).attendanceStatus();
    }

    public void modifyAttendance(LocalDate modifyDate, LocalTime afterModifyTime) {
        validateIsExistAttendanceHistory(modifyDate);
        AttendanceStatus attendanceStatus = determineAttendanceStatus(modifyDate, afterModifyTime);
        Attendance attendance = new Attendance(attendanceStatus, afterModifyTime);
        attendances.put(modifyDate, attendance);
    }

    public void validateIsExistAttendanceHistory(LocalDate date) {
        if (!isAttendanceExist(date)) {
            throw new AttendanceArgumentException(NOT_EXIST_ATTENDANCE);
        }
    }

    public boolean isAttendanceExist(LocalDate date) {
        return attendances.get(date) != null;
    }

    public Attendance getAttendance(LocalDate date) {
        return attendances.get(date);
    }
}
