package fixture;

import domain.AttendanceRecord;
import domain.AttendanceRecords;
import domain.Crew;
import domain.CrewRecords;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CrewRecordsFixture {
    public static CrewRecords fromNicknames(String... nicknames) {
        Map<Crew, AttendanceRecords> crewRecords = new HashMap<>();
        for (String nickname : nicknames) {
            Crew crew = new Crew(nickname);
            AttendanceRecords attendanceRecords = new AttendanceRecords();
            crewRecords.put(crew, attendanceRecords);
        }
        return new CrewRecords(crewRecords);
    }

    public static CrewRecords of(String nickname, String... dateTimes) {
        Crew crew = new Crew(nickname);
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        for (String dateTime : dateTimes) {
            AttendanceRecord record = new AttendanceRecord(LocalDateTime.parse(dateTime));
            attendanceRecords.add(record);
        }
        return new CrewRecords(Map.of(crew, attendanceRecords));
    }
}
