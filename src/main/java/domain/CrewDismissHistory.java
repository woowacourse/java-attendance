package domain;

public record CrewDismissHistory(String crewNickname, CrewDismiss crewDismiss) implements
        Comparable<CrewDismissHistory> {

    private static final int LATE_WEIGHT = 3;

    @Override
    public int compareTo(CrewDismissHistory other) {
        int absenceWeight = crewDismiss.late() / LATE_WEIGHT + crewDismiss.absence();
        int otherAbsenceWeight = other.crewDismiss.late() / LATE_WEIGHT + other.crewDismiss.absence();
        if (otherAbsenceWeight - absenceWeight != 0) {
            return otherAbsenceWeight - absenceWeight;
        }
        return crewNickname.compareTo(other.crewNickname);
    }
}
