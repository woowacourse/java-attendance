package attendance.domain;

public class Crew {
    private final String crewName;
    private final int lateCount;
    private final int absentCount;

    public Crew(String crewName) {
        this.crewName = crewName;
        this.lateCount = 0;
        this.absentCount = 0;
    }

    // TODO count late, absent

    public String getName() {
        return crewName;
    }
}
