package domain;

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

    public boolean isSameName(String crewName) {
        return crew.isSameName(crewName);
    }
}
