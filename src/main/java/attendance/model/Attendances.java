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

    public void initAttendances(List<List<String>> csvData, Crews crews) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
        csvData.stream()
                .map(row -> new Attendance(
                        crews.findCrew(row.getFirst()),
                        LocalDateTime.parse(row.getLast(), dateTimeFormatter)
                ))
                .forEach(attendances::add);
    }

    public void calculateAttendancesType() {
        for (Attendance attendance : attendances) {
            attendance.calculateAttendanceType();
        }
    }

    public Attendance findAttendance(Crew crew, LocalDateTime dateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.isCrewAttendance(crew))
                .filter(attendance -> attendance.isSameDateTime(dateTime))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("크루와 시간에 해당하는 출석 기록이 없습니다."));
    }
}
