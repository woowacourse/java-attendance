package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.LATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AttendanceHistoryManager {
    private static final int LATE_THRESHOLD_AS_ABSENCE = 3;

    private final Set<AttendanceHistory> attendanceHistories = new HashSet<>();

    public void addAttendanceHistory(AttendanceHistory attendanceHistory) {
        boolean isAttendanceExists = attendanceHistories.add(attendanceHistory);
        if (!isAttendanceExists) {
            throw new IllegalArgumentException("해당 날짜에 이미 출석하셨습니다.");
        }
    }

    public Set<AttendanceHistory> getAttendanceHistories() {
        return Collections.unmodifiableSet(attendanceHistories);
    }

    public AttendanceHistory getAttendanceHistory(LocalDate attendanceDate) {
        return attendanceHistories.stream()
                .filter(history -> history.isAttendanceDateEquals(attendanceDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

    public AttendanceHistory modifyAttendanceResult(AttendanceHistory modifyAttendanceHistory, LocalTime modifyTime) {
        LocalDateTime modifyDateTime = modifyAttendanceHistory.getAttendanceTime();
        AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(modifyDateTime.toLocalDate(), modifyTime);
        modifyAttendanceHistory.modify(modifyTime, attendanceType);
        return modifyAttendanceHistory;
    }

    public Map<AttendanceType, Integer> calculateAttendanceResult(LocalDate today) {
        Map<AttendanceType, Integer> attendanceResult = AttendanceType.initializeAttendanceResult();
        for (int date = 1; date < today.getDayOfMonth(); date++) {
            LocalDate attendanceDate = LocalDate.of(today.getYear(), today.getMonthValue(), date);
            try {
                AttendancePolicy.checkHoliday(attendanceDate);
            } catch (IllegalArgumentException e) {
                continue;
            }
            calculateAttendanceResultByDate(attendanceResult, attendanceDate);
        }
        return attendanceResult;
    }

    public CrewStatus calculateCrewStatus(Map<AttendanceType, Integer> attendanceResult) {
        int absenceCount = 0;
        absenceCount += attendanceResult.get(ABSENCE);
        absenceCount += attendanceResult.get(LATE) / LATE_THRESHOLD_AS_ABSENCE;
        return CrewStatus.calculateByAbsenceCount(absenceCount);
    }

    private void calculateAttendanceResultByDate(Map<AttendanceType, Integer> attendanceResult,
                                                 LocalDate attendanceDate) {
        attendanceHistories.stream()
                .filter(attendanceHistory -> attendanceHistory.isAttendanceDateEquals(attendanceDate))
                .map(AttendanceHistory::getAttendanceType)
                .forEach(attendanceType -> addAttendanceTypeCount(attendanceResult, attendanceType));
        boolean isAttendanceExists = attendanceHistories.stream()
                .anyMatch(attendanceHistory -> !attendanceHistory.isAttendanceDateEquals(attendanceDate));
        if (!isAttendanceExists) {
            addAttendanceTypeCount(attendanceResult, ABSENCE);
        }
    }

    private void addAttendanceTypeCount(Map<AttendanceType, Integer> attendanceResult,
                                        AttendanceType attendanceType) {
        attendanceResult.put(attendanceType, attendanceResult.get(attendanceType) + 1);
    }
}
