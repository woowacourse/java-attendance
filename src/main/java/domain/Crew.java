package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import dto.HistoryDto;

public class Crew {

    private final String nickname;
    private final Map<LocalDate, LocalTime> attendanceTimes = new HashMap<>();

    public Crew(String name) {
        this.nickname = name;
    }

    public AttendanceStatus attendance(LocalDate date, LocalTime time) {
        validateDate(date);
        attendanceTimes.put(date, time);
        return getAttendanceStatusByDate(date);
    }

    private void validateDate(LocalDate date) {
        if (attendanceTimes.containsKey(date)) {
            throw new IllegalArgumentException("이미 출석 처리되어 있습니다. 수정 기능을 이용해주세요.");
        }
        if (Holiday.isOffDay(date)) {
            throw new IllegalArgumentException("주말 및 공휴일에는 출석을 받지 않습니다.");
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void modifyAttendance(LocalDate date, LocalTime time) {
        attendanceTimes.put(date, time);
    }

    public LocalTime getAttendanceTimeByDate(LocalDate date) {
        return attendanceTimes.get(date);
    }

    public AttendanceStatus getAttendanceStatusByDate(LocalDate date) {
        if (Holiday.isOffDay(date)) {
            return null;
        }
        if (!attendanceTimes.containsKey(date)) {
            return AttendanceStatus.ABSENT;
        }
        return AttendanceStatus.of(date, attendanceTimes.get(date));
    }

    public List<HistoryDto> getAllHistory(LocalDate today) {
        return IntStream.range(1, today.getDayOfMonth())
            .filter(day -> !Holiday.isOffDay(today.withDayOfMonth(day)))
            .mapToObj(day -> {
                LocalDate date = today.withDayOfMonth(day);
                LocalTime time = attendanceTimes.get(date);
                return new HistoryDto(date, time, getAttendanceStatusByDate(date), time == null);
            }).toList();
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatusCounter(LocalDate today) {
        EnumMap<AttendanceStatus, Integer> result = new EnumMap<>(AttendanceStatus.class);
        initializeStatusCounter(result);
        Map<AttendanceStatus, Integer> counter = IntStream.range(1, today.getDayOfMonth())
            .filter(day -> !Holiday.isOffDay(today.withDayOfMonth(day)))
            .mapToObj(day -> LocalDate.of(today.getYear(), today.getMonth(), day))
            .collect(Collectors.toMap(this::getAttendanceStatusByDate, day -> 1, Integer::sum));
        counter.forEach((status, count) -> result.merge(status, count, Integer::sum));
        return result;
    }

    private void initializeStatusCounter(Map<AttendanceStatus, Integer> result) {
        Arrays.stream(AttendanceStatus.values()).forEach(status -> result.put(status, 0));
    }
}
