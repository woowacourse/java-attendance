import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendanceRecords {
    private static final int HEADER_ROW = 1;
    private static final int CREW_INDEX = 0;
    private static final int RECORD_INDEX = 1;

    private final Map<Crew, AttendanceRecords> crewAttendanceRecords = new HashMap<>();

    public CrewAttendanceRecords(String path) {
        List<String> rows = readContent(path).stream().skip(HEADER_ROW).toList();
        for (String row : rows) {
            Crew crew = new Crew(row.split(",")[CREW_INDEX]);
            AttendanceRecord attendanceRecord = new AttendanceRecord(row.split(",")[RECORD_INDEX]);
            AttendanceRecords existingRecords = this.crewAttendanceRecords.getOrDefault(crew, new AttendanceRecords());
            existingRecords.addRecord(attendanceRecord);
            this.crewAttendanceRecords.put(crew, existingRecords);
        }
        crewAttendanceRecords.values().forEach(AttendanceRecords::fillAbsences);
    }

    private List<String> readContent(String path) {
        if (path.isEmpty()) {
            throw new IllegalStateException("");
        }
        return getStrings(path);
    }

    private List<String> getStrings(String path) {
        try {
            InputStream inputStream = CrewAttendanceRecords.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().toList();
        } catch (NullPointerException e) {
            throw new IllegalStateException("");
        }
    }

    public boolean hasCrew(Crew crew) {
        return this.crewAttendanceRecords.containsKey(crew);
    }

    public boolean hasRecord(Crew crew, LocalDate date) {
        if (!hasCrew(crew)) {
            return false;
        }
        AttendanceRecords records = this.crewAttendanceRecords.get(crew);
        return records.hasRecordOfDate(date);
    }

    public AttendanceRecord updateAttendanceRecord(Crew crew, AttendanceRecord newAttendanceRecord) {
        AttendanceRecords records = crewAttendanceRecords.get(crew);
        records.addRecord(newAttendanceRecord);
        return records.removeRecord(newAttendanceRecord.getDate());
    }
}
