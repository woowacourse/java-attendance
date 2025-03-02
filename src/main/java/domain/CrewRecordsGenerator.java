package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewRecordsGenerator {
    public CrewRecords generate(LocalDate currentDate, List<String> lines) {
        Map<Crew, AttendanceRecords> crewRecords = new HashMap<>();

        for (String line : lines) {
            String nickname = line.split(",")[0];
            String dateTime = line.split(",")[1].replace(" ", "T");
            Crew crew = new Crew(nickname);
            if (!crewRecords.containsKey(crew)) {
                AttendanceRecords attendanceRecords = new AttendanceRecords();
                attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse(dateTime)));
                crewRecords.put(crew, attendanceRecords);
                continue;
            }
            AttendanceRecords attendanceRecords = crewRecords.get(crew);
            attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse(dateTime)));
        }
        return fillAbsences(currentDate, crewRecords);
    }

    private CrewRecords fillAbsences(LocalDate currentDate, Map<Crew, AttendanceRecords> crewRecords) {
        crewRecords.forEach((crew, attendanceRecords) -> {
            for (int date = 1; date < currentDate.getDayOfMonth(); date++) {
                LocalDate targetDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), date);
                if (ClassSchedule.isDayOff(targetDate) || attendanceRecords.hasRecordOnDate(targetDate)) {
                    continue;
                }
                attendanceRecords.add(new AttendanceRecord(LocalDateTime.of(targetDate, LocalTime.of(23, 59))));
            }
        });
        return new CrewRecords(crewRecords);
    }
}
