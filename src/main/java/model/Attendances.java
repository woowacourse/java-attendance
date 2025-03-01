package model;

import static constant.AttendanceConstant.COMMA_SEPARATOR;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import util.DateTimeGenerator;
import util.InputParser;

public class Attendances {

    private final Map<Crew, List<Attendance>> attendances;

    private Attendances(Map<Crew, List<Attendance>> attendances) {
        this.attendances = attendances;
    }

    public static Attendances from(List<String> inputs, DateTimeGenerator dateTimeGenerator) {
        Map<Crew, List<Attendance>> attendances = parseAttendances(inputs);
        List<LocalDate> allDates = generateDateRange(dateTimeGenerator);
        fillMissingAttendances(attendances, allDates);

        return new Attendances(attendances);
    }

    private static Map<Crew, List<Attendance>> parseAttendances(List<String> inputs) {
        return inputs.stream()
                .map(line -> InputParser.split(line, COMMA_SEPARATOR))
                .collect(Collectors.toMap(
                        line -> Crew.of(line.get(0)),
                        line -> new ArrayList<>(List.of(Attendance.of(line.get(1)))),
                        (existing, replacement) -> {
                            existing.addAll(replacement);
                            return existing;
                        },
                        HashMap::new
                ));
    }

    private static List<LocalDate> generateDateRange(DateTimeGenerator dateTimeGenerator) {
        LocalDate now = dateTimeGenerator.now().toLocalDate();

        return IntStream.rangeClosed(1, now.getDayOfMonth() - 1)
                .mapToObj(now::withDayOfMonth)
                .filter(date -> !date.getDayOfWeek().equals(DayOfWeek.SATURDAY))
                .filter(date -> !date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                .filter(date -> !Holiday.isHoliday(date))
                .toList();
    }

    private static void fillMissingAttendances(Map<Crew, List<Attendance>> attendances, List<LocalDate> allDates) {
        attendances.forEach((crew, attendanceList) -> {
            Set<LocalDate> recordedDates = attendanceList.stream()
                    .map(Attendance::getCheckInDate)
                    .collect(Collectors.toSet());

            allDates.stream()
                    .filter(date -> !recordedDates.contains(date))
                    .map(Attendance::ofEmpty)
                    .forEach(attendanceList::add);

            attendanceList.sort(Comparator.comparing(Attendance::getCheckInDate));
        });
    }

    public List<Attendance> getAttendancesByCrew(Crew crew) {
        List<Attendance> attendances = this.attendances.get(crew);
        return Collections.unmodifiableList(attendances);
    }
}
