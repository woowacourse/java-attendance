package domain;

import java.time.LocalDateTime;
import java.util.List;

public class CrewRecordsGenerator {
    public CrewRecords generate(List<String> lines) {
        CrewRecords crewRecords = new CrewRecords();

        for (String line : lines) {
            String nickname = line.split(",")[0];
            String dateTime = line.split(",")[1].replace(" ", "T");
            Crew crew = new Crew(nickname);
            if (!crewRecords.getRecords().containsKey(crew)) {
                AttendanceRecords attendanceRecords = new AttendanceRecords();
                attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse(dateTime)));
                crewRecords.addCrewRecords(crew, attendanceRecords);
                continue;
            }
            crewRecords.addRecord(crew, new AttendanceRecord(LocalDateTime.parse(dateTime)));
        }
        return crewRecords;
    }
}
