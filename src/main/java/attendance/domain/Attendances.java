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

    public void initAttendances(final Crews crews, final List<List<String>> attendanceRecords) {
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

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findMatchCrewDate(Crew crew, LocalDate localDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrewDate(crew, localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 해당 크루의 출석 기록이 존재하지 않습니다."));
    }

    public void modifyAttendances(final Crew crew, final LocalDateTime localDateTime) {
        for (Attendance attendance : attendances) {
            modifyAttendance(crew, localDateTime, attendance);
        }
    }

    private static void modifyAttendance(final Crew crew, final LocalDateTime localDateTime,
                                         final Attendance attendance) {
        LocalDate localDate = localDateTime.toLocalDate();
        if (attendance.isSameCrewDate(crew, localDate)) {
            attendance.modifyLocalDateTime(localDateTime);
            attendance.modifyAttendanceType(localDateTime);
        }
    }

    public String findOriginalTime(final Crew crew, final LocalDate localDate) {
        return findMatchCrewDate(crew, localDate).getTimeValue();
    }

    public AttendanceType findOriginalType(final Crew crew, final LocalDate localDate) {
        return findMatchCrewDate(crew, localDate).getType();
    }
}
