package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CrewHistory {

    public static final LocalTime DEFAULT_TIME = LocalTime.MAX;
    private static final int DATE_INCREASE_UNIT = 1;

    private final Map<LocalDate, LocalDateTime> history;

    public CrewHistory(final Map<LocalDate, LocalDateTime> history) {
        this.history = new HashMap<>(history);
    }

    public void add(final LocalDateTime attendanceDateTime) {
        LocalDate attendanceDate = LocalDate.from(attendanceDateTime);
        validateNotExists(attendanceDate);
        history.put(attendanceDate, attendanceDateTime);
    }

    public void validateNotExists(final LocalDate attendanceDate) {
        if (history.containsKey(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용해주세요.");
        }
    }

    public void validateExists(final LocalDate attendanceDate) {
        if (!history.containsKey(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재하지 않습니다.");
        }
    }

    public LocalDateTime modify(final LocalDateTime modifyingDateTime) {
        LocalDate modifyDate = LocalDate.from(modifyingDateTime);
        validateExists(modifyDate);
        LocalDateTime previousDateTime = history.get(modifyDate);
        history.put(modifyDate, modifyingDateTime);
        return previousDateTime;
    }

    public Optional<LocalDateTime> find(final LocalDate date) {
        return Optional.ofNullable(history.get(date));
    }

    public Map<LocalDateTime, AttendanceState> calculateTotalHistory(final LocalDate nowDate,
                                                                     final CampusScheduler campusScheduler) {
        Map<LocalDateTime, AttendanceState> result = new LinkedHashMap<>();
        LocalDate date = nowDate.withDayOfMonth(DATE_INCREASE_UNIT);
        while (date.isBefore(nowDate)) {
            addEachHistory(campusScheduler, date, result);
            date = date.plusDays(DATE_INCREASE_UNIT);
        }
        return result;
    }

    private void addEachHistory(final CampusScheduler campusScheduler, LocalDate date,
                                final Map<LocalDateTime, AttendanceState> result) {
        if (campusScheduler.isNotOperationDate(date)) {
            return;
        }
        LocalDateTime time = findDateHistory(date);
        AttendanceState attendanceState = campusScheduler.calculateAttendanceState(time);
        result.put(time, attendanceState);
    }

    private LocalDateTime findDateHistory(final LocalDate date) {
        Optional<LocalDateTime> history = find(date);
        return history.orElseGet(() -> LocalDateTime.of(date, DEFAULT_TIME));
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final CrewHistory that)) {
            return false;
        }
        return Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(history);
    }
}
