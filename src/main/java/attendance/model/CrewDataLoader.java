package attendance.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CrewDataLoader {
    private final Crews crews;

    public CrewDataLoader(Crews crews) {
        this.crews = crews;
    }

    public void load(String path) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path))));

        bufferedReader.lines().skip(1)
                .forEach(row -> {
                    String[] parsed = parseRow(row);
                    Crew crew = parseCrew(parsed[0]);
                    LocalDateTime dateTime = parseLocalDateTime(parsed[1]);

                    addCrew(crew, dateTime);
                });

    }

    private void addCrew(Crew crew, LocalDateTime dateTime) {
        if(!crews.getCrews().contains(crew)) {
            crews.add(crew);
        }
        crew.addAttendanceDetail(new AttendanceDetail(dateTime));
    }

    private String[] parseRow(String row) {
        return row.split(",");
    }
    private Crew parseCrew(String crewName) {
        return new Crew(crewName);
    }

    private LocalDateTime parseLocalDateTime(String dateTime) {
        //2024-12-10 10:08
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
