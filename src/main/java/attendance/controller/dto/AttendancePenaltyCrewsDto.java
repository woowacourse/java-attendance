package attendance.controller.dto;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import java.util.List;
import java.util.Map;

public record AttendancePenaltyCrewsDto(
    List<AttendancePenaltyCrewDto> attendancePenaltyCrewDtos
) {

    public static AttendancePenaltyCrewsDto from(
        final List<AttendanceBook> attendanceBooks,
        final AttendanceDate untilDate
    ) {
        return new AttendancePenaltyCrewsDto(
            attendanceBooks.stream()
                .map(attendanceBook -> AttendancePenaltyCrewDto.from(
                    attendanceBook, untilDate))
                .toList()
        );
    }

    public record AttendancePenaltyCrewDto(
        Crew crew,
        int absenceCount,
        int lateCount,
        AttendancePenalty attendancePenalty
    ) {

        public static AttendancePenaltyCrewDto from(
            final AttendanceBook attendanceBook,
            final AttendanceDate untilDate
        ) {
            final Map<AttendanceStatus, Integer> attendanceStatusCount = AttendanceStatus.from(
                attendanceBook.retrieveOrderByDateTimeUntilDate(untilDate));
            final AttendancePenalty attendancePenalty = AttendancePenalty.from(
                attendanceStatusCount);

            return new AttendancePenaltyCrewDto(
                attendanceBook.getCrew(),
                attendanceStatusCount.getOrDefault(AttendanceStatus.ABSENCE, 0),
                attendanceStatusCount.getOrDefault(AttendanceStatus.LATE, 0),
                attendancePenalty
            );
        }
    }
}
