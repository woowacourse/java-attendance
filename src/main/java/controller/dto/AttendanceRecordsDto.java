package controller.dto;

import domain.AttendanceStatus;
import domain.CrewAttendance;
import domain.Penalty;
import domain.WorkDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record AttendanceRecordsDto(
        String name,
        List<AttendanceRecordDto> attendanceRecords,
        int attendanceCount,
        int absenceCount,
        int perceptionCount,
        String penaltyName
) {
    public static AttendanceRecordsDto from(CrewAttendance crewAttendance) {
        List<WorkDateTime> workDateTimes = crewAttendance.retrieveAttendanceOrderByDate();

        List<AttendanceRecordDto> attendanceRecords = createAttendanceRecords(workDateTimes);
        Map<AttendanceStatus, Integer> attendanceStatusCount = AttendanceStatus.calculateAttendanceStatusCount(
                workDateTimes);
        Penalty penalty = Penalty.from(attendanceStatusCount);

        return new AttendanceRecordsDto(
                crewAttendance.getCrew().getName(),
                attendanceRecords,
                attendanceStatusCount.getOrDefault(AttendanceStatus.ATTENDANCE, 0),
                attendanceStatusCount.getOrDefault(AttendanceStatus.ABSENCE, 0),
                attendanceStatusCount.getOrDefault(AttendanceStatus.PERCEPTION, 0),
                penalty.getName()
        );
    }

    private static List<AttendanceRecordDto> createAttendanceRecords(List<WorkDateTime> workDateTimes) {
        List<AttendanceRecordDto> attendanceRecords = new ArrayList<>();
        workDateTimes.forEach(workDateTime ->
                attendanceRecords.add(
                        new AttendanceRecordDto(workDateTime, AttendanceStatus.from(workDateTime).getName()))
        );
        return attendanceRecords;
    }

    public record AttendanceRecordDto(
            WorkDateTime workDateTime,
            String attendanceStatusName
    ) {
    }
}
