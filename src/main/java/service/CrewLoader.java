package service;

import domain.CrewGroup;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CrewLoader {

    public static final String DELIMITER_COMMA = ",";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    public CrewGroup loadCrews(LocalDateTime today) {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.readFile();
        CrewGroup crewGroup = getCrewGroup(lines);

        crewGroup.addAllAbsent(today);
        crewGroup.calculateAllAttendanceCount();
        return crewGroup;
    }

    private CrewGroup getCrewGroup(List<String> lines) {
        CrewGroup crewGroup = new CrewGroup();

        for (String line : lines) {
            String[] splittedLine = line.split(DELIMITER_COMMA);
            String nickname = splittedLine[0];
            String rawDate = splittedLine[1];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            LocalDateTime dateTime = LocalDateTime.parse(rawDate, formatter);
            crewGroup.addCrew(nickname, dateTime);
        }
        return crewGroup;
    }
}
