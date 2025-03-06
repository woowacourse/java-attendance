package util;

import domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AttendanceBookParser {
    public static AttendanceBook parseToAttendanceBook(String fileName, DateTimeFormatter dateFormatter, DateTimeFormatter timeFormatter) {
        List<List<String>> rawAttendanceBook = CsvReader.readFile(fileName);
        Map<Crew, CheckInHistory> crewCheckInHistoryMap = new HashMap<>();
        AttendanceBook attendanceBook = AttendanceBook.of(crewCheckInHistoryMap);

        for (List<String> row : rawAttendanceBook) {
            Crew crew = Crew.of(row.get(0));
            String dateTime = row.get(1);
            CheckInDate date = CheckInDate.of(LocalDate.parse(dateTime.split(" ")[0], dateFormatter));
            CheckInTime time = CheckInTime.of(LocalTime.parse(dateTime.split(" ")[1], timeFormatter));
            try {
                CheckInHistory historyByCrew = attendanceBook.findHistoryByCrew(crew);
                historyByCrew.checkIn(date, time);
            } catch (IllegalArgumentException e) {
                Map<CheckInDate, CheckInTime> checkInHistoryMap = new TreeMap<>();
                checkInHistoryMap.put(date, time);
                CheckInHistory history = CheckInHistory.of(checkInHistoryMap);
                crewCheckInHistoryMap.put(crew, history);
            }
        }
        return attendanceBook;
    }
}
