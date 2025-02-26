package attendance.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class AttendanceLogs {

    private final Set<AttendanceLog> logs;

    public AttendanceLogs() {
        logs = new HashSet<>();
    }

    public void add(AttendanceLog attendanceLog) {
        boolean isAdded = logs.add(attendanceLog);
        if (!isAdded) {
            throw new IllegalArgumentException("금일 출석 기록이 존재하여 추가되지 않았습니다. 수정이 필요한 경우 출석 수정 기능을 사용해주세요.");
        }
    }

    public boolean contains(AttendanceLog attendanceLog) {
        return logs.contains(attendanceLog);
    }

    public List<AttendanceLog> findByNicknameInMonth(Nickname nickname, LocalDate baseDate) {
        return logs.stream()
                .filter(sameCrewAndMonth(nickname, baseDate))
                .filter(untilPreviousDay(baseDate))
                .toList();
    }

    private Predicate<AttendanceLog> sameCrewAndMonth(Nickname nickname, LocalDate baseDate) {
        return attendanceLog -> attendanceLog.isSameNicknameAndMonth(nickname, baseDate);
    }

    private Predicate<AttendanceLog> untilPreviousDay(LocalDate baseDate) {
        return attendanceLog -> attendanceLog.isBefore(baseDate);
    }
}
