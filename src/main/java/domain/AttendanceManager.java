package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AttendanceManager {
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8,0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23,0);

    private final Crews crews;

    public AttendanceManager() {
        this.crews = new Crews();
    }

    public void createCrew(String name, List<LocalDateTime> localDateTime) {
        crews.add(name, localDateTime);
    }

    public TimeAndStatus attendCrew(String name, LocalDateTime localDateTime) {
        validateDayOfMonth(localDateTime);
        validateTime(localDateTime.toLocalTime());

        if (crews.hasAlreadyAttended(name, localDateTime)) {
            throw new IllegalArgumentException("이미 출석한 경우 수정 기능을 사용하세요.");
        }
        return crews.attend(name, localDateTime);
    }

    public TimeAndStatus editCrew(String name, LocalDateTime newLocalDateTime) {
        validateDayOfMonth(newLocalDateTime);
        validateTime(newLocalDateTime.toLocalTime());

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

    private void validateDayOfMonth(LocalDateTime newLocalDateTime) {
        if(Holiday.isHoliday(newLocalDateTime.toLocalDate())){
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
    }

    private void validateTime(LocalTime time){
        if(time.isBefore(CAMPUS_OPEN_TIME) || time.isAfter(CAMPUS_CLOSE_TIME)){
            throw new IllegalArgumentException("캠퍼스 운영시간이 아닙니다.");
        }
    }
}
