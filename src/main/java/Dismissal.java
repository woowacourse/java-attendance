public enum Dismissal {

    WARNING("경고", 2),
    INTERVIEW("면담", 3),
    DISMISSAL("제적", 5);

    private static final int ABSENCE_CONVERSION_RATE = 3;

    private final String description;
    private final int count;

    Dismissal(final String description, final int count) {
        this.description = description;
        this.count = count;
    }

    public static Dismissal findDismissalBy(final int lateCount, int absenceCount) {
        absenceCount = calculateAbsenceCountBy(lateCount, absenceCount);
        return calcualteDismissal(absenceCount);
    }

    private static int calculateAbsenceCountBy(final int lateCount, int absenceCount) {
        return absenceCount + lateCount / ABSENCE_CONVERSION_RATE;
    }

    private static Dismissal calcualteDismissal(final int absenceCount) {
        if (absenceCount >= DISMISSAL.count) {
            return DISMISSAL;
        }

        if (absenceCount >= INTERVIEW.count) {
            return INTERVIEW;
        }

        return WARNING;
    }
}
