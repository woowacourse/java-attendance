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
    public static void readAttendanceFile(AttendanceStorage attendanceStorage) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("./resources/attendances.csv"));
        scanner.nextLine();

        // 여기서부터 시작
        while (scanner.hasNext()) {
            String[] currentLines = scanner.nextLine().split(",");
            Crew crew = Crew.from(currentLines[0]);
            String rawDateTime = currentLines[1];

            LocalDateTime dateTime = LocalDateTime.parse(rawDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            attendanceStorage.addCrew(crew);
            attendanceStorage.addHistory(AttendanceHistory.of(crew, dateTime));
        }
    }
}
