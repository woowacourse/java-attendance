package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import attendance.exception.AttendanceArgumentException;
import attendance.utility.StringUtility;
import java.util.Locale;

public class AttendanceManager {

    private HashMap<String, Attendances> attendanceManager = new HashMap<>();

    public void addAttendance(String nickname, LocalDateTime time) {
        validateNickname(nickname);
        LocalTime currentTime = time.toLocalTime();
        LocalDate currentDate = time.toLocalDate();
        validateIsSchoolOpen(currentTime);
        validateAttendanceAvailable(currentDate);
        Attendances attendances = attendanceManager.getOrDefault(nickname, new Attendances());
        attendanceManager.put(nickname, attendances);
        attendances.addAttendance(currentTime,currentDate);
    }

    private void validateAttendanceAvailable(LocalDate currentDate) {
        LocalDate attendanceAvailableStartDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_START_DATE;
        LocalDate attendanceAvailableEndDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_END_DATE;
        if (attendanceAvailableEndDate.isAfter(currentDate) || attendanceAvailableStartDate.isBefore(currentDate)) {
            return;
        }
        throw new AttendanceArgumentException(AttendanceManagerHelper.ATTENDANCE_NOT_AVAILABLE);
    }

    private void validateIsSchoolOpen(LocalTime currentTime) {
        if(currentTime.isBefore(AttendanceManagerHelper.SCHOOL_OPEN_TIME) || currentTime.isAfter(
                AttendanceManagerHelper.SCHOOL_CLOSE_TIME)){
            throw new AttendanceArgumentException(AttendanceManagerHelper.OUT_OF_SCHOOL_SCHEDULE);
        }
    }

    public Attendances findAttendances(String nickname) {
        Attendances attendances = attendanceManager.get(nickname);
        if (attendances == null) {
            throw new AttendanceArgumentException(AttendanceManagerHelper.NICKNAME_NOT_EXISTS);
        }
        return attendances;
    }

    private void validateNickname(String nickname) {
        if (StringUtility.isEmpty(nickname)) {
            throw new AttendanceArgumentException(AttendanceManagerHelper.CANNOT_BE_EMPTY_NICKNAME);
        }
    }

    public void modifyAttendance(String nickname, LocalDate modifyDate,LocalTime afterModifyTime) {
        validateAttendanceExist(nickname);
        Attendances attendances = attendanceManager.get(nickname);
        attendances.modifyAttendance(modifyDate,afterModifyTime);
    }

    private void validateAttendanceExist(String nickname) {
        Attendances attendances = attendanceManager.get(nickname);
        if(attendances == null){
            throw new AttendanceArgumentException(AttendanceManagerHelper.NICKNAME_NOT_EXISTS);
        }
    }
}
