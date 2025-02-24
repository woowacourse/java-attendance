package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AttendanceInitializer {

    public static List<String> readCrewAndAttendanceData(String data) {
        return Arrays.stream(data.split("\n")).toList();
    }

    public static List<String> extractUniqueCrewData(List<String> combinedData) {
        return combinedData.stream()
                .map(data -> data.split(",")[0])
                .distinct()
                .toList();
    }

    public static Attendance parseAttendanceFrom(String combinedData) {
        String dateAndTime = combinedData.split(",")[1];
        DateTimeFormatter yearMonthDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime attendanceTime = LocalDateTime.parse(dateAndTime, yearMonthDateTimeFormatter);
        return new Attendance(attendanceTime.toLocalDate(), attendanceTime.toLocalTime());
    }

    public static Map<Crew, Attendances> initializeAttendanceOf(Crews crews) {
        Map<Crew, Attendances> attendances = new HashMap<>();
        for (Crew crew : crews.getCrews()) {
            List<Attendance> defaultAttendances = IntStream.range(1, 32)
                    .mapToObj(date -> new Attendance(
                            LocalDate.of(2024, 12, date),
                            LocalTime.of(0, 0)))
                    .toList();
            attendances.put(crew, new Attendances(defaultAttendances));
        }
        return attendances;
    }
}
