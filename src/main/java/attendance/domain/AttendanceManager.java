package attendance.domain;

import attendance.dto.RequestModifyAttendanceDto;
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

    private static final LocalTime SCHOOL_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime SCHOOL_CLOSE_TIME = LocalTime.of(23, 0);
    private static final String OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";
    private static final LocalDate SYSTEM_AVAILABLE_START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate SYSTEM_AVAILABLE_END_DATE = LocalDate.of(2024, 12, 31);
    private static final String NICKNAME_NOT_EXISTS = "출석 정보가 존재하지 않습니다.";
    private static final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";
    private static final String ATTENDANCE_NOT_AVAILABLE = "출석 시스템은 2024년 12월 동안만 유효합니다";
    private static final int WEEKEND_NUMBER = 6;
    private static final String ATTENDANCE_RESULT_FORMAT = "%s (%s)";

    private final HashMap<String, Attendances> attendanceManager = new HashMap<>();
    private final int XMAS_MONTH = 12;
    private final int XMAS_DAY = 25;
    private final int STATUS_ADD_COUNT = 1;

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

    public Attendance findAttendance(String nickname, LocalDate date) {
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
        if (SYSTEM_AVAILABLE_START_DATE.isAfter(currentDate) || SYSTEM_AVAILABLE_END_DATE.isBefore(
                currentDate)) {
            throw new AttendanceArgumentException(ATTENDANCE_NOT_AVAILABLE);
        }
    }

    private void validateIsAttendanceWeekend(LocalDate currentDate) {
        String CANNOT_ATTENDANCE_ON_HOLIDAY = DateTimeFormatterWrapper.formattingAttendanceDateError(currentDate);
        if (isHoliday(currentDate)) {
            throw new AttendanceArgumentException(CANNOT_ATTENDANCE_ON_HOLIDAY);
        }
    }

    public boolean isHoliday(LocalDate currentDate) {
        int month = currentDate.getMonth().getValue();
        int dayOfMonth = currentDate.getDayOfMonth();
        int dayOfWeekend = currentDate.getDayOfWeek().getValue();
        if (month == XMAS_MONTH && dayOfMonth == XMAS_DAY) {
            return true;
        }
        if (dayOfWeekend >= WEEKEND_NUMBER) {
            return true;
        }
        return false;
    }

    public AttendanceHistory crewAttendanceHistory(String nickname) {
        validateCrewNameExist(nickname);
        LocalDate startDate = SYSTEM_AVAILABLE_START_DATE;
        LocalDate endDate = SYSTEM_AVAILABLE_END_DATE;
        List<String> attendanceHistories = new ArrayList<>();
        Map<AttendanceStatus, Integer> attendanceStatusMap = new HashMap<>();
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

    private void appendAttendanceHistories(Attendances attendances, LocalDate currentDate,
                                           List<String> attendanceHistories,
                                           Map<AttendanceStatus, Integer> attendanceStatusMap) {
        if (isHoliday(currentDate)) {
            return;
        }
        if (attendances.isAttendanceExist(currentDate)) {
            addAttendanceHistory(attendances, currentDate, attendanceHistories, attendanceStatusMap);
            return;
        }
        addAbsenceHistory(attendanceHistories, attendanceStatusMap, currentDate);
    }

    private void addAbsenceHistory(List<String> attendanceHistories,
                                   Map<AttendanceStatus, Integer> attendanceStatusMap, LocalDate currentDate) {
        String absenceHistory = DateTimeFormatterWrapper.formattingAttendanceAbsenceHistory(currentDate);
        attendanceHistories.add(absenceHistory);
        attendanceStatusMap.merge(AttendanceStatus.ABSENCE, STATUS_ADD_COUNT, Integer::sum);
    }

    private void addAttendanceHistory(Attendances attendances, LocalDate currentDate,
                                      List<String> attendanceHistories,
                                      Map<AttendanceStatus, Integer> attendanceStatusMap) {
        LocalTime attendanceTime = attendances.getAttendanceTime(currentDate);
        AttendanceStatus attendanceStatus = attendances.getAttendanceStatus(currentDate);
        attendanceStatusMap.merge(attendanceStatus, STATUS_ADD_COUNT, Integer::sum);

        String dateTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceResult(
                LocalDateTime.of(currentDate, attendanceTime));
        attendanceHistories.add(
                String.format(ATTENDANCE_RESULT_FORMAT, dateTimeFormatResult, attendanceStatus.getStatus()));
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

    public void modifyAttendance(RequestModifyAttendanceDto requestModifyAttendanceDto) {
        String nickname = requestModifyAttendanceDto.nickname();
        validateAttendanceExist(nickname);
        Attendances attendances = attendanceManager.get(nickname);
        attendances.modifyAttendance(requestModifyAttendanceDto.date(), requestModifyAttendanceDto.time());
    }
}


