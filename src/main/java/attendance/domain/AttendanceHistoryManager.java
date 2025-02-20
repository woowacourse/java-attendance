package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceHistoryManager {
    private final Set<AttendanceHistory> attendanceHistories = new HashSet<>();

    public void addAttendanceResult(AttendanceHistory attendanceHistory) {
        if (!attendanceHistories.add(attendanceHistory)) {
            throw new IllegalArgumentException("해당 날짜에 이미 출석하셨습니다.");
        }
    }

    public Set<AttendanceHistory> getAttendanceHistories() {
        return Collections.unmodifiableSet(attendanceHistories);
    }

    public AttendanceHistory getAttendanceHistory2(AttendanceHistory modifyAttendanceHistory) {
        return attendanceHistories.stream()
                .filter(result -> result.equals(modifyAttendanceHistory))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
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
}
