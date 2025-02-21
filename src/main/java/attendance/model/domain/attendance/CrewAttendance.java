package attendance.model.domain.attendance;

import attendance.model.domain.crew.Crew;
import java.time.LocalDateTime;
import java.util.List;

public class CrewAttendance {

    private final Crew crew;
    private final Attendance attendance;

    private CrewAttendance(final Crew crew, final Attendance attendance) {
        this.crew = crew;
        this.attendance = attendance;
    }

    public static CrewAttendance of(final Crew crew, final List<LocalDateTime> dateTimes) {
        return new CrewAttendance(crew, Attendance.from(dateTimes));
    }

    public boolean requiresManagement() {
        return attendance.requiresManagement();
    }

    public int getPolicyAppliedAbsenceCount() {
        return attendance.getPolicyAppliedAbsenceCount();
    }

    public int getPolicyAppliedLateCount() {
        return attendance.getPolicyAppliedLateCount();
    }

    public String getCrewName() {
        return crew.getName();
    }

    public int getAbsenceCount() {
        return attendance.getAbsenceCount();
    }

    public int getLateCount() {
        return attendance.getLateCount();
    }

    public String getManagementStatusName() {
        return attendance.getManagementStatusName();
    }
}
