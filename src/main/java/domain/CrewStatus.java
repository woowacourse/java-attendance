package domain;

import constant.Constants;

public enum CrewStatus {
    NORMAL("해당 없음", -1),
    WARNING("경고 대상자", 2),
    INTERVIEW("면담 대상자", 1),
    EXPELLED("제적 대상자", 0);

    private static final int WARNING_COUNT = 2;
    private static final int INTERVIEW_COUNT = 3;
    private static final int EXPELLED_COUNT = 6;

    private final String status;
    private final int sequence;

    CrewStatus(String status, int sequence) {
        this.status = status;
        this.sequence = sequence;
    }

    public static CrewStatus checkCrewStatus(int lateCount, int unattendedCount) {
        if (lateCount / Constants.LATE_TO_UNATTENDED_UNIT + unattendedCount >= EXPELLED_COUNT) {
            return EXPELLED;
        }
        if (lateCount / Constants.LATE_TO_UNATTENDED_UNIT + unattendedCount >= INTERVIEW_COUNT) {
            return INTERVIEW;
        }
        if (lateCount / Constants.LATE_TO_UNATTENDED_UNIT + unattendedCount >= WARNING_COUNT) {
            return WARNING;
        }
        return NORMAL;
    }

    public String getStatus() {
        return this.status;
    }

    public int getSequence() {
        return this.sequence;
    }
}
