package controller.store;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceLogs;
import domain.crew.Crew;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.Convertor;
import util.CsvReader;

public class AttendanceCsvController implements AttendanceStoreController {

    private static final String ATTENDANCES_STORE_PATH = "src/main/resources/attendances.csv";

    @Override
    public AttendanceBook store() throws IOException {
        List<String[]> storeLines = CsvReader.readCsvLines(ATTENDANCES_STORE_PATH);
        Map<Crew, AttendanceLogs> crewsAttendanceLogs = new HashMap<>();
        for (String[] storeLine : storeLines.subList(1, storeLines.size())) {
            addAttendanceFromStoreLine(crewsAttendanceLogs, storeLine);
        }
        return new AttendanceBook(crewsAttendanceLogs);
    }

    private static void addAttendanceFromStoreLine(Map<Crew, AttendanceLogs> crewsAttendanceLogs, String[] storeLine) {
        String crewName = storeLine[0];
        AttendanceLogs crewAttendanceLogs = findCrewAttendanceLogs(crewsAttendanceLogs, crewName);
        String dateTime = storeLine[1];
        crewAttendanceLogs.registerLog(Convertor.convertStringToDateTime(dateTime));
    }

    private static AttendanceLogs findCrewAttendanceLogs(Map<Crew, AttendanceLogs> crewsAttendanceLogs,
                                                         String crewName) {
        Crew crew = crewsAttendanceLogs.keySet().stream()
                .filter(c -> c.getName().equals(crewName))
                .findFirst()
                .orElseGet(() -> initializeCrewAttendance(crewsAttendanceLogs, crewName));
        return crewsAttendanceLogs.get(crew);
    }

    private static Crew initializeCrewAttendance(Map<Crew, AttendanceLogs> crewsAttendanceLogs, String crewName) {
        Crew newCrew = new Crew(crewName);
        crewsAttendanceLogs.put(newCrew, new AttendanceLogs());
        return newCrew;
    }
}

