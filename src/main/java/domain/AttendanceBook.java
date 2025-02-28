package domain;

import dto.AttendanceCount;
import dto.AttendanceLog;
import dto.InitialInformation;
import dto.ModifyingResult;
import dto.PenaltyInformation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<CrewName, AttendanceRecord> value;

    public AttendanceBook(InitialInformation initialInformation) {
        this.value = initialInformation.value();
    }

    public Attendance addAttendance(CrewName crewName, Attendance attendance) {
        findAttendanceRecordBy(crewName).add(attendance);
        return attendance;
    }

    public boolean isNotExistedName(CrewName crewName) {
        AttendanceRecord attendanceRecord = value.get(crewName);
        return attendanceRecord == null;
    }

    public AttendanceRecord findAttendanceRecordBy(CrewName crewName) {
        return value.get(crewName);
    }

    public ModifyingResult modify(CrewName crewName, Attendance newAttendance) {
        return findAttendanceRecordBy(crewName).modify(newAttendance);
    }

    public AttendanceLog findAttendanceLogUntilYesterday(CrewName crewName) {
        return findAttendanceRecordBy(crewName).findAllSortedUntilYesterday();
    }

    public AttendanceCount findCountUntilYesterday(CrewName crewName) {
        AttendanceRecord attendanceRecord = findAttendanceRecordBy(crewName);
        return new AttendanceCount(
                crewName,
                attendanceRecord.calculateCountOf(AttendanceStatus.ATTEND),
                attendanceRecord.calculateCountOf(AttendanceStatus.LATE),
                attendanceRecord.calculateCountOf(AttendanceStatus.ABSENT),
                attendanceRecord.calculateConsideredAbsentCount()
        );
    }

    public PenaltyInformation findPenaltyCrewsSortedUntilYesterday() {
        List<AttendanceCount> penaltyInformation = new ArrayList<>();
        for (CrewName crewName : value.keySet()) {
            penaltyInformation.add(findCountUntilYesterday(crewName));
        }
        penaltyInformation.sort(
                Comparator.comparingInt(AttendanceCount::consideredAbsentCount)
                        .reversed()
                        .thenComparing(AttendanceCount::crewName)
        );
        return new PenaltyInformation(penaltyInformation);
    }
}
