package attendance.model.domain.attendance;

import attendance.model.domain.attendance.vo.WarningCount;
import java.time.LocalDateTime;
import java.util.List;

public class Attendance {

    private final WarningCount warningCount;
    private final ManagementStatus managementStatus;

    public Attendance(final WarningCount warningCount, final ManagementStatus managementStatus) {
        this.warningCount = warningCount;
        this.managementStatus = managementStatus;
    }

    public static Attendance from(final List<LocalDateTime> dateTimes) {
        final WarningCount warningCount = WarningCount.from(dateTimes);
        return new Attendance(warningCount, ManagementStatus.from(warningCount));
    }

    public boolean requiresManagement() {
        return managementStatus.requiresManagement();
    }

    public int getPolicyAppliedAbsenceCount() {
        return warningCount.getPolicyAppliedAbsenceCount();
    }

    public int getPolicyAppliedLateCount() {
        return warningCount.getPolicyAppliedLateCount();
    }

    public int getAbsenceCount() {
        return warningCount.getAbsenceCount();
    }

    public int getLateCount() {
        return warningCount.getLateCount();
    }

    public String getManagementStatusName() {
        return managementStatus.getName();
    }
}
