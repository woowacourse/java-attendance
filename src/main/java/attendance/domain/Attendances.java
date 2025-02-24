package attendance.domain;

import attendance.exception.AttendanceArgumentException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Attendances {
    private final Map<LocalDate, Attendance> attendances = new HashMap<>();
    private static final String DUPLICATE_ATTENDANCE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";
    private static final String NOT_EXIST_ATTENDANCE = "해당 날짜에 출석이 존재하지 않습니다.";
    static final int MONDAY = 1;
    static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    static final LocalTime NORMAL_START_TIME = LocalTime.of(10, 0);
    static final int LATE_MINUTE = 5;
    static final int ABSENCE_MINUTE = 30;


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
        return attendances.get(datetime)
                .time();
    }

    public String getAttendanceStatus(LocalDate date) {
        return attendances.get(date)
                .attendanceStatus()
                .getStatus();
    }

    public void modifyAttendance(LocalDate modifyDate, LocalTime afterModifyTime) {
        validateIsExistAttendanceHistory(modifyDate);
        Attendance previousAttendance = attendances.remove(modifyDate);
        try {
            addAttendance(afterModifyTime, modifyDate);
        } catch (AttendanceArgumentException e) {
            attendances.put(modifyDate, previousAttendance);
            throw e;
        }
    }

    public void validateIsExistAttendanceHistory(LocalDate date) {
        if (attendances.get(date) == null) {
            throw new AttendanceArgumentException(NOT_EXIST_ATTENDANCE);
        }
    }

    public Attendance getAttendance(LocalDate date) {
        return attendances.get(date);
    }
}
