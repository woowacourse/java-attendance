package config;

import domain.Attendance;
import domain.Crew;
import domain.Crews;
import domain.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceHistoryLoader {


    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Crews loadCrews() {
        List<Crew> crews = new ArrayList<>();
        Map<String, Crew> crewMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/attendances.csv"))) {
            skipItemTitle(reader);
            loadAttendanceHistory(reader, crewMap, crews);
        } catch (IOException e) {
            throw new IllegalStateException("크루원들의 출석 기록을 읽는 중 오류가 발생했습니다.", e);
        }

        return new Crews(crews);
    }

    private void skipItemTitle(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private void loadAttendanceHistory(BufferedReader reader, Map<String, Crew> crewMap, List<Crew> crews) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            String[] datetimeValues = values[1].split(" ");
            String date = datetimeValues[0];
            String time = datetimeValues[1];

            Crew crew = getCrew(crewMap, crews, values[0]);

            crew.addAttendance(new Attendance(new Day(LocalDate.parse(date, dateFormatter)), LocalTime.parse(time, timeFormatter)));
        }
    }

    private Crew getCrew(Map<String, Crew> crewMap, List<Crew> crews, String nickname) {
        return crewMap.computeIfAbsent(nickname, key -> {
            Crew newCrew = new Crew(nickname);
            crews.add(newCrew);
            return newCrew;
        });
    }
}
