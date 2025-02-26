package domain;

import static domain.AttendanceHistory.ABSENT_DEFAULT_HOUR;
import static domain.AttendanceHistory.ABSENT_DEFAULT_MINUTE;
import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.LATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AttendanceHistories {
    private final List<AttendanceHistory> histories;

    public AttendanceHistories(List<LocalDateTime> originalHistories, LocalDate standard) {
        List<LocalDateTime> attendanceTimes = new ArrayList<>(originalHistories);
        List<LocalDateTime> absenceHistories = generateAbsenceHistories(attendanceTimes, standard);
        attendanceTimes.addAll(absenceHistories);
        this.histories = attendanceTimes.stream()
                .map(AttendanceHistory::new)
                .collect(Collectors.toList());
    }

    private List<LocalDateTime> generateAbsenceHistories(List<LocalDateTime> attendanceTimes, LocalDate standard) {
        return getAbsentDays(attendanceTimes, standard).stream()
                .filter(date -> !Holiday.isHoliday(date))
                .map(date -> LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                        ABSENT_DEFAULT_HOUR, ABSENT_DEFAULT_MINUTE))
                .collect(Collectors.toList());
    }

    private List<LocalDate> getAbsentDays(List<LocalDateTime> attendanceTimes, LocalDate standard) {
        int day = standard.getDayOfMonth();
        return IntStream.range(1, day)
                .mapToObj(i -> LocalDate.of(standard.getYear(), standard.getMonthValue(), i))
                .filter(date -> !hasAttendanceForDate(attendanceTimes, date))
                .collect(Collectors.toList());
    }

    private boolean hasAttendanceForDate(List<LocalDateTime> attendanceTimes, LocalDate date) {
        return attendanceTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .anyMatch(attendanceDate -> attendanceDate.equals(date));
    }

    public AbsenceLevel classifyAbsenceLevel(LocalDateTime standard) {
        Map<AttendanceResult, Integer> results = getAttendanceResultCount(standard);
        int absentCount = results.getOrDefault(ABSENCE, 0);
        int lateCount = results.getOrDefault(LATE, 0);
        return AbsenceLevel.findAbsenceLevel(absentCount, lateCount);
    }

    public boolean hasHistory(LocalDateTime time) {
        return histories.stream()
                .anyMatch(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue()));
    }

    public void deleteHistory(LocalDateTime time) {
        AttendanceHistory findAttendanceHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다. 출석 기록이 있는 날짜를 입력해 주세요."));
        histories.remove(findAttendanceHistory);
    }

    public void editHistory(LocalDateTime time) {
        deleteHistory(time);
        histories.add(new AttendanceHistory(time));
    }

    public void addHistory(LocalDateTime time) {
        if (hasHistory(time)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다.");
        }
        histories.add(new AttendanceHistory(time));
    }

    public Map<AttendanceResult, Integer> getAttendanceResultCount(LocalDateTime standard) {
        Map<AttendanceResult, Integer> results = new HashMap<>();
        histories.stream().filter(history -> history.isBeforeHistory(standard))
                .forEach(history -> {
                    AttendanceResult attendanceResult = history.getAttendanceResult();
                    results.put(attendanceResult, results.getOrDefault(attendanceResult, 0) + 1);
                });
        return results;
    }

    public List<AttendanceHistory> getSortedHistories(LocalDateTime standard) {
        return histories.stream()
                .filter(history -> history.isBeforeHistory(standard))
                .sorted()
                .toList();
    }

    public String getHistoryResult(LocalDateTime time) {
        AttendanceHistory findAttendanceHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다. 출석 기록이 있는 날짜를 입력해 주세요."));
        return findAttendanceHistory.getAttendanceResult().getResult();
    }

    public LocalDateTime getHistory(LocalDateTime time) {
        AttendanceHistory findAttendanceHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다. 출석 기록이 있는 날짜를 입력해 주세요."));
        return findAttendanceHistory.getAttendanceTime();
    }
}
