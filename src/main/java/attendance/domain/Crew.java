package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Crew {
    private final String name;
    private final Attendances attendances;

    public Crew(String name) {
        this.name = name;
        this.attendances = new Attendances();
    }

    public void attend(LocalDateTime attendTime) {
        attendances.attend(attendTime);
    }

    public Attendance modify(LocalDateTime modifyTime) {
        return attendances.modify(modifyTime);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatus(int today) {
        return attendances.countAttendanceStatus(today);
    }

    public Map<LocalDate, Attendance> getTimeStamps(int today) {
        return attendances.getAttendances(today);
    }

    public boolean isNameMatch(String anotherName) {
        return this.name.equals(anotherName);
    }

    public String getName() {
        return name;
    }
}
