package attendance.domain;

import java.util.Comparator;

public class CrewAttendanceStatus {

    private final String crewName;
    private final long absent;
    private final long late;

    public CrewAttendanceStatus(String crewName, long absent, long late) {
        this.crewName = crewName;
        this.absent = absent;
        this.late = late;
    }

    public String getCrewName() {
        return crewName;
    }

    public long getAbsent() {
        return absent;
    }

    public long getLate() {
        return late;
    }

    private long getTotalIssueCount() {
        return absent + late;
    }

    public static Comparator<CrewAttendanceStatus> createSortingComparator() {
        return Comparator
                .comparingLong(CrewAttendanceStatus::getTotalIssueCount).reversed()
                .thenComparing(CrewAttendanceStatus::getCrewName);
    }
}
