package attendance.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CrewDataLoader {
    public static final String ROW_DELIMITER = ",";
    public static final DateTimeFormatter CSV_DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final int CREW_NAME_COLUMN_INDEX = 0;
    public static final int ATTENDANCE_DATE_TIME_COLUMN_INDEX = 1;
    private final AttendanceRegister attendanceRegister;

    public CrewDataLoader(AttendanceRegister attendanceRegister) {
        this.attendanceRegister = attendanceRegister;
    }

    public void load(String path) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)))
        );
        bufferedReader.lines().skip(1).forEach(row -> {
            String[] parsed = parseRow(row);
            String crewName = parsed[CREW_NAME_COLUMN_INDEX];
            LocalDateTime dateTime = parseLocalDateTime(parsed[ATTENDANCE_DATE_TIME_COLUMN_INDEX]);
            attendanceRegister.attend(crewName, new AttendanceDateTime(
                    new AttendanceDate(dateTime.toLocalDate()),
                    new AttendanceTime(dateTime.toLocalTime())
            ));
        });
    }

    private String[] parseRow(String row) {
        return row.split(ROW_DELIMITER);
    }

    private LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, CSV_DATE_TIME_FORMATER);
    }
}
