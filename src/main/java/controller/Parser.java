package controller;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.AttendanceStatuses;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {
    public static LocalTime stringToLocalTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, formatter);
    }

    public static Map<LocalDate, LocalTime> getAttendanceBook(AttendanceBook attendanceBook) {
        return attendanceBook.getAttendanceBook().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getDate(),
                        entry -> entry.getValue().getTime()
                ));
    }

    public static Map<LocalDate, AttendanceStatus> getAttendanceStatuses(AttendanceStatuses attendanceStatuses) {
        return attendanceStatuses.getAttendanceStatuses()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getDate(),
                        Map.Entry::getValue
                ));
    }

}
