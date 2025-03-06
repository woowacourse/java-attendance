package domain;

public enum AbsenceLevel {
    NORMAL("정상", 0),
    WARNING("경고", 6),
    MEETING("면담", 9),
    GET_OUT("제적", 15);

    private static final int lateToAbsent = 3;

    private final String name;
    private final int standardForAbsenceLevel;

    AbsenceLevel(String name, int standardForAbsenceLevel) {
        this.name = name;
        this.standardForAbsenceLevel = standardForAbsenceLevel;
    }

    public static AbsenceLevel getAbsenceLevel(int lateCount, int absentCount) {
        if (sumOfCount(absentCount, lateCount) > GET_OUT.standardForAbsenceLevel) {
            return GET_OUT;
        }
        if (sumOfCount(absentCount, lateCount) >= MEETING.standardForAbsenceLevel) {
            return MEETING;
        }
        if (sumOfCount(absentCount, lateCount) >= WARNING.standardForAbsenceLevel) {
            return WARNING;
        }
        return NORMAL;
    }

    private static int sumOfCount(int absentCount, int lateCount) {
        return lateCount + absentCount * lateToAbsent;
    }

    public String getName() {
        return name;
    }
}
