package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void initAttendances(Crews crews, List<List<String>> attendanceRecords) {
        for (List<String> attendanceRecord : attendanceRecords) {
            String crewName = attendanceRecord.getFirst();
            Crew crew = crews.findCrew(crewName);

            List<String> dateInfo = List.of(attendanceRecord.getLast().split(" "));
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(dateInfo.getFirst()),
                    LocalTime.parse(dateInfo.getLast()));

            AttendanceType status = AttendanceType.of(localDateTime);

            attendances.add(new Attendance(crew, localDateTime, status));
        }

    }
}
