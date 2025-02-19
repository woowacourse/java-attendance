package controller;

import domain.Attendance;
import domain.Attendances;
import domain.CheckInTimes;
import domain.Crew;
import util.CsvParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {

    public static void run() {
        Attendances attendances = registerAttendances();

    }

    private static Attendances registerAttendances() {
        List<List<String>> rawAttendances = CsvParser.readFile("src/main/resources/attendances.csv");

        List<Attendance> attendances = new ArrayList<>();

        Attendances entity = Attendances.of(attendances);

        for (List<String> line : rawAttendances) {
            String crewName = line.get(0);
            try {
                Attendance attendanceByName = entity.findAttendanceByName(crewName);
                LocalDateTime time = getLocalDateTime(line.get(1));
                attendanceByName.checkIn(time);
            } catch (IllegalArgumentException e) {
                Crew crew = Crew.of(crewName);
                Attendance newAttendance = Attendance.of(crew, CheckInTimes.of(List.of()));
                LocalDateTime time = getLocalDateTime(line.get(1));
                newAttendance.checkIn(time);
                attendances.add(newAttendance);
            }
        }

        return entity;
    }

    private static LocalDateTime getLocalDateTime(String rawDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time = LocalDateTime.parse(rawDateTime, dateTimeFormatter);
        return time;
    }
}
