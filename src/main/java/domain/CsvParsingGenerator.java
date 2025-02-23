package domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParsingGenerator implements CrewAttendanceRecordsGenerator {
    private static final int HEADER_ROW = 1;
    private static final int CREW_INDEX = 0;
    private static final int RECORD_INDEX = 1;
    private static final String FILE_PATH = "/attendances.csv";

    @Override
    public Map<Crew, AttendanceRecords> generate(LocalDate currentDate) {
        Map<Crew, AttendanceRecords> crewAttendanceRecords = new HashMap<>();
        List<String> rows = getStrings().stream().skip(HEADER_ROW).toList();
        for (String row : rows) {
            Crew crew = new Crew(row.split(",")[CREW_INDEX]);
            AttendanceRecord attendanceRecord = AttendanceRecord.parse(row.split(",")[RECORD_INDEX]);
            AttendanceRecords existingRecords = crewAttendanceRecords.getOrDefault(crew, new AttendanceRecords());
            existingRecords.addRecord(attendanceRecord);
            crewAttendanceRecords.put(crew, existingRecords);
        }
        return fillAbsence(crewAttendanceRecords, currentDate);
    }

    private Map<Crew, AttendanceRecords> fillAbsence(Map<Crew, AttendanceRecords> crewAttendanceRecords, LocalDate currentDate) {
        crewAttendanceRecords.values().forEach(attendanceRecord -> attendanceRecord.fillAbsences(currentDate));
        return crewAttendanceRecords;
    }

    private List<String> getStrings() {
        try {
            InputStream inputStream = CrewAttendanceRecords.class.getResourceAsStream(FILE_PATH);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().toList();
        } catch (NullPointerException e) {
            throw new IllegalStateException("");
        }
    }
}
