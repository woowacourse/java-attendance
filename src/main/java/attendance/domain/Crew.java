package attendance.domain;

import java.util.Objects;

public class Crew {
    private final String crewName;
    private int safeCount;
    private int lateCount;
    private int absentCount;

    public Crew(String crewName) {
        this.crewName = crewName;
        this.safeCount = 0;
        this.lateCount = 0;
        this.absentCount = 0;
    }

    public boolean isSameCrewName(final String crewName) {
        return this.crewName.equals(crewName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crew)) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(this.crewName, crew.crewName);
    }

    public void plusSafeCount() {
        this.safeCount++;
    }

    public void plusLateCount() {
        this.lateCount++;
    }

    public void plusAbsentCount() {
        this.absentCount++;
    }

    public int getSafeCount() {
        return safeCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public String getName() {
        return crewName;
    }
}
