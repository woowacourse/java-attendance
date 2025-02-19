package service;

import domain.CrewGroup;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import util.FileReader;

public class CrewLoader {
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
            String[] splittedLine = line.split(",");
            String nickname = splittedLine[0];
            String rawDate = splittedLine[1];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(rawDate, formatter);
            crewGroup.addCrew(nickname, dateTime);
        }
        return crewGroup;
    }
}
