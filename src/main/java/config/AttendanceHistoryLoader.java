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

    public Crews loadCrews() {
        List<Crew> crews = new ArrayList<>();
        Map<String, Crew> crewMap = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/attendances.csv"))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String nickname = values[0];
                String datetime = values[1];
                String[] datetimeValues = datetime.split(" ");
                String date = datetimeValues[0];
                String time = datetimeValues[1];

                Crew crew = crewMap.computeIfAbsent(nickname, key -> {
                    Crew newCrew = new Crew(nickname);
                    crews.add(newCrew);
                    return newCrew;
                });

                crew.addAttendance(new Attendance(new Day(LocalDate.parse(date, dateFormatter)), LocalTime.parse(time, timeFormatter)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Crews(crews);
    }
}
