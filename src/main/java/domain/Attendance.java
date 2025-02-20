package domain;

import java.time.LocalDateTime;
import java.util.List;

import static domain.PenaltyStatus.LATE_TO_ABSENCE_UNIT;

public class Attendance implements Comparable<Attendance> {
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
        return checkInTimes.getAttendanceLog(LocalDateTime.now()).stream()
                .sorted()
                .toList();
    }

    public LocalDateTime modify(LocalDateTime time) {
        return checkInTimes.modify(CheckInTime.of(time));
    }

    public int countPresence() {
        return checkInTimes.countPresence();
    }

    public int countLate() {
        return checkInTimes.countLate();
    }

    public int countAbsence() {
        LocalDateTime now = LocalDateTime.now();

        return checkInTimes.countAbsence();
    }

    public String getName() {
        return crew.getName();
    }

    @Override
    public int compareTo(Attendance o) {
        int absenceOther = o.countAbsence();
        int lateOther = o.countLate();

        int absence = this.countAbsence();
        int late = this.countLate();

        int other = (absenceOther * LATE_TO_ABSENCE_UNIT) + lateOther;
        int me = (absence * LATE_TO_ABSENCE_UNIT) + late;

        if (me != other) {
            return me - other;
        }
        return this.crew.compareTo(o.crew);
    }
}
