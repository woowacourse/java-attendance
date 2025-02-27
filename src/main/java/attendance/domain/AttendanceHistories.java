package attendance.domain;

import static attendance.error.ErrorMessage.ALREADY_EXIST_ATTENDANCE;
import static attendance.error.ErrorMessage.NOT_EXIST_ATTENDANCE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttendanceHistories {

    private final List<AttendanceHistory> attendanceHistories;

    private AttendanceHistories() {
        this.attendanceHistories = new ArrayList<>();
    }

    public static AttendanceHistories create() {
        return new AttendanceHistories();
    }

    public void calculateHistories(LocalDate currentDate) {
        int day = currentDate.getDayOfMonth();
        for (int i = 1; i < day; i++) {
            LocalDate calculateDay = currentDate.withDayOfMonth(i);
            validateWeekdayAndCalculateAttendance(calculateDay);
        }
    }

    public void addAttendanceHistory(AttendanceHistory attendanceHistory) {
        validateDuplicateHistory(attendanceHistory);
        attendanceHistories.add(attendanceHistory);
    }

    public List<AttendanceHistory> getAttendanceHistories() {
        Collections.sort(attendanceHistories, Comparator.comparing(
            history -> history.getAttendanceTime().getTime().toLocalDate()
        ));
        return Collections.unmodifiableList(attendanceHistories);
    }

    public AttendanceHistory getAttendanceHistoryByDate(LocalDate findDate) {
        return validateNotExistAttendance(findDate);
    }

    public AttendanceHistory modifyAttendanceResult(LocalDateTime modifyDateTime) {
        AttendanceHistory attendanceHistory = getAllAttendanceHistoryByDate(
            modifyDateTime.toLocalDate()).orElseThrow(
            () -> new IllegalArgumentException(NOT_EXIST_ATTENDANCE.getMessage()));
        AttendanceHistory modifyAttendanceHistory = AttendanceHistory.from(modifyDateTime);
        attendanceHistories.remove(attendanceHistory);
        attendanceHistories.add(modifyAttendanceHistory);
        return modifyAttendanceHistory;
    }

    public Map<AttendanceType, Long> calculateAttendanceResult() {
        Map<AttendanceType, Integer> attendanceResult = new LinkedHashMap<>();
        initAttendanceResult(attendanceResult);
        return attendanceHistories.stream()
            .collect(
                Collectors.groupingBy(AttendanceHistory::getAttendanceType, Collectors.counting()));
    }

    private void validateWeekdayAndCalculateAttendance(LocalDate calculateDay) {
        if (Holiday.isHoliday(calculateDay)) {
            return;
        }
        if (!DayOfWeek.isWeekday(calculateDay)) {
            return;
        }
        Optional<AttendanceHistory> hasDate = getAllAttendanceHistoryByDate(calculateDay);
        if (hasDate.isEmpty()) {
            addAbsenceAttendance(calculateDay);
        }
    }

    private void addAbsenceAttendance(LocalDate calculateDay) {
        LocalDateTime notAttendanceTime = LocalDateTime.of(calculateDay, LocalTime.of(0, 0));
        AttendanceHistory attendanceHistory = AttendanceHistory.from(notAttendanceTime);
        addAttendanceHistory(attendanceHistory);
    }

    private Optional<AttendanceHistory> getAllAttendanceHistoryByDate(LocalDate localDate) {
        return attendanceHistories.stream()
            .filter(history -> history.findAttendanceTimeByDate(localDate))
            .findAny();
    }

    private void validateDuplicateHistory(AttendanceHistory attendanceHistory) {
        attendanceHistories.stream()
            .filter(result -> result.findAttendanceTimeByDate(attendanceHistory.getAttendanceDate()))
            .findAny()
            .ifPresent(result -> {
                throw new IllegalArgumentException(ALREADY_EXIST_ATTENDANCE.getMessage());
            });
    }

    private AttendanceHistory validateNotExistAttendance(LocalDate findDate) {
        return getAllAttendanceHistoryByDate(findDate).orElseThrow(
            () -> new IllegalArgumentException(NOT_EXIST_ATTENDANCE.getMessage()));
    }

    private void initAttendanceResult(Map<AttendanceType, Integer> attendanceResult) {
        for (AttendanceType attendanceType : AttendanceType.values()) {
            attendanceResult.put(attendanceType, 0);
        }
    }
}
