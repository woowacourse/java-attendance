package view;

import domain.AttendanceHistory;
import domain.AttendanceStorage;
import domain.Crew;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AttendanceFileReader {
    private static final int INDEX_OF_CREW = 0;
    private static final int INDEX_OF_DATE = 1;

    public static void applyAttendanceFileTo(AttendanceStorage attendanceStorage) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/attendances.csv"));
        scanner.nextLine();

        while (scanner.hasNext()) {
            String[] currentLines = scanner.nextLine().split(",");
            Crew crew = Crew.from(currentLines[INDEX_OF_CREW]);
            String rawDateTime = currentLines[INDEX_OF_DATE];

            LocalDateTime dateTime = LocalDateTime.parse(rawDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            attendanceStorage.add(crew);
            attendanceStorage.add(AttendanceHistory.of(crew, dateTime));
        }
    }
}
