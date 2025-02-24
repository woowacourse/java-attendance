package attendance.domain;

import attendance.exception.AttendanceArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceManager {

    static final LocalTime SCHOOL_OPEN_TIME = LocalTime.of(8, 0);
    static final LocalTime SCHOOL_CLOSE_TIME = LocalTime.of(23, 0);
    static final String OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";
    static final LocalDate ATTENDANCE_AVAILABLE_START_DATE = LocalDate.of(2024, 12, 1);
    static final LocalDate ATTENDANCE_AVAILABLE_END_DATE = LocalDate.of(2024, 12, 31);
    static final String NICKNAME_NOT_EXISTS = "출석 정보가 존재하지 않습니다.";
    static final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";
    static final String ATTENDANCE_NOT_AVAILABLE = "출석 시스템은 2024년 12월 동안만 유효합니다";
    static final int WEEKEND_NUMBER = 6;

    public AttendanceManager() {

    }

    public AttendanceManager(List<String> attendanceLines) {
        loadAttendances(attendanceLines);
    }

    private void loadAttendances(List<String> attendanceLines) {
        for (String attendanceLine : attendanceLines) {
            String[] attendanceUnits = attendanceLine.split(",");
            String nickname = attendanceUnits[0];
            LocalDateTime datetime = DateTimeFormatterWrapper.parsingAttendanceDateTime(attendanceUnits[1]);
            addAttendance(nickname, datetime);
        }
    }

    private HashMap<String, Attendances> attendanceManager = new HashMap<>();
    private String ATTENDANCE_RESULT_FORMAT = "%s (%s)";

    public void addAttendance(String nickname, LocalDateTime time) {
        validateNickname(nickname);
        LocalTime currentTime = time.toLocalTime();
        LocalDate currentDate = time.toLocalDate();
        validateIsSchoolOpen(currentTime);
        validateIsAttendanceAvailable(currentDate);
        Attendances attendances = attendanceManager.getOrDefault(nickname, new Attendances());
        attendanceManager.put(nickname, attendances);
        attendances.addAttendance(currentTime, currentDate);
    }

    public Attendance getAttendance(String nickname, LocalDate date) {
        Attendances attendances = attendanceManager.getOrDefault(nickname, new Attendances());
        return attendances.getAttendance(date);
    }

    public void validateIsSchoolOpen(LocalTime currentTime) {
        if (currentTime.isBefore(SCHOOL_OPEN_TIME) || currentTime.isAfter(SCHOOL_CLOSE_TIME)) {
            throw new AttendanceArgumentException(OUT_OF_SCHOOL_SCHEDULE);
        }
    }

    public void validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank() || nickname.isEmpty()) {
            throw new AttendanceArgumentException(CANNOT_BE_EMPTY_NICKNAME);
        }
    }

    public void modifyAttendance(String nickname, LocalDate modifyDate, LocalTime afterModifyTime) {
        validateAttendanceExist(nickname);
        Attendances attendances = attendanceManager.get(nickname);
        attendances.modifyAttendance(modifyDate, afterModifyTime);
    }

    public void validateAttendanceExist(String nickname) {
        Attendances attendances = attendanceManager.get(nickname);
        if (attendances == null) {
            throw new AttendanceArgumentException(NICKNAME_NOT_EXISTS);
        }
    }

    public void validateIsAttendanceAvailable(LocalDate currentDate) {
        validateIsAttendanceWeekend(currentDate);
        if (ATTENDANCE_AVAILABLE_START_DATE.isAfter(currentDate) || ATTENDANCE_AVAILABLE_END_DATE.isBefore(
                currentDate)) {
            throw new AttendanceArgumentException(ATTENDANCE_NOT_AVAILABLE);
        }
    }

    private void validateIsAttendanceWeekend(LocalDate currentDate) {
        String ATTENDANCE_WEEKEND_ERROR = DateTimeFormatterWrapper.formattingAttendanceDateError(currentDate);
        if (currentDate.getMonth().getValue() == 12 && currentDate.getDayOfMonth() == 25) {
            throw new AttendanceArgumentException(ATTENDANCE_WEEKEND_ERROR);
        }
        if (currentDate.getDayOfWeek().getValue() >= WEEKEND_NUMBER) {
            throw new AttendanceArgumentException(ATTENDANCE_WEEKEND_ERROR);
        }
    }

    public AttendanceHistory crewAttendanceHistory(String nickname) {
        validateCrewNameExist(nickname);
        LocalDate startDate = ATTENDANCE_AVAILABLE_START_DATE;
        LocalDate endDate = ATTENDANCE_AVAILABLE_END_DATE;
        List<String> attendanceHistories = new ArrayList<>();
        Map<String, Integer> attendanceStatusMap = new HashMap<>();
        Attendances attendances = attendanceManager.get(nickname);
        for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
            appendAttendanceHistories(attendances, currentDate, attendanceHistories, attendanceStatusMap);
        }
        return new AttendanceHistory(nickname, attendanceHistories, attendanceStatusMap);
    }

    private void validateCrewNameExist(String nickname) {
        validateNickname(nickname);
        validateAttendanceExist(nickname);
    }

    private boolean isAttendanceAvailable(LocalDate currentDate) {
        try {
            validateIsAttendanceAvailable(currentDate);
            return true;
        } catch (AttendanceArgumentException e) {
            return false;
        }
    }

    private void appendAttendanceHistories(Attendances attendances, LocalDate currentDate,
                                           List<String> attendanceHistories,
                                           Map<String, Integer> attendanceStatusMap) {
        if (!isAttendanceAvailable(currentDate)) {
            return;
        }
        if (!isAttendanceExistInDate(attendances, currentDate)) {
            addAbsenceHistory(attendanceHistories, attendanceStatusMap, currentDate);
            return;
        }
        addAttendanceHistory(attendances, currentDate, attendanceHistories, attendanceStatusMap);
    }

    private boolean isAttendanceExistInDate(Attendances attendances, LocalDate currentDate) {
        try {
            attendances.validateIsExistAttendanceHistory(currentDate);
            return true;
        } catch (AttendanceArgumentException e) {
            return false;
        }
    }

    private void addAbsenceHistory(List<String> attendanceHistories,
                                   Map<String, Integer> attendanceStatusMap, LocalDate currentDate) {
        String absenceHistory = DateTimeFormatterWrapper.formattingAttendanceAbsenceHistory(currentDate);
        attendanceHistories.add(absenceHistory);
        attendanceStatusMap.merge(AttendanceStatus.ABSENCE.getStatus(), 1, Integer::sum);
    }

    private void addAttendanceHistory(Attendances attendances, LocalDate currentDate,
                                      List<String> attendanceHistories,
                                      Map<String, Integer> attendanceStatusMap) {
        LocalTime attendanceTime = attendances.getAttendanceTime(currentDate);
        String attendanceStatus = attendances.getAttendanceStatus(currentDate);

        attendanceStatusMap.merge(attendanceStatus, 1, Integer::sum);

        String dateTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceResult(
                LocalDateTime.of(currentDate, attendanceTime));
        attendanceHistories.add(
                String.format(ATTENDANCE_RESULT_FORMAT, dateTimeFormatResult, attendanceStatus));
    }

    public List<String> attendancesNicknames() {
        return attendanceManager.keySet().stream().toList();
    }

    public List<AttendanceHistory> crewDismissHistory() {
        return attendancesNicknames()
                .stream()
                .map(this::crewAttendanceHistory)
                .collect(Collectors.toList());
    }
}


