package util;

import domain.Attendance;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SPLIT_DELIMITER = ",";

    public static Attendance readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            oneLineSkip(br);
            return createAttendance(br);
        } catch (IOException e) {
            throw new IllegalArgumentException("잘못된 파일 입니다.");
        }
    }

    private static Attendance createAttendance(final BufferedReader br) throws IOException {
        String line;

        Attendance attendance = new Attendance();
        while ((line = oneLineSkip(br)) != null) {
            String[] lineSplit = split(line);

            Crew crew = createCrew(lineSplit);
            LocalDateTime localDateTime = createLocalDateTime(lineSplit);

            attendance.addAttendance(crew, localDateTime);
        }
        return attendance;
    }

    private static String oneLineSkip(final BufferedReader br) throws IOException {
        return br.readLine();
    }

    private static String[] split(final String line) {
        validateSplit(line);
        return line.split(SPLIT_DELIMITER);
    }

    private static void validateSplit(final String line) {
        if (!line.contains(SPLIT_DELIMITER)) {
            throw new IllegalArgumentException("잘못된 구분자 입니다.");
        }
    }

    private static Crew createCrew(final String[] lineSplit) {
        String name = lineSplit[0].trim();
        return Crew.from(name);
    }

    private static LocalDateTime createLocalDateTime(final String[] lineSplit) {
        String dateTime = lineSplit[1].trim();
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
    }

}
