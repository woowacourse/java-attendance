package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewRecordsGenerator {
    private static final int NICKNAME_INDEX = 0;
    private static final int DATE_TIME_INDEX = 1;

    public CrewRecords generate(LocalDate currentDate, List<String> lines) {
        Map<Crew, AttendanceRecords> crewRecords = new HashMap<>();
        for (String line : lines) {
            fillRecords(currentDate, line, crewRecords);
        }
        for (AttendanceRecords attendanceRecords : crewRecords.values()) {
            fillAbsences(currentDate, attendanceRecords);
        }
        return new CrewRecords(crewRecords);
    }

    private Crew createCrew(String line) {
        String nickname = line.split(",")[NICKNAME_INDEX];
        return new Crew(nickname);
    }

    private LocalDateTime createDateTime(String line) {
        String dateTime = line.split(",")[DATE_TIME_INDEX].replace(" ", "T");
        return LocalDateTime.parse(dateTime);
    }

    private void fillRecords(LocalDate currentDate, String line, Map<Crew, AttendanceRecords> crewRecords) {
        Crew crew = createCrew(line);
        LocalDateTime dateTime = createDateTime(line);
        if (dateTime.toLocalDate().isEqual(currentDate) || dateTime.toLocalDate().isAfter(currentDate)) {
            return;
        }
        AttendanceRecords attendanceRecords = crewRecords.getOrDefault(crew, new AttendanceRecords());
        attendanceRecords.add(new AttendanceRecord(dateTime));
        if (!crewRecords.containsKey(crew)) {
            crewRecords.put(crew, attendanceRecords);
        }
    }

    private void fillAbsences(LocalDate currentDate, AttendanceRecords attendanceRecords) {
        for (int date = 1; date < currentDate.getDayOfMonth(); date++) {
            LocalDate targetDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), date);
            addAbsentRecord(targetDate, attendanceRecords);
        }
    }

    private void addAbsentRecord(LocalDate date, AttendanceRecords attendanceRecords) {
        if (ClassSchedule.isDayOff(date) || attendanceRecords.hasRecordOnDate(date)) {
            return;
        }
        attendanceRecords.add(new AttendanceRecord(date));
    }
}
