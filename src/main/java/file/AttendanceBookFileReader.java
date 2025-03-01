package file;

import domain.AttendanceBook;
import domain.AttendanceDateTime;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class AttendanceBookFileReader {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static AttendanceBook read(String fileName) {
        AttendanceBook book = new AttendanceBook();
        forEachLinesExceptForTopLine(fileName,
            line -> attendCrewAboutOneLine(line, book));

        return book;
    }

    private static void forEachLinesExceptForTopLine(String fileName, Consumer<String> lineConsumer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.lines()
                .skip(1)
                .forEach(lineConsumer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void attendCrewAboutOneLine(String line, AttendanceBook book) {
        var crewName = line.split(",")[0];
        Crew crew = book.findCrewByName(crewName)
            .orElse(new Crew(crewName));

        var rawDateTime = line.split(",")[1];
        var localDateTime = LocalDateTime.parse(rawDateTime, formatter);
        AttendanceDateTime dateTime = AttendanceDateTime.from(localDateTime);

        book.attend(crew, dateTime);
    }
}
