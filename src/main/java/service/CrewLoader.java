package service;

import domain.CrewGroup;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import util.FileReader;

public class CrewLoader {

    private static final String DELIMITER_COMMA = ",";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final int HOUR_INDEX = 0;
    private static final int MINUTE_INDEX = 1;

    public CrewGroup loadCrews(LocalDateTime today) {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.readFile();
        CrewGroup crewGroup = getCrewGroup(lines);

        crewGroup.addAllAbsent(today);
        return crewGroup;
    }

    private CrewGroup getCrewGroup(List<String> lines) {
        CrewGroup crewGroup = new CrewGroup();

        for (String line : lines) {
            String[] splittedLine = line.split(DELIMITER_COMMA);
            String nickname = splittedLine[HOUR_INDEX];
            String rawDate = splittedLine[MINUTE_INDEX];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            LocalDateTime dateTime = LocalDateTime.parse(rawDate, formatter);
            crewGroup.addCrew(nickname, dateTime);
        }
        return crewGroup;
    }
}
