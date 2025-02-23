package attendance.utils;

import attendance.domain.AttendanceHistory;
import attendance.domain.Crew;
import attendance.domain.CrewAttendanceManager;
import attendance.domain.Crews;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceFileReader {

    private static final String PATH = "src/main/resources/attendances";

    public static BufferedReader read() throws IOException {
        return new BufferedReader(new FileReader(PATH));
    }

    public static void initializeAttendances(final BufferedReader br, Crews crews,
        CrewAttendanceManager crewAttendanceManager)
        throws IOException {
        String string;
        br.readLine();
        while ((string = br.readLine()) != null) {
            String[] split = string.split(",");
            String nickname = split[0];
            Crew crew = Crew.from(nickname);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(split[1], formatter);
            AttendanceHistory attendanceHistory = AttendanceHistory.from(dateTime);
            if (!crews.addCrew(crew)) {
                crew = crews.findByCrewName(nickname);
            }
            crewAttendanceManager.addCrewAttendanceInfo(crew, attendanceHistory);
        }
    }
}

