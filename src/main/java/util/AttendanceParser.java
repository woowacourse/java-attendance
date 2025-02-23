package util;

import domain.Attendance;
import domain.Attendances;
import domain.CheckInTimes;
import domain.Crew;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceParser {
    public static Attendances registerAttendances(String fileName, DateTimeFormatter formatter) {
        List<List<String>> rawAttendances = CsvParser.readFile(fileName);

        List<Attendance> attendanceLog = new ArrayList<>();

        Attendances attendances = Attendances.of(attendanceLog);

        for (List<String> line : rawAttendances) {
            String crewName = line.get(0);
            try {
                Attendance attendance = attendances.findAttendanceByName(crewName);
                LocalDateTime time = getLocalDateTime(line.get(1), formatter);
                attendance.checkIn(time);
            } catch (IllegalArgumentException e) {
                Crew crew = Crew.of(crewName);
                Attendance newAttendance = Attendance.of(crew, CheckInTimes.of(List.of()));
                LocalDateTime time = getLocalDateTime(line.get(1), formatter);
                newAttendance.checkIn(time);
                attendanceLog.add(newAttendance);
            }
        }

        return attendances;
    }

    private static LocalDateTime getLocalDateTime(String rawDateTime, DateTimeFormatter formatter) {
        LocalDateTime time = LocalDateTime.parse(rawDateTime, formatter);
        return time;
    }
}
