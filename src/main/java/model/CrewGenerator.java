package model;

import java.util.Arrays;
import java.util.List;

public class CrewGenerator {
    public static List<String> findCrewNames(String crewInput) {
        List<String> parsedCrewInput = Arrays.stream(crewInput.split("\n")).toList();
        List<String> crewNames = parsedCrewInput.stream()
                .map(c -> c.split(",")[0])
                .distinct()
                .toList();
        return crewNames;
    }

    public static List<Crew> registerCrew(List<String> crewNames) {
        return crewNames.stream()
                .map(Crew::new)
                .toList();
    }
}
