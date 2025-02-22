package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AttendanceManager {

    private final Map<String, Records> crews;

    public AttendanceManager() {
        this.crews = new HashMap<>();
    }

    public void createCrew(String name, List<LocalDateTime> localDateTimes) {
        crews.put(name, new Records(localDateTimes));
    }

    public TimeAndStatus attendCrew(String name, LocalDateTime localDateTime) {
        Records records = findByName(name);

        if (records.isSameDate(localDateTime)) {
            throw new IllegalArgumentException("이미 출석한 경우 수정 기능을 사용하세요.");
        }

        return records.attend(localDateTime);
    }

    public TimeAndStatus editCrew(String name, LocalDateTime newLocalDateTime) {
        Records records = findByName(name);

        if (!records.isSameDate(newLocalDateTime)) {
            throw new IllegalArgumentException("수정 기능은 출석 후 이용 가능합니다.");
        }

        return records.edit(newLocalDateTime);
    }

    public Map<String, StatisticsResult> sortCrew(LocalDate nowDate) {
        Map<String, StatisticsResult> sortedResult = findWarningCrews(nowDate);

        return sortedResult
            .entrySet().stream().sorted(Comparator.comparing(
                    (Map.Entry<String, StatisticsResult> entry) -> entry.getValue().getPenalty(),
                    Comparator.naturalOrder())
                .thenComparing(entry -> entry.getValue().getAbsenceCount(),
                    Comparator.reverseOrder()).thenComparing(Map.Entry::getKey)
            )
            .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                Map::putAll);
    }

    public Map<String, StatisticsResult> findWarningCrews(LocalDate nowDate) {
        return AttendanceStatistics.calculateExpelledWarning(nowDate, crews);
    }

    public Records findByName(String name) {
        Records records = crews.get(name);
        if (records == null) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
        return records;
    }
}
