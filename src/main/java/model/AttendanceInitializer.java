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

    public static Map<Crew, AttendanceBook> initializeAttendanceOf(Crews crews) {
        Map<Crew, AttendanceBook> attendances = new HashMap<>();
        for (Crew crew : crews.getCrews()) {
            List<Attendance> defaultAttendances = IntStream.range(1, 32)
                    .mapToObj(date -> new Attendance(
                            LocalDate.of(2024, 12, date),
                            Common.noneAttendanceTime))
                    .collect(Collectors.toList()); //TODO : toList면 불변이 되어 수정 불가능해짐
            attendances.put(crew, new AttendanceBook(defaultAttendances));
        }
        return attendances;
    }

    public static void updateAttendances(Crews crews, List<String> combinedData, Map<Crew, AttendanceBook> defaultAttendances) {
        for (String data : combinedData) {
            Crew crew = crews.findCrewByName(data.split(",")[0]).orElseThrow(RuntimeException::new);
            AttendanceBook attendanceBook = defaultAttendances.get(crew);
            LocalDateTime attendanceTime = parseAttendanceFrom(data);
            attendanceBook.register(attendanceTime.toLocalDate(), attendanceTime.toLocalTime());
        }
    }

    public static LocalDateTime parseAttendanceFrom(String combinedData) {
        String dateAndTime = combinedData.split(",")[1];
        DateTimeFormatter yearMonthDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime attendanceTime = LocalDateTime.parse(dateAndTime, yearMonthDateTimeFormatter);
        return attendanceTime;
    }
}
