package attendance.domain;

public class Crew {

    private final String crewName;

    private Crew(String crewName) {
        this.crewName = crewName;
    }

    public static Crew from(String crewName) {
        return new Crew(crewName);
    }

    public String getCrewName() {
        return crewName;
    }

    public boolean checkSameName(String crewName) {
        return this.crewName.equals(crewName);
    }
}

