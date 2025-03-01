package model;

import static constant.AttendanceConstant.COMMA_SEPARATOR;
import static constant.ErrorMessage.ALREADY_CHECK_IN;
import static constant.ErrorMessage.CANNOT_CHECK_IN_ON_HOLIDAY;
import static constant.ErrorMessage.NOT_FOUND_ATTENDANCE;
import static constant.ErrorMessage.NOT_FOUND_CREW;
import static constant.ErrorMessage.OUT_OF_OPERATION_HOURS;

import dto.AttendanceCheckInRequest;
import dto.AttendanceCheckInResponse;
import dto.AttendanceHistoryRequest;
import dto.AttendanceHistoryResponse;
import dto.AttendanceRiskCrewsResponse;
import dto.AttendanceUpdateRequest;
import dto.AttendanceUpdateResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
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
        validateCrewExists(crew);
        validateHoliday(dateTimeGenerator.now());
        validateOperationTime(request.checkInTime(), dateTimeGenerator.now());
        validateAlreadyCheckedIn(crew, dateTimeGenerator.now());

        Attendance attendance = Attendance.of(dateTimeGenerator, request.checkInTime());
        attendances.get(crew).add(attendance);

        return new AttendanceCheckInResponse(
                dateTimeGenerator.now(),
                LocalTime.parse(request.checkInTime()),
                attendance.getAttendanceType()
        );
    }

    public AttendanceUpdateResponse update(AttendanceUpdateRequest request, DateTimeGenerator dateTimeGenerator) {
        Crew crew = Crew.of(request.nickname());
        LocalDate date = dateTimeGenerator.now().withDayOfMonth(Integer.parseInt(request.day()));

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

    public AttendanceHistoryResponse findHistoryByCrew(AttendanceHistoryRequest request,
                                                       DateTimeGenerator dateTimeGenerator) {
        Crew crew = Crew.of(request.nickname());
        List<Attendance> filteredAttendances = getAttendancesByCrew(crew).stream()
                .filter(attendance -> attendance.getCheckInDate().isBefore(dateTimeGenerator.now()))
                .toList();
        EnumMap<AttendanceType, Integer> attendanceTotal = AttendanceType.calculateTotal(filteredAttendances);
        PunishmentType punishmentType = PunishmentType.find(attendanceTotal);

        return new AttendanceHistoryResponse(crew.getNickname(), filteredAttendances, attendanceTotal, punishmentType);
    }

    public AttendanceRiskCrewsResponse findRiskCrews() {
        List<AttendanceRiskCrewsResponse.AttendanceRiskCrewResponse> sortedRiskCrews = attendances.keySet().stream()
                .map(crew -> new AttendanceRiskCrewsResponse.AttendanceRiskCrewResponse(
                        crew,
                        AttendanceType.calculateTotal(getAttendancesByCrew(crew)),
                        PunishmentType.find(AttendanceType.calculateTotal(getAttendancesByCrew(crew)))
                ))
                .sorted(Comparator
                        .comparing(AttendanceRiskCrewsResponse.AttendanceRiskCrewResponse::punishmentType)
                        .reversed()
                        .thenComparing(response -> response.attendanceTotal().getOrDefault(AttendanceType.ABSENCE, 0))
                        .reversed()
                        .thenComparing(response -> response.attendanceTotal().getOrDefault(AttendanceType.BE_LATE, 0))
                        .reversed()
                        .thenComparing(response -> response.crew().getNickname())
                )
                .toList();

        return new AttendanceRiskCrewsResponse(sortedRiskCrews);
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
        LocalDate now = dateTimeGenerator.now();
        return IntStream.rangeClosed(1, now.getDayOfMonth() - 1)
                .mapToObj(now::withDayOfMonth)
                .filter(date -> !EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(date.getDayOfWeek()))
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

    private void validateCrewExists(Crew crew) {
        if (!attendances.containsKey(crew)) {
            throw new IllegalArgumentException(NOT_FOUND_CREW.getMessage());
        }
    }

    private void validateOperationTime(String checkInTime, LocalDate now) {
        if (!AttendanceTime.isInOperationTime(now, LocalTime.parse(checkInTime))) {
            throw new IllegalArgumentException(OUT_OF_OPERATION_HOURS.getMessage());
        }
    }

    private void validateHoliday(LocalDate date) {
        if (Holiday.isHoliday(date)) {
            throw new IllegalArgumentException(CANNOT_CHECK_IN_ON_HOLIDAY.getMessage());
        }
    }

    private void validateAlreadyCheckedIn(Crew crew, LocalDate date) {
        if (attendances.getOrDefault(crew, Collections.emptyList()).stream()
                .anyMatch(attendance -> attendance.getCheckInDate().equals(date))) {
            throw new IllegalArgumentException(ALREADY_CHECK_IN.getMessage());
        }
    }

    private Attendance find(Crew crew, LocalDate localDate) {
        return attendances.getOrDefault(crew, Collections.emptyList()).stream()
                .filter(attendance -> attendance.getCheckInDate().equals(localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ATTENDANCE.getMessage()));
    }

    public List<Attendance> getAttendancesByCrew(Crew crew) {
        List<Attendance> attendances = this.attendances.get(crew);
        return Collections.unmodifiableList(attendances);
    }
}
