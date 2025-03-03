package domain;

import static util.Constants.ERROR_HEADER;

import dto.AttendanceCount;
import dto.AttendanceHistory;
import dto.AttendanceLog;
import dto.InitialInformation;
import dto.ModifyingResult;
import dto.PenaltyCrewsInformation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private static final String NAME_NOT_EXISTED_ERROR = "존재하지 않는 닉네임입니다.";

    private final Map<CrewName, AttendanceRecord> attendanceBook;

    public AttendanceBook(InitialInformation initialInformation) {
        this.attendanceBook = initialInformation.initialInformation();
    }

    public Attendance addAttendance(CrewName crewName, Attendance attendance) {
        findAttendanceRecordBy(crewName).add(attendance);
        return attendance;
    }

    public ModifyingResult modify(CrewName crewName, Attendance newAttendance) {
        return findAttendanceRecordBy(crewName).modify(newAttendance);
    }

    public AttendanceRecord findAttendanceRecordBy(CrewName crewName) {
        AttendanceRecord attendanceRecord = attendanceBook.get(crewName);
        if (attendanceRecord == null) {
            throw new IllegalArgumentException(ERROR_HEADER + NAME_NOT_EXISTED_ERROR);
        }
        return attendanceRecord;
    }

    public AttendanceHistory findAttendanceHistoryUntilYesterday(CrewName crewName) {
        return new AttendanceHistory(
                findAttendanceLogUntilYesterday(crewName),
                findCountUntilYesterday(crewName)
        );
    }

    private AttendanceLog findAttendanceLogUntilYesterday(CrewName crewName) {
        return findAttendanceRecordBy(crewName).findAllSortedUntilYesterday();
    }

    private AttendanceCount findCountUntilYesterday(CrewName crewName) {
        AttendanceRecord attendanceRecord = findAttendanceRecordBy(crewName);
        return new AttendanceCount(
                crewName,
                attendanceRecord.countByAttendanceStatus(AttendanceStatus.ATTEND),
                attendanceRecord.countByAttendanceStatus(AttendanceStatus.LATE),
                attendanceRecord.countByAttendanceStatus(AttendanceStatus.ABSENT),
                attendanceRecord.countConsideredAbsent()
        );
    }

    public PenaltyCrewsInformation findPenaltyCrewsSortedUntilYesterday() {
        List<AttendanceCount> penaltyInformation = new ArrayList<>();
        for (CrewName crewName : attendanceBook.keySet()) {
            penaltyInformation.add(findCountUntilYesterday(crewName));
        }
        penaltyInformation.sort(
                Comparator.comparingInt(AttendanceCount::consideredAbsentCount)
                        .reversed()
                        .thenComparing(AttendanceCount::crewName)
        );
        return new PenaltyCrewsInformation(penaltyInformation);
    }
}
