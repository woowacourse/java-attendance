package components;

import domain.AttendanceBook;
import domain.AttendanceHistory;
import domain.Crew;
import domain.Crews;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import utils.FileReader;

public class SavedDataLoader {
    private static final String FILE_PATH = "./src/main/resources/attendances.csv";
    private static final int CREW = 0;
    private static final int ATTENDANCE = 1;
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final List<String[]> loadedData;

    public SavedDataLoader() {
        final List<String> readData = FileReader.read(FILE_PATH);
        readData.removeFirst();
        loadedData = parsedData(readData);
    }

    private List<String[]> parsedData(final List<String> readData) {
        return readData.stream()
                .map(line -> line.split(DELIMITER))
                .collect(Collectors.toList());
    }

    public void loadAttendances(final AttendanceBook attendanceBook, final Crews crews) {
        loadedData.forEach(line -> loadAttendance(attendanceBook, crews, line));
    }

    private void loadAttendance(final AttendanceBook attendanceBook, final Crews crews, final String[] line) {
        final Crew crew = crews.findByName(line[CREW]);
        registerCrew(attendanceBook, crew);
        final AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);
        attendanceHistory.attendance(parseDateTime(line[ATTENDANCE]));
    }

    private void registerCrew(final AttendanceBook attendanceBook, final Crew crew) {
        if (!attendanceBook.containsCrew(crew)) {
            attendanceBook.registerCrew(crew);
        }
    }

    private LocalDateTime parseDateTime(final String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER);
    }

    public Crews loadCrews() {
        return new Crews(loadedData.stream()
                .map(line -> line[CREW])
                .map(Crew::new)
                .collect(Collectors.toList()));
    }


}
