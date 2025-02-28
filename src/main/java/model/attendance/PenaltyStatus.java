package model.attendance;

public enum PenaltyStatus {
    NONE("없음"),
    WARNING("경고"),
    MEETING("면담"),
    EXPELLED("제적"),
    ;

    private final String meaning;

    PenaltyStatus(String meaning) {
        this.meaning = meaning;
    }
    public static PenaltyStatus findByAttendanceCount(int lateCount, int absenceCount) {
        int finalAbsenceCount = absenceCount + (lateCount / 3);
        //TODO : 추가 리팩토링
        if (finalAbsenceCount >= 6) {
            return EXPELLED;
        }
        if (finalAbsenceCount >= 3) {
            return MEETING;
        }
        if (finalAbsenceCount >= 2) {
            return WARNING;
        }
        return NONE;
    }

    public String getMeaning() {
        return meaning;
    }
}
