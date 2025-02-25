package domain;

import static domain.AttendanceStatus.ABSENCE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import util.parser.DateTimeParser;

public class Crews {

    private static final String DUPLICATE_ATTEND_ERROR_MESSAGE = "이미 출석한 경우 수정 기능을 사용하세요.";
    private static final String NOT_ATTEND_ERROR_MESSAGE = "수정 기능은 출석 후 이용 가능합니다.";
    private static final String NOT_CREW_ERROR_MESSAGE = "등록되지 않는 크루입니다.";
    private static final String NOT_OPERATING_TIME_ERROR_MESSAGE = "캠퍼스 운영 시간이 아닙니다.";
    private static final int CAMPUS_START_HOUR = 8;
    private static final int CAMPUS_START_MINUTE = 0;
    private static final int CAMPUS_END_HOUR = 23;
    private static final int CAMPUS_END_MINUTE = 0;

    private final Map<String, Crew> crews;

    public Crews() {
        this.crews = new HashMap<>();
    }

    public void createCrew(String name, List<LocalDateTime> localDateTimes) {
        crews.put(name, new Crew(localDateTimes));
    }

    public DailyRecord attendCrew(String name, LocalDateTime localDateTime) {
        validateAttendancePossibility(name, localDateTime);

        Crew crew = findCrewByName(name);
        return crew.attend(localDateTime);
    }

    public DailyRecord editCrew(String name, LocalDateTime newLocalDateTime) {
        validateEditPossibility(name, newLocalDateTime);

        Crew crew = findCrewByName(name);
        return crew.edit(newLocalDateTime);
    }

    public Map<String, StatisticsResult> findWarningCrews(LocalDate nowDate) {
        return Penalty.calculateExpelledWarning(nowDate, crews)
            .entrySet().stream()
            .sorted(Comparator.comparing((Map.Entry<String, StatisticsResult> entry)
                    -> entry.getValue().getPenalty(), Comparator.naturalOrder())
                .thenComparing(entry
                    -> entry.getValue().getCount(ABSENCE), Comparator.reverseOrder())
                .thenComparing(Map.Entry::getKey))
            .collect(LinkedHashMap::new, (map, entry)
                -> map.put(entry.getKey(), entry.getValue()), Map::putAll);
    }

    public Crew findCrewByName(String name) {
        try {
            return crews.get(name);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(NOT_CREW_ERROR_MESSAGE);
        }
    }

    public Map<String, Crew> getCrews() {
        return crews;
    }

    private void validateAttendancePossibility(String name, LocalDateTime dateTime) {
        if (findCrewByName(name).isAlreadyAttended(dateTime.toLocalDate())) {
            throw new IllegalArgumentException(DUPLICATE_ATTEND_ERROR_MESSAGE);
        }
        if (!isOperatingTime(dateTime.toLocalTime())) {
            throw new IllegalArgumentException(NOT_OPERATING_TIME_ERROR_MESSAGE);
        }
    }

    private void validateEditPossibility(String name, LocalDateTime newDateTime) {
        if (!findCrewByName(name).isAlreadyAttended(newDateTime.toLocalDate())) {
            throw new IllegalArgumentException(NOT_ATTEND_ERROR_MESSAGE);
        }
        if (!isOperatingTime(newDateTime.toLocalTime())) {
            throw new IllegalArgumentException(NOT_OPERATING_TIME_ERROR_MESSAGE);
        }
    }

    private boolean isOperatingTime(LocalTime time) {
        LocalTime start = DateTimeParser.parseIntegerToTime(CAMPUS_START_HOUR, CAMPUS_START_MINUTE);
        LocalTime end = DateTimeParser.parseIntegerToTime(CAMPUS_END_HOUR, CAMPUS_END_MINUTE);

        return (time.isAfter(start) || time.equals(start))
            && (time.isBefore(end) || time.equals(end));
    }
}
