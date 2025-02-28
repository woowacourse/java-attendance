package domain;

import dto.AttendanceCount;
import dto.AttendanceLog;
import dto.InitialInformation;
import dto.ModifyingResult;
import dto.PenaltyInformation;
import java.time.LocalDate;
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

    public AttendanceLog findAttendanceLogUntil(CrewName crewName, LocalDate yesterday) {
        return findAttendanceRecordBy(crewName).findAllSortedUntil(yesterday);
    }

    public AttendanceCount findCountUntil(CrewName crewName, LocalDate yesterday) {
        AttendanceRecord attendanceRecord = findAttendanceRecordBy(crewName);
        return new AttendanceCount(
                crewName,
                attendanceRecord.calculateCountOf(AttendanceStatus.ATTEND, yesterday),
                attendanceRecord.calculateCountOf(AttendanceStatus.LATE, yesterday),
                attendanceRecord.calculateCountOf(AttendanceStatus.ABSENT, yesterday),
                attendanceRecord.calculateConsideredAbsentCount(yesterday)
        );
    }

    public PenaltyInformation findPenaltyCrewsSortedUntil(LocalDate yesterday) {
        List<AttendanceCount> penaltyInformation = new ArrayList<>();
        for (CrewName crewName : value.keySet()) {
            penaltyInformation.add(findCountUntil(crewName, yesterday));
        }
        penaltyInformation.sort(
                Comparator.comparingInt(AttendanceCount::consideredAbsentCount)
                        .reversed()
                        .thenComparing(AttendanceCount::crewName)
        );
        return new PenaltyInformation(penaltyInformation);
    }
}
