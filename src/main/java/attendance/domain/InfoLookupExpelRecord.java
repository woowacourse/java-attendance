package attendance.domain;

import java.util.List;

public class InfoLookupExpelRecord {
    private static final int CREW_NAME_INDEX = 0;
    private static final int ABSENT_COUNT_INDEX = 1;
    private static final int LATE_COUNT_INDEX = 2;
    private static final int EXPECTED_PENALTY_INDEX = 3;

    private final List<String> expelRecord;

    public InfoLookupExpelRecord(List<String> expelRecord) {
        this.expelRecord = expelRecord;
    }

    public String getCrewName() {
        return expelRecord.get(CREW_NAME_INDEX);
    }

    public String getAbsentCount() {
        return expelRecord.get(ABSENT_COUNT_INDEX);
    }

    public String getLateCount() {
        return expelRecord.get(LATE_COUNT_INDEX);
    }

    public String getExpectedPenalty() {
        return expelRecord.get(EXPECTED_PENALTY_INDEX);
    }
}
