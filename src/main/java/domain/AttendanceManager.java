package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AttendanceManager {

    private final Crews crews;

    public AttendanceManager() {
        this.crews = new Crews();
    }

    public void createCrew(String name, List<LocalDateTime> localDateTime) {
        crews.add(name, localDateTime);
    }

    public TimeAndStatus attendCrew(String name, LocalDateTime localDateTime) {
        if(Holiday.isHoliday(localDateTime.toLocalDate())){
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
        if (crews.hasAlreadyAttended(name, localDateTime)) {
            throw new IllegalArgumentException("이미 출석한 경우 수정 기능을 사용하세요.");
        }
        return crews.attend(name, localDateTime);
    }

    public TimeAndStatus editCrew(String name, LocalDateTime newLocalDateTime) {
        if(Holiday.isHoliday(newLocalDateTime.toLocalDate())){
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
        if (!crews.hasAlreadyAttended(name, newLocalDateTime)) {
            throw new IllegalArgumentException("수정 기능은 출석 후 이용 가능합니다.");
        }
        return crews.edit(name, newLocalDateTime);
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
        return crews.findWarningCrews(nowDate);
    }

    public Records findByName(String name) {
        return crews.findByName(name);
    }

    public void hasCrew(String name){
        crews.hasCrew(name);
    }
}
