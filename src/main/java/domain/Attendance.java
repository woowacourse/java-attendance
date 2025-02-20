package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Attendance {
    private final Crew crew;
    private final CheckInTimes checkInTimes;

    private Attendance(Crew crew, CheckInTimes checkInTimes) {
        this.crew = crew;
        this.checkInTimes = checkInTimes;
    }

    public static Attendance of(Crew crew, CheckInTimes checkInTimes) {
        return new Attendance(crew, checkInTimes);
    }

    public void checkIn(LocalDateTime time) {
        checkInTimes.add(CheckInTime.of(time));
    }

    public boolean isSameName(String crewName) {
        return crew.isSameName(crewName);
    }

    public List<LocalDateTime> getAttendanceLog() {
        return checkInTimes.getAttendanceLog(LocalDateTime.now());
    }

    public LocalDateTime modify(LocalDateTime time) {
        return checkInTimes.modify(CheckInTime.of(time));
    }
}
