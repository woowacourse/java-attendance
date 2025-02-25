package attendance.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        attendances = new ArrayList<>();
    }

    public void initAttendances(String name, List<List<String>> csvData) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
        csvData.stream()
                .filter(row -> row.getFirst().equals(name))
                .map(row -> new Attendance(
                        new Crew(name),
                        LocalDateTime.parse(row.getLast(), dateTimeFormatter)
                ))
                .forEach(attendances::add);

        calculateAttendancesType();
    }

    private void calculateAttendancesType() {
        for (Attendance attendance : attendances) {
            attendance.calculateAttendanceType();
        }
    }

    public int calculatePresentCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getType() == AttendanceType.PRESENT)
                .count();
    }

    public int calculateLateCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getType() == AttendanceType.LATE)
                .count();
    }

//    public int calculateAbsentCount() {
//        return (int) attendances.stream()
//                .filter(attendance -> attendance.getType() == AttendanceType.ABSENT)
//                .count();
//    }
}
