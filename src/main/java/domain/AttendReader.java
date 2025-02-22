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
        String dateTime = row.split(",")[1];
        String[] dateTimeParse = dateTime.split(" ");
        return Attend.of(LocalDate.parse(dateTimeParse[0].strip()), LocalTime.parse(dateTimeParse[1].strip()));
    }

    private String parseName(String row) {
        return row.split(",")[0];
    }

    private List<String> readCsv() {
        URL fileURL = AttendReader.class.getClassLoader().getResource(CSV_PATH);
        return readFile(fileURL);
    }

    private List<String> readFile(URL fileURL) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileURL.toURI()))) {
            return reader.lines().skip(1).toList();
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(String.format("경로 문제: %s", fileURL.getPath()));
        }
    }
}
