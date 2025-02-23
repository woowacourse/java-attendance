package attendance.domain;

import static attendance.error.ErrorMessage.ALREADY_EXIST_ATTENDANCE;
import static attendance.error.ErrorMessage.NOT_EXIST_ATTENDANCE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AttendanceHistories {

    private final List<AttendanceHistory> attendanceHistories;

    private AttendanceHistories() {
        this.attendanceHistories = new ArrayList<>();
    }

    public static AttendanceHistories create() {
        return new AttendanceHistories();
    }

    public void calculateHistories(LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        for (int i = 1; i < day; i++) {
            LocalDate findDate = LocalDate.of(year, month, i);
            if (Holiday.isHoliday(findDate)) {
                continue;
            }

            if (!DayOfWeek.isWeekday(findDate)) {
                continue;
            }
            Optional<AttendanceHistory> hasDate = getAttendanceHistoryByDate(findDate);
            calculateAttendance(hasDate, year, month, i);
        }
    }

    private void calculateAttendance(Optional<AttendanceHistory> hasDate, int year, int month,
        int i) {
        if (hasDate.isEmpty()) {
            LocalDateTime notAttendanceTime = LocalDateTime.of(year, month, i, 0, 0);
            AttendanceHistory attendanceHistory = AttendanceHistory.from(notAttendanceTime);
            addAttendanceHistory(attendanceHistory);
        }
    }

    public void addAttendanceHistory(AttendanceHistory attendanceHistory) {
        validateDuplicateHistory(attendanceHistory);
        attendanceHistories.add(attendanceHistory);
    }

    public List<AttendanceHistory> getAttendanceHistories() {

        Collections.sort(attendanceHistories, Comparator.comparing(
            history -> history.getAttendanceTime().getAttendanceTime().toLocalDate()
        ));
        return Collections.unmodifiableList(attendanceHistories);
    }

    public AttendanceHistory getValidationAttendanceDate(LocalDate localDate) {
        Optional<AttendanceHistory> attendanceHistoryByDate = getAttendanceHistoryByDate(localDate);
        if (attendanceHistoryByDate.isEmpty()) {
            throw new IllegalArgumentException(NOT_EXIST_ATTENDANCE.getMessage());
        }
        return attendanceHistoryByDate.get();
    }

    private Optional<AttendanceHistory> getAttendanceHistoryByDate(LocalDate localDate) {
        return attendanceHistories.stream()
            .filter(history -> history.findAttendanceTimeByDate(localDate))
            .findAny();
    }

    /***
     *  .줄이는 리팩토링 필요
     */
    private void validateDuplicateHistory(AttendanceHistory attendanceHistory) {
        boolean isSame = attendanceHistories.stream()
            .anyMatch(result -> result.getAttendanceTime().
                getAttendanceTime().toLocalDate()
                .isEqual(attendanceHistory.getAttendanceTime().getAttendanceTime().toLocalDate()));
        if (isSame) {
            throw new IllegalArgumentException(ALREADY_EXIST_ATTENDANCE.getMessage());
        }
    }

    public AttendanceHistory modifyAttendanceResult(LocalDateTime modifyDateTime) {
        Optional<AttendanceHistory> attendanceHistory = getAttendanceHistoryByDate(
            modifyDateTime.toLocalDate());
        if (attendanceHistories.isEmpty()) {
            throw new IllegalArgumentException(NOT_EXIST_ATTENDANCE.getMessage());
        }
        AttendanceHistory modifyAttendanceHistory = AttendanceHistory.from(modifyDateTime);
        attendanceHistories.remove(attendanceHistory);
        attendanceHistories.add(modifyAttendanceHistory);
        return modifyAttendanceHistory;
    }

    /*
    public Map<AttendanceType, Integer> calculateAttendanceResult(LocalDate localDate) {
        Map<AttendanceType, Integer> attendanceResult = initializeAttendanceResult();
        for (int i = 1; i < localDate.getDayOfMonth(); i++) {
            LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), i);
            try {
                AttendancePolicy.ifHolidayOrWeekendsThrowException(date);
            } catch (IllegalArgumentException e) {
                continue;
            }
            boolean flag = false;
            for (AttendanceHistory attendanceHistory : attendanceHistories) {
                if (attendanceHistory.getAttendanceTime().toLocalDate().equals(date)) {
                    AttendanceType attendanceType = attendanceHistory.getAttendanceType();
                    attendanceResult.put(attendanceType, attendanceResult.get(attendanceType) + 1);
                    flag = true;
                }
            }
            if (!flag) {
                attendanceResult.put(ABSENCE, attendanceResult.get(ABSENCE) + 1);
            }
        }
        return attendanceResult;


    }

    /*
    public CrewStatus calculateCrewStatus(Map<AttendanceType, Integer> attendanceResult) {
        int validateValue = 0;
        validateValue += attendanceResult.get(ABSENCE);
        validateValue += attendanceResult.get(LATE) / 3;
        if (validateValue > 5) {
            return FIRE;
        }
        if (validateValue >= 3) {
            return INTERVIEW;
        }
        if (validateValue >= 2) {
            return WARNING;
        }
        return CLEAR;
    }

     */
    /*

    private Map<AttendanceType, Integer> initializeAttendanceResult() {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        for (AttendanceType attendanceType : AttendanceType.values()) {
            attendanceResult.put(attendanceType, 0);
        }
        return attendanceResult;
    }
     */
}
