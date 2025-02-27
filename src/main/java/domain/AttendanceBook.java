package domain;

import dto.AttendanceCount;
import dto.AttendanceHistory;
import dto.InitialInformation;
import dto.ModifyResult;
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
        AttendanceRecord attendanceRecord = findAttendanceRecordBy(crewName);
        if(attendanceRecord.contains(attendance)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 수정 메뉴를 이용해주세요.");
        }
        attendanceRecord.add(attendance);
        value.put(crewName, attendanceRecord);
        return attendance;
    }

    public AttendanceRecord findAttendanceRecordBy(CrewName crewName) {
        AttendanceRecord attendanceRecord = value.get(crewName);
        if (attendanceRecord == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 닉네임입니다.");
        }
        return attendanceRecord;
    }

    public ModifyResult modify(CrewName crewName, Attendance newAttendance) {
        return findAttendanceRecordBy(crewName).modify(newAttendance);
    }

    public AttendanceHistory findAttendanceHistoryUntil(CrewName crewName, LocalDate yesterday) {
        AttendanceRecord attendanceRecord = findAttendanceRecordBy(crewName);
        return attendanceRecord.findAllSortedUntil(yesterday);
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

    public PenaltyInformation findPenaltyCrewSorted(LocalDate yesterday) {
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
