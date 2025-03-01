package model;

import static constant.AttendanceConstant.COMMA_SEPARATOR;
import static constant.ErrorMessage.NOT_FOUND_ATTENDANCE;
import static constant.ErrorMessage.NOT_FOUND_CREW;

import dto.AttendanceCheckInRequest;
import dto.AttendanceCheckInResponse;
import dto.AttendanceHistoryRequest;
import dto.AttendanceHistoryResponse;
import dto.AttendanceUpdateRequest;
import dto.AttendanceUpdateResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
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

    public AttendanceCheckInResponse add(AttendanceCheckInRequest request, DateTimeGenerator dateTimeGenerator) {
        Crew crew = Crew.of(request.nickname());
        Attendance attendance = Attendance.of(dateTimeGenerator, request.checkInTime());

        if (!attendances.containsKey(crew)) {
            throw new IllegalArgumentException(NOT_FOUND_CREW.getMessage());
        }

        attendances.get(crew).add(attendance);

        return new AttendanceCheckInResponse(
                dateTimeGenerator.now().toLocalDate(),
                LocalTime.parse(request.checkInTime()),
                attendance.getAttendanceType());
    }

    public AttendanceUpdateResponse update(AttendanceUpdateRequest request, DateTimeGenerator dateTimeGenerator) {
        Crew crew = Crew.of(request.nickname());
        LocalDate date = LocalDate.of(
                dateTimeGenerator.now().getYear(),
                dateTimeGenerator.now().getMonthValue(),
                Integer.parseInt(request.day()));
        Attendance attendance = find(crew, date);
        LocalTime previousTime = attendance.getCheckInTime();
        AttendanceType previousAttendanceType = attendance.getAttendanceType();

        attendance.update(LocalTime.parse(request.updateTime()));

        return new AttendanceUpdateResponse(
                date,
                previousTime,
                previousAttendanceType,
                attendance.getCheckInTime(),
                attendance.getAttendanceType()
        );
    }

    public AttendanceHistoryResponse findHistoryByCrew(AttendanceHistoryRequest request) {
        Crew crew = Crew.of(request.nickname());
        List<Attendance> attendances = getAttendancesByCrew(crew);
        EnumMap<AttendanceType, Integer> attendanceTotal = AttendanceType.calculateTotal(attendances);
        PunishmentType punishmentType = PunishmentType.find(attendanceTotal);

        return new AttendanceHistoryResponse(
                crew.getNickname(),
                attendances,
                attendanceTotal,
                punishmentType
        );
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

    private Attendance find(Crew crew, LocalDate localDate) {
        return attendances.get(crew).stream()
                .filter(attendance -> attendance.getCheckInDate().equals(localDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ATTENDANCE.getMessage()));
    }

    public List<Attendance> getAttendancesByCrew(Crew crew) {
        List<Attendance> attendances = this.attendances.get(crew);
        return Collections.unmodifiableList(attendances);
    }
}
