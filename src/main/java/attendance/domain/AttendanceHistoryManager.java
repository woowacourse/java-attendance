package attendance.domain;

import static attendance.domain.AttendanceType.*;

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
        if (!attendanceHistories.add(attendanceHistory)) {
            throw new IllegalArgumentException("해당 날짜에 이미 출석하셨습니다.");
        }
    }

    public Set<AttendanceHistory> getAttendanceHistories() {
        return Collections.unmodifiableSet(attendanceHistories);
    }

    public AttendanceHistory getAttendanceHistory(LocalDate localDate) {
        return attendanceHistories.stream()
                .filter(history -> history.getAttendanceTime().toLocalDate().equals(localDate))
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
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        for (AttendanceType attendanceType : AttendanceType.values()) {
            attendanceResult.put(attendanceType, 0);
        }
        for (int i = 1; i <= localDate.getDayOfMonth(); i++) { // today 1~20
            LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), i); //20250201~ 20250220
            try {
                AttendancePolicy.checkHoliday(date);
            } catch (IllegalArgumentException e) {
                continue;
            }
            for (AttendanceHistory attendanceHistory : attendanceHistories) {
                if (attendanceHistory.getAttendanceTime().toLocalDate().equals(date)) {
                    AttendanceType attendanceType = attendanceHistory.getAttendanceType();
                    attendanceResult.put(attendanceType, attendanceResult.get(attendanceType) + 1);
                    continue;
                }
                attendanceResult.put(ABSENCE, attendanceResult.get(ABSENCE) + 1);
            }
        }
        return attendanceResult;
    }
}
