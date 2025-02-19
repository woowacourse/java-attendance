package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import attendance.exception.AttendanceArgumentException;

public class Attendances {
    private final Map<LocalDate, Attendance> attendances = new HashMap<>();
    private static final String DUPLICATE_ATTENDANCE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";
    private static final String NOT_EXIST_ATTENDANCE = "해당 날짜에 출석이 존재하지 않습니다.";

    private void validateIsAttendanceAvailable(LocalDate currentDate) {
        if(currentDate.getDayOfWeek().getValue() >= AttendanceManagerHelper.WEEKEND_NUMBER) {
            String cannotAttendanceMessage = currentDate.format(DateTimeFormatter.ofPattern(
                    AttendanceManagerHelper.CANNOT_ATTENDANCE_WEEKEND_FORMAT, Locale.KOREA));
            throw new AttendanceArgumentException(cannotAttendanceMessage);
        }
    }

    private AttendanceStatus determineAttendanceStatus(LocalDate currentDate, LocalTime currentTime) {
        LocalTime startTime = determineAttendanceStartTime(currentDate);
        validateIsAttendanceAvailable(currentDate);
        if(currentTime.isAfter(startTime.plusMinutes(AttendanceManagerHelper.ABSENCE_MINUTE))){
            return AttendanceStatus.ABSENCE;
        }
        if(currentTime.isAfter(startTime.plusMinutes(AttendanceManagerHelper.LATE_MINUTE))){
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private LocalTime determineAttendanceStartTime(LocalDate currentDate) {
        if(currentDate.getDayOfWeek().getValue() == AttendanceManagerHelper.MONDAY){
            return AttendanceManagerHelper.MONDAY_START_TIME;
        }
        return AttendanceManagerHelper.NORMAL_START_TIME;
    }

    public void addAttendance(LocalTime currentTime, LocalDate currentDate) {
        AttendanceStatus attendanceStatus = determineAttendanceStatus(currentDate,currentTime);
        Attendance attendance = new Attendance(attendanceStatus,currentTime);
        if (attendances.containsKey(currentDate)) {
            throw new AttendanceArgumentException(DUPLICATE_ATTENDANCE_DATE);
        }
        attendances.put(currentDate, attendance);
    }

    public LocalTime getAttendanceTime(LocalDate date) {
        return attendances.get(date)
                .time();
    }

    public AttendanceStatus getAttendanceStatus(LocalDate date) {
        return attendances.get(date)
                .attendanceStatus();
    }

    public void modifyAttendance(LocalDate modifyDate, LocalTime afterModifyTime) {
        validateIsExistAttendanceHistory(modifyDate);
        Attendance prevAttendance = attendances.remove(modifyDate);
        try{
            addAttendance(afterModifyTime,modifyDate);
        }catch (AttendanceArgumentException e){
            attendances.put(modifyDate,prevAttendance);
            throw e;
        }
    }

    private void validateIsExistAttendanceHistory(LocalDate modifyDate) {
        if(attendances.get(modifyDate) == null){
            throw new AttendanceArgumentException(NOT_EXIST_ATTENDANCE);
        }
    }
}
