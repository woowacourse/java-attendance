package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AttendanceHistoryLoader {


    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Crews loadCrews() {
        Crews crews = new Crews();
        Map<String, Crew> crewMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/attendances.csv"))) {
            skipHeader(reader);
            loadAttendanceHistory(reader, crewMap, crews);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return crews;
    }

    private void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private void loadAttendanceHistory(BufferedReader reader, Map<String, Crew> crewMap, Crews crews)
            throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseAndAddAttendance(crewMap, crews, line);
        }
    }

    private void parseAndAddAttendance(Map<String, Crew> crewMap, Crews crews, String line) {
        String[] values = line.split(",");
        String[] datetimeValues = values[1].split(" ");
        String date = datetimeValues[0];
        String time = datetimeValues[1];

        Crew crew = getCrew(crewMap, crews, values[0]);

        crew.addAttendance(new Attendance(new Day(LocalDate.parse(date, dateFormatter)),
                LocalTime.parse(time, timeFormatter)));
    }

    private Crew getCrew(Map<String, Crew> crewMap, Crews crews, String nickname) {
        return crewMap.computeIfAbsent(nickname, key -> {
            Crew newCrew = new Crew(nickname);
            crews.addCrew(newCrew);
            return newCrew;
        });
    }
}
