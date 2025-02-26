package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public AttendanceLog findByNicknameAndAttendanceDate(Nickname nickname, LocalDate attendanceDate) {
        return logs.stream()
                .filter(attendanceLog -> attendanceLog.isSameNicknameAndDate(nickname, attendanceDate))
                .findFirst()
                .orElseGet(() -> new AttendanceLog(nickname, attendanceDate));
    }

    public boolean contains(AttendanceLog attendanceLog) {
        return logs.contains(attendanceLog);
    }

    public List<AttendanceLog> findAllByNicknameInMonth(Nickname nickname, LocalDate baseDate) {
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

    public AttendanceLog edit(Nickname nickname, LocalDateTime dateTime) {
        AttendanceLog attendanceLog = findByNicknameAndAttendanceDate(nickname, dateTime.toLocalDate());
        if (attendanceLog.isNotRecorded()) {
            AttendanceLog newAttendanceLog = new AttendanceLog(nickname, dateTime.toLocalDate(), dateTime.toLocalTime());
            logs.add(newAttendanceLog);
            return newAttendanceLog;
        }
        logs.remove(attendanceLog);
        AttendanceLog newAttendanceLog = new AttendanceLog(nickname, dateTime.toLocalDate(), dateTime.toLocalTime());
        logs.add(newAttendanceLog);
        return newAttendanceLog;
    }
}
