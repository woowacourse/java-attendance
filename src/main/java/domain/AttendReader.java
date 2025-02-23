package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendReader {

    private static final String CSV_PATH = "attendances.csv";
    private static final String CSV_DELIMITER = ",";
    private static final String DATE_TIME_DELIMITER = " ";
    private static final int NAME_INDEX = 0;
    private static final int DATE_TIME_INDEX = 1;

    public AttendanceBook loadAttendanceBook() {
        List<String> data = readCsv();
        AttendanceBook attendanceBook = new AttendanceBook();
        for (String row : data) {
            final String name = parseName(row);
            final Attend attend = parseAttend(row);
            attendanceBook.registerName(name);
            attendanceBook.attend(name, attend);
        }
        return attendanceBook;
    }

    private Attend parseAttend(String row) {
        String dateTime = row.split(CSV_DELIMITER)[DATE_TIME_INDEX];
        String[] dateTimeParse = dateTime.split(DATE_TIME_DELIMITER);
        return Attend.of(LocalDate.parse(dateTimeParse[DATE_TIME_INDEX].strip()),
                LocalTime.parse(dateTimeParse[NAME_INDEX].strip()));
    }

    private String parseName(String row) {
        return row.split(CSV_DELIMITER)[NAME_INDEX];
    }

    private List<String> readCsv() {
        URL fileURL = AttendReader.class.getClassLoader().getResource(CSV_PATH);
        return readFile(fileURL);
    }

    private List<String> readFile(URL fileURL) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileURL.toURI()))) {
            return removeTitleRow(reader);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(String.format("경로 문제: %s", fileURL.getPath()));
        }
    }

    private List<String> removeTitleRow(BufferedReader reader) {
        return reader.lines().skip(1).toList();
    }
}
