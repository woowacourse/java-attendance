package attendance.domain;

public class Crew {
    private final String crewName;
    private final int lateCount;
    private final int absentCount;

    public Crew(String crewName, int lateCount, int absentCount) {
        this.crewName = crewName;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
    }
}
