package domain;

import static domain.AttendanceResult.ABSENCE;
import static domain.AttendanceResult.LATE;
import static domain.History.ABSENT_DEFAULT_HOUR;
import static domain.History.ABSENT_DEFAULT_MINUTE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Histories {
    private final List<History> histories;

    public Histories(List<LocalDateTime> histories, LocalDate standard) {
        List<LocalDateTime> copy = new ArrayList<>(histories);
        int day = standard.getDayOfMonth();
        for (int i = 1; i < day; i++) {
            addAbsenceHistory(standard, i, copy);
        }
        this.histories = copy.stream().map(History::new).collect(Collectors.toList());
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
        History findHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다. 출석 기록이 있는 날짜를 입력해 주세요."));
        histories.remove(findHistory);
    }

    public void editHistory(LocalDateTime time) {
        deleteHistory(time);
        histories.add(new History(time));
    }

    public void addHistory(LocalDateTime time) {
        if (hasHistory(time)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다.");
        }
        histories.add(new History(time));
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

    public List<History> getSortedHistories(LocalDateTime standard) {
        return histories.stream()
                .filter(history -> history.isBeforeHistory(standard))
                .sorted()
                .toList();
    }

    public String getHistoryResult(LocalDateTime time) {
        History findHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다. 출석 기록이 있는 날짜를 입력해 주세요."));
        return findHistory.getAttendanceResult();
    }

    public LocalDateTime getHistory(LocalDateTime time) {
        History findHistory = histories.stream()
                .filter(history -> (history.getAttendanceTime().getDayOfMonth() == time.getDayOfMonth()) &&
                        (history.getAttendanceTime().getMonthValue() == time.getMonthValue())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜 출석 기록이 없습니다. 출석 기록이 있는 날짜를 입력해 주세요."));
        return findHistory.getAttendanceTime();
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
}
