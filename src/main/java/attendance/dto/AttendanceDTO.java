package attendance.dto;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRecord;
import attendance.model.AttendanceStatus;
import attendance.model.EducationDay;
import attendance.model.EducationSchedule;
import attendance.model.SystemDuration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public record AttendanceDto(
        String crewName,
        List<AttendanceDetailDto> attendanceDetailDTOs,
        String warningType,
        long attendanceCount,
        long lateCount,
        long absenceCount
) {

    public static AttendanceDto from(String crewName, AttendanceRecord attendanceRecord) {
        LocalDate now = SystemDuration.getNow();
        return new AttendanceDto(
                crewName,
                Stream.iterate(
                                SystemDuration.startDate,
                                date -> date.isBefore(SystemDuration.computeLastAttendanceDate(now).plusDays(1)),
                                date -> date.plusDays(1))
                        .filter(EducationDay::isDuringEducationDay)
                        .map(AttendanceDate::new)
                        .map(attendanceDate -> generateAttendanceDetailDTO(attendanceRecord, attendanceDate))
                        .toList(),
                attendanceRecord.computePanaltyUntil(now).name(),
                attendanceRecord.computeAttendanceCount(),
                attendanceRecord.computeLateCount(),
                attendanceRecord.computeAbsencesUntil(now)
        );
    }

    private static AttendanceDetailDto generateAttendanceDetailDTO(
            AttendanceRecord attendanceRecord,
            AttendanceDate attendanceDate
    ) {
        if (attendanceRecord.containsAttendanceDateTimeByDate(attendanceDate)) {
            AttendanceDateTime attendanceDateTime = attendanceRecord.findAttendanceByDate(attendanceDate);
            return AttendanceDetailDto.fromArriveAttendance(
                    attendanceDateTime.getAttendanceDate(),
                    attendanceDateTime.getAttendanceTime()
            );
        }
        return AttendanceDetailDto.fromNonArriveAttendance(attendanceDate.date());
    }

    public record AttendanceDetailDto(
            LocalDate attendanceDate,
            LocalTime attendanceTime,
            String attendanceType
    ) {
        public static AttendanceDetailDto fromArriveAttendance(
                AttendanceDate attendanceDate,
                LocalTime attendanceTime
        ) {
            return new AttendanceDetailDto(
                    attendanceDate.date(),
                    attendanceTime,
                    AttendanceStatus.from(
                            attendanceTime,
                            EducationSchedule.from(attendanceDate.date())
                    ).name()
            );
        }

        public static AttendanceDetailDto fromArriveAttendance(AttendanceDateTime attendanceDateTime) {
            return fromArriveAttendance(attendanceDateTime.getAttendanceDate(), attendanceDateTime.getAttendanceTime());
        }

        public static AttendanceDetailDto fromNonArriveAttendance(LocalDate localDate) {
            return new AttendanceDetailDto(
                    localDate,
                    null,
                    AttendanceStatus.ABSENCE.name()
            );
        }
    }

}
