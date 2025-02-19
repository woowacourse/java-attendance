package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import attendance.dto.AttendanceDateDto;
import attendance.exception.AttendanceException;
import attendance.utility.StringUtility;
import java.util.Locale;

public class AttendanceManager {

    private HashMap<String, Attendances> attendanceManager = new HashMap<>();

    public void addAttendance(String nickname, LocalDateTime time) {
        validateNickname(nickname);
        LocalTime currentTime = time.toLocalTime();
        validateIsSchoolOpen(currentTime);
        AttendanceStatus attendanceStatus = determineAttendanceStatus(time.toLocalDate(),currentTime);
        Attendances attendances = attendanceManager.getOrDefault(nickname, new Attendances());
        attendanceManager.put(nickname, attendances);
        attendances.addAttendance(time, attendanceStatus);
    }

    private void validateIsSchoolOpen(LocalTime currentTime) {
        if(currentTime.isBefore(AttendanceManagerHelper.SCHOOL_OPEN_TIME) || currentTime.isAfter(
                AttendanceManagerHelper.SCHOOL_CLOSE_TIME)){
            throw new AttendanceException(AttendanceManagerHelper.OUT_OF_SCHOOL_SCHEDULE);
        }
    }

    private void validateIsAttendanceAvailable(LocalDate currentDate) {
        if(currentDate.getDayOfWeek().getValue() >= AttendanceManagerHelper.WEEKEND_NUMBER) {
            String cannotAttendanceMessage = currentDate.format(DateTimeFormatter.ofPattern(
                    AttendanceManagerHelper.CANNOT_ATTENDANCE_WEEKEND_FORMAT, Locale.KOREA));
            throw new AttendanceException(cannotAttendanceMessage);
        }
    }

    private Attendances findAttendances(String nickname) {
        Attendances attendances = attendanceManager.get(nickname);
        if (attendances == null) {
            throw new AttendanceException(AttendanceManagerHelper.NICKNAME_NOT_EXISTS);
        }
        return attendances;
    }

    private void validateNickname(String nickname) {
        if (StringUtility.isEmpty(nickname)) {
            throw new AttendanceException(AttendanceManagerHelper.CANNOT_BE_EMPTY_NICKNAME);
        }
    }

    public AttendanceDateDto getAttendanceResult(String nickname, LocalDate attendanceDate) {
        var attendances = findAttendances(nickname);
        return attendances.getAttendanceTime(attendanceDate);
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
}
