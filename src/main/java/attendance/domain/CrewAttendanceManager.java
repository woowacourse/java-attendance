package attendance.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendanceManager {

    private final Map<String, Attendances> crewAttendance = new HashMap<>();

    public void addNewCrew(String nickname, Attendances attendances) {
        crewAttendance.put(nickname, attendances);
    }

    public Attendance processAttendanceCheck(final String nickname, final LocalDateTime dateTime) {
        Attendances attendances = crewAttendance.get(nickname);
        Attendances newAttendances = attendances.registerAttendance(dateTime);
        crewAttendance.put(nickname, newAttendances);

        return newAttendances.findAttendanceByDate(dateTime.toLocalDate());
    }

    public AttendanceUpdate processAttendanceUpdate(final String nickname, final LocalDateTime dateTime) {
        Attendances attendances = crewAttendance.get(nickname);
        Attendance beforeAttendance = attendances.findAttendanceByDate(dateTime.toLocalDate());

        Attendances newAttendances = attendances.updateAttendance(dateTime);
        Attendance afterAttendance = newAttendances.findAttendanceByDate(dateTime.toLocalDate());

        return new AttendanceUpdate(beforeAttendance, afterAttendance);
    }

    public AttendanceRecord getAttendanceRecord(String nickname) {
        Attendances attendances = crewAttendance.get(nickname);
        List<Attendance> excludingToday = attendances.getAttendanceRecord().getRecordExcludingToday();

        return new AttendanceRecord(excludingToday);
    }
}
