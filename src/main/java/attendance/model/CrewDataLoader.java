package attendance.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CrewDataLoader {
    public static final LocalTime ABSENCE_TIME = LocalTime.of(17, 0);
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

        fillAbsencesForNoAttendance();
    }

    private void fillAbsencesForNoAttendance() {
        LocalDate currentDate = LocalDate.of(2024, 12, 1);
        while (isAttendableDate(currentDate)) {
            addAbsence(currentDate);
            currentDate = currentDate.plusDays(1);
        }
    }

    private boolean isAttendableDate(LocalDate currentDate) {
        return currentDate.isBefore(LocalDate.of(2025, 1, 1)) &&
                currentDate.isBefore(CustomLocalDateTime.now().toLocalDate());
    }

    private void addAbsence(LocalDate currentDate) {
        for (Crew crew : crews.getCrews()) {
            AttendanceHistory attendanceHistory = crew.getAttendanceHistory();
            if (!canAttend(attendanceHistory, currentDate)) {
                continue;
            }
            LocalDateTime absenceDatetime = LocalDateTime.of(currentDate, ABSENCE_TIME);
            crew.addAttendanceDetail(new AttendanceDetail(absenceDatetime));
        }
    }

    private boolean canAttend(AttendanceHistory attendanceHistory, LocalDate currentDate) {
        return !(attendanceHistory.containsDate(currentDate) || CustomLocalDateTime.isHoliday(currentDate));
    }

    private void addCrew(Crew crew, LocalDateTime dateTime) {
        if (!crews.containsCrew(crew.getName())) {
            crews.add(crew);
            crew.addAttendanceDetail(new AttendanceDetail(dateTime));
            return;
        }
        crews.findCrew(crew.getName()).addAttendanceDetail(new AttendanceDetail(dateTime));
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
