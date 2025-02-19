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

    private static final int MONDAY = 1;
    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime NORMAL_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime SCHOOL_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime SCHOOL_CLOSE_TIME = LocalTime.of(23, 0);
    private final int LATE_MINUTE = 5;
    private final int WEEKEND_NUMBER = 6;
    private final int ABSENCE_MINUTE = 30;
    private final String NICKNAME_NOT_EXISTS = "출석 정보가 존재하지 않습니다.";
    private final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";
    private final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";
    private final String OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";

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
        if(currentTime.isBefore(SCHOOL_OPEN_TIME) || currentTime.isAfter(SCHOOL_CLOSE_TIME)){
            throw new AttendanceException(OUT_OF_SCHOOL_SCHEDULE);
        }
    }

    private void validateIsAttendanceAvailable(LocalDate currentDate) {
        if(currentDate.getDayOfWeek().getValue() >= WEEKEND_NUMBER) {
            String cannotAttendanceMessage = currentDate.format(DateTimeFormatter.ofPattern(CANNOT_ATTENDANCE_WEEKEND_FORMAT, Locale.KOREA));
            throw new AttendanceException(cannotAttendanceMessage);
        }
    }

    private Attendances findAttendances(String nickname) {
        Attendances attendances = attendanceManager.get(nickname);
        if (attendances == null) {
            throw new AttendanceException(NICKNAME_NOT_EXISTS);
        }
        return attendances;
    }

    private void validateNickname(String nickname) {
        if (StringUtility.isEmpty(nickname)) {
            throw new AttendanceException(CANNOT_BE_EMPTY_NICKNAME);
        }
    }

    public AttendanceDateDto getAttendanceResult(String nickname, LocalDate attendanceDate) {
        var attendances = findAttendances(nickname);
        return attendances.getAttendanceTime(attendanceDate);
    }

    private AttendanceStatus determineAttendanceStatus(LocalDate currentDate, LocalTime currentTime) {
        LocalTime startTime = determineAttendanceStartTime(currentDate);
        validateIsAttendanceAvailable(currentDate);
        if(currentTime.isAfter(startTime.plusMinutes(ABSENCE_MINUTE))){
            return AttendanceStatus.ABSENCE;
        }
        if(currentTime.isAfter(startTime.plusMinutes(LATE_MINUTE))){
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private LocalTime determineAttendanceStartTime(LocalDate currentDate) {
        if(currentDate.getDayOfWeek().getValue() == MONDAY){
            return MONDAY_START_TIME;
        }
        return NORMAL_START_TIME;
    }
}
