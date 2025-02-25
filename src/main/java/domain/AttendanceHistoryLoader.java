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

    public Crews loadCrews(FileReader fileReader) throws IOException {
        Crews crews = new Crews();
        Map<String, Crew> crewMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(fileReader)) {
            skipHeader(reader);
            loadAttendanceHistory(reader, crewMap, crews);
        } catch (IOException e) {
            throw new IOException("[ERROR] 초기 출석 데이터를 로드하는 중 오류가 발생하였습니다.");
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
        String crewName = values[0];

        String[] datetimeValues = values[1].split(" ");
        String date = datetimeValues[0];
        String time = datetimeValues[1];

        Crew crew = getCrew(crewMap, crews, crewName);

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
