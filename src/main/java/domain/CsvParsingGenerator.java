package domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParsingGenerator implements CrewAttendanceRecordsGenerator {
    private static final String FILE_PATH = "/attendances.csv";
    private static final String CREW_RECORD_SEPARATOR = ",";
    private static final String DATE_TIME_SEPARATOR = " ";
    private static final int HEADER_ROW = 1;
    private static final int CREW_INDEX = 0;
    private static final int RECORD_INDEX = 1;
    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;

    @Override
    public Map<Crew, AttendanceRecords> generate(LocalDate today) {
        Map<Crew, AttendanceRecords> crewAttendanceRecords = new HashMap<>();
        List<String> rows = getStrings().stream().skip(HEADER_ROW).toList();
        for (String row : rows) {
            Crew crew = parseCrew(row);
            AttendanceRecord attendanceRecord = parseAttendanceRecord(row);
            AttendanceRecords existingRecords = crewAttendanceRecords.getOrDefault(crew, new AttendanceRecords());
            existingRecords.addRecord(attendanceRecord);
            crewAttendanceRecords.put(crew, existingRecords);
        }
        return fillAbsence(crewAttendanceRecords, today);
    }

    private AttendanceRecord parseAttendanceRecord(String row) {
        String record = row.split(CREW_RECORD_SEPARATOR)[RECORD_INDEX];
        String dateRecord = record.split(DATE_TIME_SEPARATOR)[DATE_INDEX];
        String timeRecord = record.split(DATE_TIME_SEPARATOR)[TIME_INDEX];
        LocalDate date = LocalDate.parse(dateRecord);
        LocalTime time = LocalTime.parse(timeRecord);
        return AttendanceRecord.of(date, time);
    }

    private Crew parseCrew(String row) {
        return new Crew(row.split(",")[CREW_INDEX]);
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

    private Map<Crew, AttendanceRecords> fillAbsence(Map<Crew, AttendanceRecords> crewAttendanceRecords,
                                                     LocalDate today) {
        crewAttendanceRecords.values().forEach(attendanceRecord -> attendanceRecord.fillAbsences(today));
        return crewAttendanceRecords;
    }
}
