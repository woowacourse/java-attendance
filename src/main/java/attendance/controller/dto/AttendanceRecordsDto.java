package attendance.controller.dto;


import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record AttendanceRecordsDto(
    Crew crew,
    List<AttendanceRecordDto> attendanceRecordDtos,
    int attendanceCount,
    int lateCount,
    int absenceCount,
    AttendancePenalty attendancePenalty
) {
    public static AttendanceRecordsDto from(
        final AttendanceBook attendanceBook,
        final AttendanceDate untilDate
    ) {
        final List<AttendanceDateTime> attendanceDateTimes = attendanceBook.retrieveOrderByDateTimeUntilDate(
            untilDate);

        final List<AttendanceRecordDto> attendanceRecords = createAttendanceRecordDtos(
            attendanceDateTimes);
        final Map<AttendanceStatus, Integer> attendanceStatusCount = AttendanceStatus.from(
            attendanceDateTimes);
        final AttendancePenalty attendancePenalty = AttendancePenalty.from(
            attendanceStatusCount);

        return new AttendanceRecordsDto(
            attendanceBook.getCrew(),
            attendanceRecords,
            attendanceStatusCount.getOrDefault(AttendanceStatus.ATTENDANCE, 0),
            attendanceStatusCount.getOrDefault(AttendanceStatus.LATE, 0),
            attendanceStatusCount.getOrDefault(AttendanceStatus.ABSENCE, 0),
            attendancePenalty
        );
    }

    private static List<AttendanceRecordDto> createAttendanceRecordDtos(final List<AttendanceDateTime> attendanceDateTimes) {
        final List<AttendanceRecordDto> attendanceRecords = new ArrayList<>();

        attendanceDateTimes.forEach(workDateTime ->
            attendanceRecords.add(new AttendanceRecordDto(
                workDateTime,
                AttendanceStatus.from(workDateTime))));

        return attendanceRecords;
    }

    public record AttendanceRecordDto(
        AttendanceDateTime attendanceDateTime,
        AttendanceStatus attendanceStatus
    ) {
    }
}
