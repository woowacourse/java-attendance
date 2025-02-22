package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import attendance.exception.AttendanceArgumentException;

public class Attendances {
    private static final String DUPLICATE_ATTENDANCE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";
    private static final String NOT_EXIST_ATTENDANCE = "해당 날짜에 출석이 존재하지 않습니다.";

    private final Map<LocalDate, Attendance> attendances = new HashMap<>();

    private AttendanceStatus determineAttendanceStatus(LocalDate currentDate, LocalTime currentTime) {
        LocalTime startTime = determineAttendanceStartTime(currentDate);
        if (currentTime.isAfter(startTime.plusMinutes(AttendanceManagerHelper.ABSENCE_MINUTE))) {
            return AttendanceStatus.ABSENCE;
        }
        if (currentTime.isAfter(startTime.plusMinutes(AttendanceManagerHelper.LATE_MINUTE))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private LocalTime determineAttendanceStartTime(LocalDate currentDate) {
        if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
            return AttendanceManagerHelper.MONDAY_START_TIME;
        }
        return AttendanceManagerHelper.NORMAL_START_TIME;
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
        return attendances.get(datetime)
            .time();
    }

    public AttendanceStatus getAttendanceStatus(LocalDate date) {
        return attendances.get(date)
            .attendanceStatus();
    }

    public void modifyAttendance(LocalDate modifyDate, LocalTime afterModifyTime) {
        validateIsExistAttendanceHistory(modifyDate);
        Attendance prevAttendance = attendances.remove(modifyDate);
        try {
            addAttendance(afterModifyTime, modifyDate);
        } catch (AttendanceArgumentException e) {
            attendances.put(modifyDate, prevAttendance);
            throw e;
        }
    }

    public void validateIsExistAttendanceHistory(LocalDate date) {
        if (attendances.get(date) == null) {
            throw new AttendanceArgumentException(NOT_EXIST_ATTENDANCE);
        }
    }
}
