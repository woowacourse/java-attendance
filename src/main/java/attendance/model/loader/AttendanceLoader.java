package attendance.model.loader;

import attendance.util.DateFormatUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AttendanceLoader {
    public static final String FILE_NAME = "attendances.csv";
    public static final String ROW_DELIMITER = ",";
    public static final int CREW_NAME_COLUMN_INDEX = 0;
    public static final int ATTENDANCE_DATE_TIME_COLUMN_INDEX = 1;

    private final List<RawAttendanceEntry> rawEntries = new ArrayList<>();

    public void load() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(FILE_NAME)))
        );
        bufferedReader.lines().skip(1).forEach(row -> {
            String[] parsed = parseRow(row);
            String crewName = parsed[CREW_NAME_COLUMN_INDEX];
            LocalDateTime dateTime = DateFormatUtil.parseLocalDateTime(parsed[ATTENDANCE_DATE_TIME_COLUMN_INDEX]);
            add(crewName, dateTime);
        });
    }

    private String[] parseRow(String row) {
        return row.split(ROW_DELIMITER);
    }

    public void add(String crewName, LocalDateTime dateTime) {
        rawEntries.add(new RawAttendanceEntry(crewName, dateTime));
    }

    public List<RawAttendanceEntry> getAllEntries() {
        return rawEntries;
    }

    public record RawAttendanceEntry(String crewName, LocalDateTime dateTime) {
    }
}
