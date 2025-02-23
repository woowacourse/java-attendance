package domain;

import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.LATE;
import static domain.AttendanceHistory.ABSENT_DEFAULT_HOUR;
import static domain.AttendanceHistory.ABSENT_DEFAULT_MINUTE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttendanceHistories {
    private final List<AttendanceHistory> histories;

    public AttendanceHistories(List<LocalDateTime> originalHistories, LocalDate standard) {
        List<LocalDateTime> attendanceTimes = new ArrayList<>(originalHistories);

        int day = standard.getDayOfMonth();
        for (int i = 1; i < day; i++) {
            createAbsenceHistory(standard, i).ifPresent(absenceHistory -> {
                if (!checkHasAttendanceTime(attendanceTimes, absenceHistory.toLocalDate())) {
                    attendanceTimes.add(absenceHistory);
                }
            });
        }

        this.histories = attendanceTimes.stream()
                .map(AttendanceHistory::new)
                .collect(Collectors.toList());
    }

    public AbsenceLevel classifyAbsenceLevel(LocalDateTime standard) {
        Map<String, Integer> results = getAttendanceResultCount(standard);
        int absentCount = results.getOrDefault(ABSENCE.getResult(), 0);
        int lateCount = results.getOrDefault(LATE.getResult(), 0);
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

    public Map<String, Integer> getAttendanceResultCount(LocalDateTime standard) {
        Map<String, Integer> results = new HashMap<>();
        histories.stream().filter(history -> history.isBeforeHistory(standard))
                .forEach(history -> {
                    String result = history.getAttendanceResult();
                    results.put(result, results.getOrDefault(result, 0) + 1);
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
        return findAttendanceHistory.getAttendanceResult();
    }

    public LocalDateTime getHistory(LocalDateTime time) {
        AttendanceHistory findAttendanceHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다. 출석 기록이 있는 날짜를 입력해 주세요."));
        return findAttendanceHistory.getAttendanceTime();
    }

    private void addAbsenceHistory(LocalDate standard, int day, List<LocalDateTime> copy) {
        LocalDate time = LocalDate.of(standard.getYear(), standard.getMonthValue(), day);
        if (!Holiday.isHoliday(time) && !checkHasAttendanceTime(copy, time)) {
            copy.add(LocalDateTime.of(standard.getYear(), standard.getMonthValue(), time.getDayOfMonth(),
                    ABSENT_DEFAULT_HOUR,
                    ABSENT_DEFAULT_MINUTE));
        }
    }

    private boolean checkHasAttendanceTime(List<LocalDateTime> histories, LocalDate standard) {
        return histories.stream()
                .map(LocalDateTime::toLocalDate)
                .anyMatch(date -> date.equals(standard));
    }

    private Optional<LocalDateTime> createAbsenceHistory(LocalDate standard, int day) {
        LocalDate time = LocalDate.of(standard.getYear(), standard.getMonthValue(), day);
        if (Holiday.isHoliday(time)) {
            return Optional.empty();
        }
        return Optional.of(LocalDateTime.of(standard.getYear(), standard.getMonthValue(), day,
                ABSENT_DEFAULT_HOUR, ABSENT_DEFAULT_MINUTE));
    }
}
