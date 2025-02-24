package model;

import common.Common;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AttendanceInitializer {

    public static List<String> extractUniqueCrewData(List<String> combinedData) {
        return combinedData.stream()
                .map(data -> data.split(",")[0])
                .distinct()
                .toList();
    }

    public static Map<Crew, Attendances> initializeAttendanceOf(Crews crews) {
        Map<Crew, Attendances> attendances = new HashMap<>();
        for (Crew crew : crews.getCrews()) {
            List<Attendance> defaultAttendances = IntStream.range(1, 32)
                    .mapToObj(date -> new Attendance(
                            LocalDate.of(2024, 12, date),
                            Common.noneAttendanceTime))
                    .collect(Collectors.toList()); //TODO : toList면 불변이 되어 수정 불가능해짐
            attendances.put(crew, new Attendances(defaultAttendances));
        }
        return attendances;
    }

    public static void updateAttendances(Crews crews, List<String> combinedData, Map<Crew, Attendances> defaultAttendances) {

    }

    public static LocalDateTime parseAttendanceFrom(String combinedData) {
        String dateAndTime = combinedData.split(",")[1];
        DateTimeFormatter yearMonthDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime attendanceTime = LocalDateTime.parse(dateAndTime, yearMonthDateTimeFormatter);
        return attendanceTime;
    }
}
