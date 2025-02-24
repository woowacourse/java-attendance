package util;

import domain.Attendance;
import domain.Attendances;
import domain.CheckInTimes;
import domain.Crew;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceParser {
    public static Attendances registerAttendances(String fileName, DateTimeFormatter formatter) {
        List<List<String>> rawAttendances = CsvParser.readFile(fileName);

        Map<Crew, Attendance> attendances = new HashMap<>();

        Attendances entity = Attendances.of(attendances);

        for (List<String> line : rawAttendances) {
            String crewName = line.get(0);
            try {
                Attendance attendanceByName = entity.findAttendanceByName(crewName);
                LocalDateTime time = getLocalDateTime(line.get(1), formatter);
                attendanceByName.checkIn(time);
            } catch (IllegalArgumentException e) {
                Crew crew = Crew.of(crewName);
                Attendance newAttendance = Attendance.of(crew, CheckInTimes.of(List.of()));
                LocalDateTime time = getLocalDateTime(line.get(1), formatter);
                newAttendance.checkIn(time);
                attendances.put(crew, newAttendance);
            }
        }

        return entity;
    }

    private static LocalDateTime getLocalDateTime(String rawDateTime, DateTimeFormatter formatter) {
        LocalDateTime time = LocalDateTime.parse(rawDateTime, formatter);
        return time;
    }
}
