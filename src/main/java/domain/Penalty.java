package domain;

public enum Penalty {
    제적, 면담, 경고, NONE;

    public static Penalty from(int absentCount) {
        if (absentCount > 5) {
            return 제적;
        }
        if (absentCount >= 3) {
            return 면담;
        }
        if (absentCount == 2) {
            return 경고;
        }
        return NONE;
    }
}
