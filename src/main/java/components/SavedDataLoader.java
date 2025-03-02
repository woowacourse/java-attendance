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
    private static final String LOAD_FILE_PATH = "./src/main/resources/attendances.csv";
    private static final int CREW_INDEX = 0;
    private static final int ATTENDANCE_INDEX = 1;
    private static final String SAVED_ATTENDANCE_LINE_DELIMITER = ",";
    private static final DateTimeFormatter SAVED_ATTENDANCE_DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm");

    private final List<String[]> loadedData;

    public SavedDataLoader() {
        final List<String> readData = FileReader.read(LOAD_FILE_PATH);
        readData.removeFirst();
        loadedData = parsedData(readData);
    }

    private List<String[]> parsedData(final List<String> readData) {
        return readData.stream()
                .map(line -> line.split(SAVED_ATTENDANCE_LINE_DELIMITER))
                .collect(Collectors.toList());
    }

    public void loadAttendances(final AttendanceBook attendanceBook, final Crews crews) {
        loadedData.forEach(line -> loadAttendance(attendanceBook, crews, line));
    }

    private void loadAttendance(final AttendanceBook attendanceBook, final Crews crews, final String[] line) {
        final Crew crew = crews.findByName(line[CREW_INDEX]);
        registerCrew(attendanceBook, crew);
        final AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);
        attendanceHistory.attendance(parseDateTime(line[ATTENDANCE_INDEX]));
    }

    private void registerCrew(final AttendanceBook attendanceBook, final Crew crew) {
        if (!attendanceBook.containsCrew(crew)) {
            attendanceBook.registerCrew(crew);
        }
    }

    private LocalDateTime parseDateTime(final String dateTime) {
        return LocalDateTime.parse(dateTime, SAVED_ATTENDANCE_DATE_TIME_FORMATTER);
    }

    public Crews loadCrews() {
        return new Crews(loadedData.stream()
                .map(line -> line[CREW_INDEX])
                .map(Crew::new)
                .collect(Collectors.toList()));
    }


}
