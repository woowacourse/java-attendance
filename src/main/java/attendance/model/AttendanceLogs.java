package attendance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        List<AttendanceLog> realLogs = logs.stream()
                .filter(sameCrewAndMonth(nickname, baseDate))
                .filter(untilPreviousDay(baseDate))
                .toList();

        Set<LocalDate> existingDates = realLogs.stream()
                .map(AttendanceLog::getAttendanceDate)
                .collect(Collectors.toSet());

        List<AttendanceLog> completeLogs = new ArrayList<>(realLogs);
        for (LocalDate date = baseDate.withDayOfMonth(1); date.isBefore(baseDate); date = date.plusDays(1)) {
            if (!existingDates.contains(date)) {
                try {
                    completeLogs.add(new AttendanceLog(nickname, date));
                } catch (IllegalArgumentException ignore) {
                }
            }
        }

        return completeLogs.stream()
                .sorted(Comparator.comparing(AttendanceLog::getAttendanceDate))
                .toList();
    }

    private Predicate<AttendanceLog> sameCrewAndMonth(Nickname nickname, LocalDate baseDate) {
        return attendanceLog -> attendanceLog.isSameNicknameAndMonth(nickname, baseDate);
    }

    private Predicate<AttendanceLog> untilPreviousDay(LocalDate baseDate) {
        return attendanceLog -> attendanceLog.isBefore(baseDate);
    }

    public void edit(AttendanceLog updateAttendanceLog) {
        logs.remove(updateAttendanceLog);
        logs.add(updateAttendanceLog);
    }

    public EnumMap<AttendanceType, Integer> countAttendanceTypes(Nickname nickname, LocalDate baseDate) {
        EnumMap<AttendanceType, Integer> attendanceCounts = new EnumMap<>(AttendanceType.class);

        Arrays.stream(AttendanceType.values())
                .forEach(type -> attendanceCounts.put(type, 0));

        findAllByNicknameInMonth(nickname, baseDate).forEach(log -> {
            AttendanceType type = AttendanceType.determine(EducationSchedule.findStartTimeByDay(log.getAttendanceDate().getDayOfWeek()), log.getAttendanceTime());
            attendanceCounts.put(type, attendanceCounts.get(type) + 1);
        });

        return attendanceCounts;
    }

    public Set<Nickname> getAllNicknames() {
        return logs.stream()
                .map(AttendanceLog::getNickname)
                .collect(Collectors.toUnmodifiableSet());
    }
}
