package attendance.infrastructure;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Crew;
import attendance.domain.Nickname;
import attendance.util.FileResourceReader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Initializer {

    private static final String PATH = "src/main/resources/attendances.csv";
    private static final String DELIMITER = ",";
    private static final int NICKNAME_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final AttendanceBook attendanceBook;

    public Initializer() {
        this.attendanceBook = new AttendanceBook(new HashMap<>());
    }

    public AttendanceBook initAttendanceBook() {
        for (String line : FileResourceReader.read(PATH)) {
            String nickname = line.split(DELIMITER)[NICKNAME_INDEX];
            Crew crew = new Crew(new Nickname(nickname));
            LocalDate attendDate = LocalDate.parse(line.split(DELIMITER)[DATE_INDEX], DATE_TIME_FORMATTER);
            LocalTime attendTime = LocalTime.parse(line.split(DELIMITER)[DATE_INDEX], DATE_TIME_FORMATTER);
            Attendance attendance = Attendance.of(attendDate, attendTime);
            attendanceBook.add(crew, attendance);
        }
        return attendanceBook;
    }

    public LocalDate initSystemDate() {
        return LocalDate.of(2024, 12, 14);
    }
}
