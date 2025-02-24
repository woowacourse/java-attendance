package attendance.utils;

import attendance.domain.Crew;
import attendance.domain.CrewManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceFileReader {
    private static final String PATH = "src/main/resources/attendances";
    private static final String SPLIT_DELIMITER = ",";
    private static final int INDEX_AS_CREW_NAME = 0;
    private static final int INDEX_AS_ATTENDANCE_DATE_TIME = 1;
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static BufferedReader read() throws IOException {
        return new BufferedReader(new FileReader(PATH));
    }

    public static void initializeAttendances(final BufferedReader bufferedReader, CrewManager crewManager) throws IOException {
        String string;
        bufferedReader.readLine();
        while ((string = bufferedReader.readLine()) != null) {
            String[] split = string.split(SPLIT_DELIMITER);
            Crew crew = parseCrew(split);
            LocalDateTime attendanceDateTime = parseAttendanceDateTime(split);
            crewManager.addCrew(crew);
            crew = crewManager.findByCrewName(crew.getName());
            crew.doAttendance(attendanceDateTime.toLocalDate(), attendanceDateTime.toLocalTime());
        }
    }

    private static Crew parseCrew(String[] split) {
        String crewName = split[INDEX_AS_CREW_NAME];
        return new Crew(crewName);
    }

    private static LocalDateTime parseAttendanceDateTime(String[] split) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        String attendanceDateTime = split[INDEX_AS_ATTENDANCE_DATE_TIME];
        return LocalDateTime.parse(attendanceDateTime, formatter);
    }
}

