package domain;

public class CrewDismissHistory implements Comparable<CrewDismissHistory> {

    private final String crewNickname;
    private final CrewDismiss crewDismiss;

    private static final int LATE_WEIGHT = 3;

    public CrewDismissHistory(String crewNickname, CrewDismiss crewDismiss) {
        this.crewNickname = crewNickname;
        this.crewDismiss = crewDismiss;
    }

    @Override
    public int compareTo(CrewDismissHistory other) {
        int absenceWeight = crewDismiss.late() / LATE_WEIGHT + crewDismiss.absence();
        int otherAbsenceWeight = other.crewDismiss.late() / LATE_WEIGHT + other.crewDismiss.absence();
        if (otherAbsenceWeight - absenceWeight != 0) {
            return otherAbsenceWeight - absenceWeight;
        }
        return crewNickname.compareTo(other.crewNickname);
    }

    public String crewNickname() {
        return crewNickname;
    }

    public CrewDismiss crewDismiss() {
        return crewDismiss;
    }
}
