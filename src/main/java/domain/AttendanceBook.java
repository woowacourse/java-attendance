package domain;

import static util.parser.DateTimeParser.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AttendanceBook {

    private final Map<String, Crew> crewRecords;

    public AttendanceBook() {
        this.crewRecords = new HashMap<>();
    }

    public int countCrew() {
        return crewRecords.size();
    }

    public void initializeCrewRecords(Scanner scanner) {
        Map<String, List<LocalDateTime>> result = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] attributes = scanner.nextLine().split(",");
            String name = attributes[0];
            LocalDateTime dateTime = parseStringToDateTime(attributes[1]);

            result.putIfAbsent(name, new ArrayList<>());
            result.get(name).add(dateTime);
        }
        createCrews(result);
    }

    private void createCrews(Map<String, List<LocalDateTime>> result) {
        for (String name : result.keySet()) {
            List<LocalDateTime> dailyRecords = result.get(name);
            crewRecords.put(name, new Crew());
            crewRecords.get(name).initializeDailyRecords(dailyRecords);
        }
    }
}