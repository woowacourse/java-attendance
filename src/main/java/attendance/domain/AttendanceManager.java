package attendance.domain;

import static attendance.domain.DateTimeFormatterWrapper.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attendance.exception.AttendanceArgumentException;
import attendance.utility.StringUtility;

public class AttendanceManager {

    private final HashMap<String, Attendances> attendanceManager = new HashMap<>();
    private final String ATTENDANCE_RESULT_FORMAT = "%s (%s)";

    public void addAttendance(String nickname, LocalDateTime time) {
        validateNickname(nickname);
        LocalTime currentTime = time.toLocalTime();
        LocalDate currentDate = time.toLocalDate();
        validateIsSchoolOpen(currentTime);
        validateAttendanceAvailable(currentDate);
        Attendances attendances = attendanceManager.getOrDefault(nickname, new Attendances());
        attendanceManager.put(nickname, attendances);
        attendances.addAttendance(currentTime, currentDate);
    }

    private void validateAttendanceAvailable(LocalDate currentDate) {
        if (currentDate.getDayOfWeek().getValue() >= AttendanceManagerHelper.WEEKEND_NUMBER) {
            throw new AttendanceArgumentException(formattingAttendanceWeekendError(currentDate));
        }

        LocalDate attendanceAvailableStartDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_START_DATE;
        LocalDate attendanceAvailableEndDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_END_DATE;
        if (attendanceAvailableEndDate.isAfter(currentDate) || attendanceAvailableStartDate.isBefore(currentDate)) {
            return;
        }
        throw new AttendanceArgumentException(AttendanceManagerHelper.ATTENDANCE_NOT_AVAILABLE);
    }

    private void validateIsSchoolOpen(LocalTime currentTime) {
        if (currentTime.isBefore(AttendanceManagerHelper.SCHOOL_OPEN_TIME) || currentTime.isAfter(
            AttendanceManagerHelper.SCHOOL_CLOSE_TIME)) {
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

    public void modifyAttendance(String nickname, LocalDate modifyDate, LocalTime afterModifyTime) {
        validateAttendanceExist(nickname);
        Attendances attendances = attendanceManager.get(nickname);
        attendances.modifyAttendance(modifyDate, afterModifyTime);
    }

    private void validateAttendanceExist(String nickname) {
        Attendances attendances = attendanceManager.get(nickname);
        if (attendances == null) {
            throw new AttendanceArgumentException(AttendanceManagerHelper.NICKNAME_NOT_EXISTS);
        }
    }

    public void validateIsAttendanceAvailable(LocalDate currentDate) {
        String ATTENDANCE_WEEKEND_ERROR = formattingAttendanceWeekendError(currentDate);
        if (currentDate.getDayOfWeek().getValue() >= AttendanceManagerHelper.WEEKEND_NUMBER) {
            throw new AttendanceArgumentException(ATTENDANCE_WEEKEND_ERROR);
        }
        if (currentDate.getMonth().getValue() == 12 && currentDate.getDayOfMonth() == 25) {
            throw new AttendanceArgumentException(ATTENDANCE_WEEKEND_ERROR);
        }
    }

    public AttendanceHistory crewAttendanceHistory(String nickname) {
        validateNickname(nickname);
        LocalDate ATTENDANCE_HISTORY_END = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_END_DATE;
        List<String> attendanceHistories = new ArrayList<>();

        Map<AttendanceStatus, Integer> attendanceStatusMap = new HashMap<>();
        for (LocalDate currentDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_START_DATE;
             currentDate.isBefore(ATTENDANCE_HISTORY_END.plusDays(1)); currentDate = currentDate.plusDays(1)) {
            try {
                validateIsAttendanceAvailable(currentDate);
            } catch (AttendanceArgumentException e) {
                continue;
            }
            Attendances attendances = attendanceManager.get(nickname);
            try {
                attendances.validateIsExistAttendanceHistory(currentDate);
            } catch (AttendanceArgumentException e) {
                String absenceHistory = DateTimeFormatterWrapper.formattingAttendanceAbsenceHistory(
                    currentDate);
                attendanceHistories.add(absenceHistory);
                attendanceStatusMap.put(AttendanceStatus.ABSENCE,
                    attendanceStatusMap.getOrDefault(AttendanceStatus.ABSENCE, 0) + 1);
                continue;
            }

            var attendanceTime = attendances.getAttendanceTime(currentDate);
            var attendanceStatus = attendances.getAttendanceStatus(currentDate);

            attendanceStatusMap.put(attendanceStatus, attendanceStatusMap.getOrDefault(attendanceStatus, 0) + 1);

            var dateTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceResult(
                LocalDateTime.of(currentDate, attendanceTime));
            attendanceHistories.add(
                String.format(ATTENDANCE_RESULT_FORMAT, dateTimeFormatResult, attendanceStatus.getStatus()));
        }
        return new AttendanceHistory(attendanceHistories, attendanceStatusMap);

    }
}
