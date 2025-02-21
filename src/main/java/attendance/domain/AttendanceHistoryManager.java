package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static attendance.domain.CrewStatus.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AttendanceHistoryManager {
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

    public AttendanceHistory getAttendanceHistory(LocalDate localDate) {
        return attendanceHistories.stream()
                .filter(history -> history.isAttendanceDateEquals(localDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

    public AttendanceHistory modifyAttendanceResult(AttendanceHistory modifyAttendanceHistory, LocalTime localTime) {
        AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(
                modifyAttendanceHistory.getAttendanceTime().toLocalDate(), localTime);
        modifyAttendanceHistory.modify(localTime, attendanceType);
        return modifyAttendanceHistory;
    }

    public Map<AttendanceType, Integer> calculateAttendanceResult(LocalDate localDate) {
        Map<AttendanceType, Integer> attendanceResult = initializeAttendanceResult();
        for (int i = 1; i < localDate.getDayOfMonth(); i++) {
            LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), i);
            try {
                AttendancePolicy.checkHoliday(date);
            } catch (IllegalArgumentException e) {
                continue;
            }
            boolean flag = false;
            for (AttendanceHistory attendanceHistory : attendanceHistories) {
                if (attendanceHistory.isAttendanceDateEquals(date)) {
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

    private Map<AttendanceType, Integer> initializeAttendanceResult() {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        for (AttendanceType attendanceType : AttendanceType.values()) {
            attendanceResult.put(attendanceType, 0);
        }
        return attendanceResult;
    }
}
