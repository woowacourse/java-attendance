package attendance.controller.dto;

import attendance.model.attendance.log.CrewAttendanceLog;

public class WarningCrewResponse {

    private final String crewNickName;
    private final int absenceCount;
    private final int lateCount;
    private final int policyAppliedAbsenceCount;
    private final int policyAppliedLateCount;
    private final String crewStatus;

    public WarningCrewResponse(
            String crewNickName,
            int absenceCount,
            int lateCount,
            int policyAppliedAbsenceCount,
            int policyAppliedLateCount,
            String crewStatus
    ) {

        this.crewNickName = crewNickName;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
        this.policyAppliedAbsenceCount = policyAppliedAbsenceCount;
        this.policyAppliedLateCount = policyAppliedLateCount;
        this.crewStatus = crewStatus;
    }

    public static WarningCrewResponse from(final CrewAttendanceLog crewAttendanceLog) {
        return new WarningCrewResponse(
                crewAttendanceLog.getCrewNickname(),
                crewAttendanceLog.getAbsenceCount(),
                crewAttendanceLog.getLateCount(),
                crewAttendanceLog.getPolicyAppliedAbsenceCount(),
                crewAttendanceLog.getPolicyAppliedLateCount(),
                crewAttendanceLog.getCrewStatus().getName()
        );
    }

    public String getCrewNickName() {
        return crewNickName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getPolicyAppliedAbsenceCount() {
        return policyAppliedAbsenceCount;
    }

    public int getPolicyAppliedLateCount() {
        return policyAppliedLateCount;
    }

    public String getCrewStatus() {
        return crewStatus;
    }
}
