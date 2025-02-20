package attendance.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CrewDataLoader {
    private final Crews crews;
    private final LocalDateTime localDateTime;

    public CrewDataLoader(Crews crews, LocalDateTime localDateTime) {
        this.crews = crews;
        this.localDateTime = localDateTime;
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

        LocalDate currentDate = LocalDate.of(2024, 12, 1);
        while (
                currentDate.isBefore(LocalDate.of(2025, 1, 1))
                        && currentDate.isBefore(CustomLocalDateTime.now().toLocalDate())
        ) {
            for (Crew crew : crews.getCrews()) {
                AttendanceHistory attendanceHistory = crew.getAttendanceHistory();
                if (attendanceHistory.containsNowDate(currentDate) || WoowaDayOfWeek.isHoliday(currentDate)) {
                    continue;
                }
                LocalDateTime lateDatetime = LocalDateTime.of(currentDate, LocalTime.of(17, 0));
                crew.addAttendanceDetail(new AttendanceDetail(lateDatetime));
            }
            currentDate = currentDate.plusDays(1);
        }
    }

    private void addCrew(Crew crew, LocalDateTime dateTime) {
        if (!crews.containsCrew(crew.getName())) {
            crews.add(crew);
            crew.addAttendanceDetail(new AttendanceDetail(dateTime));

            return;
        }
        crews.findCrew(crew).addAttendanceDetail(new AttendanceDetail(dateTime));

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
