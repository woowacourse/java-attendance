package controller.dto;

import domain.AttendanceStatus;
import domain.CrewAttendance;
import domain.Penalty;
import java.util.List;
import java.util.Map;

public record PenaltyRecordsDto(
        List<PenaltyRecord> penaltyRecords
) {
    public static PenaltyRecordsDto from(List<CrewAttendance> crewAttendances) {
        List<PenaltyRecord> records = crewAttendances.stream()
                .map(PenaltyRecord::from)
                .toList();

        return new PenaltyRecordsDto(records);
    }

    public record PenaltyRecord(
            String name,
            int absenceCount,
            int perceptionCount,
            String penaltyName
    ) {
        public static PenaltyRecord from(CrewAttendance crewAttendance) {
            Map<AttendanceStatus, Integer> attendanceStatusCount = AttendanceStatus.calculateAttendanceStatusCount(
                    crewAttendance.retrieveAttendanceOrderByDate()
            );
            Penalty penalty = Penalty.from(attendanceStatusCount);

            return new PenaltyRecord(
                    crewAttendance.getCrew().getName(),
                    attendanceStatusCount.getOrDefault(AttendanceStatus.ABSENCE, 0),
                    attendanceStatusCount.getOrDefault(AttendanceStatus.PERCEPTION, 0),
                    penalty.getName()
            );
        }
    }
}
