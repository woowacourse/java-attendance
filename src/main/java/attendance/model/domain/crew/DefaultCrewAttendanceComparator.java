package attendance.model.domain.crew;

import attendance.model.domain.attendance.CrewAttendance;

public class DefaultCrewAttendanceComparator implements CrewAttendanceComparator {

    @Override
    public int compare(CrewAttendance o1, CrewAttendance o2) {
        if (o1.getPolicyAppliedAbsenceCount() == o2.getPolicyAppliedAbsenceCount()) {
            if (o1.getPolicyAppliedLateCount() == o2.getPolicyAppliedLateCount()) {
                return o1.getCrewName().compareTo(o2.getCrewName());
            }
            return o2.getPolicyAppliedLateCount() - o1.getPolicyAppliedLateCount();
        }
        return o2.getPolicyAppliedAbsenceCount() - o1.getPolicyAppliedAbsenceCount();
    }
}
