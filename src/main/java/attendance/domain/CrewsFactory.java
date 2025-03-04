package attendance.domain;

import attendance.controller.AttendanceFileParser;
import attendance.controller.CsvFileReader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrewsFactory {

    public static Crews initFromCsv(final String filePath, LocalDate today) {
        String fileContents = CsvFileReader.read(filePath);
        Map<String, List<LocalDateTime>> crewAttendances = AttendanceFileParser.parse(fileContents);

        List<Crew> crews = initCrewsFromCsv(crewAttendances);

        for (Crew crew : crews) {
            crew.fillAbsentAttendances(today);
        }

        return new Crews(crews);
    }

    private static List<Crew> initCrewsFromCsv(final Map<String, List<LocalDateTime>> crewAttendances) {
        List<Crew> crews = new ArrayList<>();
        crewAttendances.forEach((nickname, attendances) -> {
            Crew crew = new Crew(nickname);
            attendances.forEach(crew::addAttendance);
            crews.add(crew);
        });
        return crews;
    }
}
