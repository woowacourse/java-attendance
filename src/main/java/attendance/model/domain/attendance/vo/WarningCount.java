package attendance.model.domain.attendance.vo;

import java.time.LocalDateTime;
import java.util.List;

public class WarningCount {

    private final AbsenceCount absenceCount;
    private final LateCount lateCount;

    private WarningCount(AbsenceCount absenceCount, LateCount lateCount) {
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
    }

    public static WarningCount from(List<LocalDateTime> dateTimes) {
        AbsenceCount absenceCount = AbsenceCount.fromDateTimes(dateTimes);
        LateCount lateCount = LateCount.fromDateTimes(dateTimes);

        return new WarningCount(absenceCount, lateCount);
    }

    public int getPolicyAppliedAbsenceCount() {
        return absenceCount.getPolicyAppliedValue(lateCount);
    }

    public int getPolicyAppliedLateCount() {
        return lateCount.calculatePolicyValue();
    }

    public int getAbsenceCount() {
        return absenceCount.getValue();
    }

    public int getLateCount() {
        return lateCount.getValue();
    }
}
