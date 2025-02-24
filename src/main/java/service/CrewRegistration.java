package service;

import domain.AttendanceBook;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import utils.CsvReader;
import utils.ParsingUtils;

public class CrewRegistration {
    // TODO: CsvReader 와 ParsingUtils 에서 메서드 호출
    public AttendanceBook registerCrews(String filePath) {
        List<Crew> crews = new ArrayList<>();

        List<String> existedRecords = CsvReader.readExistedRecords(filePath);
        existedRecords.removeFirst();
        for (String existedRecord : existedRecords) {
            String name = ParsingUtils.parseRecordToNameAndDate(existedRecord).getFirst();
            String timeLog = ParsingUtils.parseRecordToNameAndDate(existedRecord).getLast();

            String date = ParsingUtils.parseTimeLogToDateAndTime(timeLog).getFirst();
            String time = ParsingUtils.parseTimeLogToDateAndTime(timeLog).getLast();

            crews.add(new Crew(name, LocalDate.parse(date), LocalTime.parse(time)));
        }
        return new AttendanceBook(crews);
    }
}