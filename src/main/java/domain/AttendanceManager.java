package domain;

import static util.constant.ErrorMessage.NOT_ATTEND_ERROR_MESSAGE;
import static util.constant.ErrorMessage.NOT_CREW_ERROR_MESSAGE;
import static util.constant.ErrorMessage.DUPLICATE_ATTEND_ERROR_MESSAGE;
import static util.constant.ErrorMessage.NOT_OPERATING_TIME_ERROR_MESSAGE;
import static util.constant.Value.CAMPUS_END_HOUR;
import static util.constant.Value.CAMPUS_END_MINUTE;
import static util.constant.Value.CAMPUS_START_HOUR;
import static util.constant.Value.CAMPUS_START_MINUTE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import util.parser.DateTimeParser;

public class AttendanceManager {

    private final Map<String, Records> crews;

    public AttendanceManager() {
        this.crews = new HashMap<>();
    }

    public void createCrew(String name, List<LocalDateTime> localDateTimes) {
        crews.put(name, new Records(localDateTimes));
    }

    public TimeAndStatus attendCrew(String name, LocalDateTime localDateTime) {
        validateAttendancePossibility(name, localDateTime);

        Records records = findByName(name);
        return records.attend(localDateTime);
    }

    public TimeAndStatus editCrew(String name, LocalDateTime newLocalDateTime) {
        validateEditPossibility(name, newLocalDateTime);

        Records records = findByName(name);
        return records.edit(newLocalDateTime);
    }

    public Map<String, StatisticsResult> sortCrew(LocalDate nowDate) {
        return findWarningCrews(nowDate)
            .entrySet().stream()
            .sorted(Comparator.comparing((Map.Entry<String, StatisticsResult> entry)
                    -> entry.getValue().getPenalty(), Comparator.naturalOrder())
                .thenComparing(entry
                    -> entry.getValue().getAbsenceCount(), Comparator.reverseOrder())
                .thenComparing(Map.Entry::getKey))
            .collect(LinkedHashMap::new, (map, entry)
                -> map.put(entry.getKey(), entry.getValue()), Map::putAll);
    }

    public Map<String, StatisticsResult> findWarningCrews(LocalDate nowDate) {
        return AttendanceStatistics.calculateExpelledWarning(nowDate, crews);
    }

    public Records findByName(String name) {
        try {
            return crews.get(name);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(NOT_CREW_ERROR_MESSAGE);
        }
    }

    private void validateAttendancePossibility(String name, LocalDateTime dateTime) {
        if (findByName(name).isAlreadyAttended(dateTime.toLocalDate())) {
            throw new IllegalArgumentException(DUPLICATE_ATTEND_ERROR_MESSAGE);
        }
        if (!isOperatingTime(dateTime.toLocalTime())) {
            throw new IllegalArgumentException(NOT_OPERATING_TIME_ERROR_MESSAGE);
        }
    }

    private void validateEditPossibility(String name, LocalDateTime newDateTime) {
        if (!findByName(name).isAlreadyAttended(newDateTime.toLocalDate())) {
            throw new IllegalArgumentException(NOT_ATTEND_ERROR_MESSAGE);
        }
        if (!isOperatingTime(newDateTime.toLocalTime())) {
            throw new IllegalArgumentException(NOT_OPERATING_TIME_ERROR_MESSAGE);
        }
    }

    private boolean isOperatingTime(LocalTime time) {
        LocalTime start = DateTimeParser.parseIntegerToTime(CAMPUS_START_HOUR, CAMPUS_START_MINUTE);
        LocalTime end = DateTimeParser.parseIntegerToTime(CAMPUS_END_HOUR, CAMPUS_END_MINUTE);

        return (time.isAfter(start) || time.equals(start)) && (time.isBefore(end) || time.equals(
            end));
    }
}
