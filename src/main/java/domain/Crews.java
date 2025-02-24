package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crews {
    private final Map<String, Records> crews;

    public Crews() {
        this.crews = new HashMap<>();
    }

    public void add(String name, List<LocalDateTime> localDateTime) {
        crews.putIfAbsent(name, new Records(localDateTime));
    }

    public boolean hasAlreadyAttended(String name, LocalDateTime localDateTime) {
        return crews.get(name).hasSameDate(localDateTime);
    }

    public TimeAndStatus attend(String name, LocalDateTime localDateTime) {
        return crews.get(name).attend(localDateTime);
    }

    public TimeAndStatus edit(String name, LocalDateTime localDateTime) {
        return crews.get(name).edit(localDateTime);
    }

    public Records findByName(String name) {
        return crews.get(name);
    }

    public Map<String, StatisticsResult> findWarningCrews(LocalDate nowDate) {
        return AttendanceStatistics.calculateExpelledWarning(nowDate, crews);
    }

    public void hasCrew(String name) {
        if (!crews.containsKey(name)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }
}
