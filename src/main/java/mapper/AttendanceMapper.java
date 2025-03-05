package mapper;

import domain.AttendancePaper;
import domain.AttendanceRecord;
import dto.AttendanceDetails;
import dto.PenaltyCrew;
import java.util.List;

public class AttendanceMapper {

    private AttendanceMapper() {

    }

    public static List<AttendanceDetails> toAttendanceDetailsGroup(final List<AttendanceRecord> attendanceRecords) {
        return attendanceRecords.stream()
                .map(AttendanceMapper::toAttendanceDetails)
                .toList();
    }

    public static AttendanceDetails toAttendanceDetails(final AttendanceRecord attendanceRecord) {
        return AttendanceDetails.of(
                attendanceRecord.attendanceDate(),
                attendanceRecord.attendanceTime(),
                attendanceRecord.status()
        );
    }

    public static List<PenaltyCrew> toPenaltyCrews(final List<AttendancePaper> attendancePapers) {
        return attendancePapers.stream()
                .map(AttendanceMapper::toPenaltyCrew)
                .toList();
    }

    public static PenaltyCrew toPenaltyCrew(final AttendancePaper attendancePaper) {
        return PenaltyCrew.of(
                attendancePaper.getCrewName(),
                attendancePaper.countAttendanceStatus(),
                attendancePaper.calculatePenalty()
        );
    }

}
