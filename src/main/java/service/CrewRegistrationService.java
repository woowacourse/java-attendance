package service;

import domain.AttendanceBook;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import utils.CsvReader;
import utils.ParsingUtils;

public class CrewRegistrationService {
    public AttendanceBook registerCrews(String filePath) {
        AttendanceBook attendanceBook = new AttendanceBook();
        List<String> existedRecords = CsvReader.readExistedRecords(filePath);
        existedRecords.removeFirst();
        for (String existedRecord : existedRecords) {
            String name = ParsingUtils.parseRecordToNameAndDate(existedRecord).getFirst();
            String timeLog = ParsingUtils.parseRecordToNameAndDate(existedRecord).getLast();

            String date = ParsingUtils.parseTimeLogToDateAndTime(timeLog).getFirst();
            String time = ParsingUtils.parseTimeLogToDateAndTime(timeLog).getLast();

            attendanceBook.registerCrew(name, LocalDate.parse(date), LocalTime.parse(time));
        }
        return attendanceBook;
    }
}