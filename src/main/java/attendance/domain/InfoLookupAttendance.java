package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class InfoLookupAttendance {
    private static final int SAFE_COUNT_INDEX = 0;
    private static final int LATE_COUNT_INDEX = 1;
    private static final int ABSENT_COUNT_INDEX = 2;
    private static final int PENALTY_STATUS_INDEX = 3;

    private final String crewName;
    private final List<List<String>> crewAttendanceRecord;
    private final List<String> crewStatisticStatus;

    private final List<InfoCheckAttendance> crewAttendanceRecords = new ArrayList<>();

    public InfoLookupAttendance(String crewName, List<List<String>> crewAttendanceRecord,
                                List<String> crewStatisticStatus) {
        this.crewName = crewName;
        this.crewAttendanceRecord = crewAttendanceRecord;
        this.crewStatisticStatus = crewStatisticStatus;
    }

    public void createCrewAttendanceRecords() {
        for (List<String> attendanceRecord : crewAttendanceRecord) {
            crewAttendanceRecords.add(new InfoCheckAttendance(attendanceRecord));
        }
    }

    public String getCrewName() {
        return crewName;
    }

    public List<InfoCheckAttendance> getCrewAttendanceRecords() {
        return crewAttendanceRecords;
    }

    public String getCrewStatisticSafe() {
        return crewStatisticStatus.get(SAFE_COUNT_INDEX);
    }

    public String getCrewStatisticLate() {
        return crewStatisticStatus.get(LATE_COUNT_INDEX);
    }

    public String getCrewStatisticAbsent() {
        return crewStatisticStatus.get(ABSENT_COUNT_INDEX);
    }

    public String getCrewStatisticPenalty() {
        return crewStatisticStatus.get(PENALTY_STATUS_INDEX);
    }
}
