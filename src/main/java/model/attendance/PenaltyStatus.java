package model.attendance;

import java.util.Arrays;
import model.exception.SystemException;

public enum PenaltyStatus {
    EXPELLED("제적", 6),
    MEETING("면담", 3),
    WARNING("경고", 2),
    NONE("없음", 0),
    ;

    private final String meaning;
    private final int absenceCountLowerBound;

    PenaltyStatus(String meaning, int absenceCountLowerBound) {
        this.meaning = meaning;
        this.absenceCountLowerBound = absenceCountLowerBound;
    }

    public static PenaltyStatus findByAttendanceCount(int lateCount, int absenceCount) {
        int finalAbsenceCount = calculateFinalAbsenceCount(lateCount, absenceCount);
        return Arrays.stream(PenaltyStatus.values())
                .filter(penaltyStatus -> finalAbsenceCount >= penaltyStatus.absenceCountLowerBound)
                .findFirst()
                .orElseThrow(SystemException::new);
    }

    public static int calculateFinalAbsenceCount(int lateCount, int absenceCount) {
        return absenceCount + (lateCount / 3);
    }

    public String getMeaning() {
        return meaning;
    }
}
