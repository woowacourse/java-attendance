package attendance.model.domain.attendance;

import attendance.model.domain.attendance.vo.WarningCount;

public enum ManagementStatus {

    EXPULSION("제적"),
    COUNSELING("면담"),
    WARNING("경고"),
    NONE("일반");

    private final String name;

    ManagementStatus(String name) {
        this.name = name;
    }

    public static ManagementStatus from(WarningCount warningCount) {
        int policyAppliedAbsenceCount = warningCount.getPolicyAppliedAbsenceCount();
        if (policyAppliedAbsenceCount > 5) {
            return EXPULSION;
        }
        if (policyAppliedAbsenceCount > 2) {
            return COUNSELING;
        }
        if (policyAppliedAbsenceCount > 1) {
            return WARNING;
        }
        return NONE;
    }

    public boolean requiresManagement() {
        return this != NONE;
    }

    public String getName() {
        return name;
    }
}
