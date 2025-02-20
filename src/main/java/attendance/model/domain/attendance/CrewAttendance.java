package attendance.model.domain.attendance;

import attendance.model.domain.attendance.vo.WarningCount;
import attendance.model.domain.crew.Crew;
import java.time.LocalDateTime;
import java.util.List;

public class CrewAttendance {

    private final Crew crew;
    private final WarningCount warningCount;
    private final ManagementStatus managementStatus;

    private CrewAttendance(
            Crew crew,
            WarningCount warningCount,
            ManagementStatus managementStatus
    ) {

        this.crew = crew;
        this.warningCount = warningCount;
        this.managementStatus = managementStatus;
    }

    public static CrewAttendance of(Crew crew, List<LocalDateTime> dateTimes) {
        WarningCount warningCount = WarningCount.from(dateTimes);
        return new CrewAttendance(crew, warningCount, ManagementStatus.from(warningCount));
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

    public String getCrewName() {
        return crew.getName();
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
