package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    public void add(AttendanceLog log) {
        validateAttendanceNotDuplicate(log);
        logs.add(log);
    }

    private void validateAttendanceNotDuplicate(AttendanceLog log) {
        if (hasAttendanceOnSameDate(log)) {
            throw new IllegalArgumentException("금일 출석 기록이 존재하여 추가되지 않았습니다. 수정이 필요한 경우 출석 수정 기능을 사용해주세요.");
        }
    }

    private boolean hasAttendanceOnSameDate(AttendanceLog log) {
        return logs.stream()
                .anyMatch(existingLog -> existingLog.isSameNicknameAndDate(log.getNickname(), log.getAttendanceDate()));
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
        List<AttendanceLog> attendanceLogs = getAttendanceLogsForMonth(nickname, baseDate);
        Set<LocalDate> recordedDates = extractAttendanceDates(attendanceLogs);
        fillAbsentDays(nickname, baseDate, recordedDates, attendanceLogs);
        return sortByDate(attendanceLogs);
    }

    private List<AttendanceLog> getAttendanceLogsForMonth(Nickname nickname, LocalDate baseDate) {
        return logs.stream()
                .filter(isSameCrewAndMonth(nickname, baseDate))
                .filter(untilPreviousDay(baseDate))
                .collect(Collectors.toList());
    }

    private Set<LocalDate> extractAttendanceDates(List<AttendanceLog> logs) {
        return logs.stream()
                .map(AttendanceLog::getAttendanceDate)
                .collect(Collectors.toSet());
    }

    private void fillAbsentDays(Nickname nickname, LocalDate baseDate,
                                Set<LocalDate> existingDates,
                                List<AttendanceLog> logs) {
        LocalDate startOfMonth = baseDate.withDayOfMonth(1);
        for (LocalDate date = startOfMonth; date.isBefore(baseDate); date = date.plusDays(1)) {
            addIfUnrecordedAttendance(nickname, existingDates, logs, date);
        }
    }

    private void addIfUnrecordedAttendance(Nickname nickname, Set<LocalDate> recordedDates,
                                           List<AttendanceLog> logs, LocalDate date) {
        if (isValidAttendanceDay(date) && !recordedDates.contains(date)) {
            logs.add(new AttendanceLog(nickname, date));
        }
    }

    private boolean isValidAttendanceDay(LocalDate date) {
        return !isWeekend(date) && !PublicHoliday.isPublicHoliday(date);
    }

    private boolean isWeekend(LocalDate baseDate) {
        DayOfWeek dayOfWeek = baseDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private List<AttendanceLog> sortByDate(List<AttendanceLog> logs) {
        return logs.stream()
                .sorted(Comparator.comparing(AttendanceLog::getAttendanceDate))
                .toList();
    }

    private Predicate<AttendanceLog> isSameCrewAndMonth(Nickname nickname, LocalDate baseDate) {
        return attendanceLog -> attendanceLog.isSameNicknameAndMonth(nickname, baseDate);
    }

    private Predicate<AttendanceLog> untilPreviousDay(LocalDate baseDate) {
        return attendanceLog -> attendanceLog.isBefore(baseDate);
    }

    public void edit(AttendanceLog updateAttendanceLog) {
        logs.removeIf(isSameNicknameAndDate(updateAttendanceLog));
        logs.add(updateAttendanceLog);
    }

    private Predicate<AttendanceLog> isSameNicknameAndDate(AttendanceLog updateAttendanceLog) {
        return log -> log.isSameNicknameAndDate(
                updateAttendanceLog.getNickname(),
                updateAttendanceLog.getAttendanceDate()
        );
    }

    public EnumMap<AttendanceType, Integer> countAttendanceTypes(Nickname nickname, LocalDate baseDate) {
        EnumMap<AttendanceType, Integer> attendanceCounts = initializeAttendanceCounts();
        for (AttendanceLog attendanceLog : findAllByNicknameInMonth(nickname, baseDate)) {
            AttendanceType type = AttendanceType.determine(
                    EducationSchedule.findStartTimeByDay(attendanceLog.getAttendanceDate().getDayOfWeek()),
                    attendanceLog.getAttendanceTime());
            attendanceCounts.merge(type, 1, Integer::sum);
        }
        return attendanceCounts;
    }

    private EnumMap<AttendanceType, Integer> initializeAttendanceCounts() {
        EnumMap<AttendanceType, Integer> attendanceCounts = new EnumMap<>(AttendanceType.class);
        for (AttendanceType value : AttendanceType.values()) {
            attendanceCounts.put(value, 0);
        }
        return attendanceCounts;
    }

    public Set<Nickname> getAllNicknames() {
        return logs.stream()
                .map(AttendanceLog::getNickname)
                .collect(Collectors.toUnmodifiableSet());
    }
}
