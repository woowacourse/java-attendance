package attendance.dto;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRecord;
import attendance.model.AttendanceStatus;
import attendance.model.EducationDay;
import attendance.model.EducationSchedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public record AttendanceDto(
        String crewName,
        List<AttendanceDetailDto> attendanceDetailDTOs,
        String warningType,
        long attendanceCount,
        long lateCount,
        long absenceCount
) {

    public static final int DURING_YEAR = 2024;
    public static final int LAST_DAY = 31;

    public static AttendanceDto from(String crewName, AttendanceRecord attendanceRecord) {
        LocalDate now = LocalDate.of(2024, 12, 31);
        return new AttendanceDto(
                crewName,
                IntStream.range(1, computeLastAttendanceDate())
                        .filter(AttendanceDto::isDurationDay)
                        .mapToObj(day -> new AttendanceDate(LocalDate.of(2024, 12, day)))
                        .map(attendanceDate -> generateAttendanceDetailDTO(attendanceRecord, attendanceDate))
                        .toList(),
                attendanceRecord.computePanaltyUntil(now).name(),
                attendanceRecord.computeAttendanceCount(),
                attendanceRecord.computeLateCount(),
                attendanceRecord.computeAbsencesUntil(LocalDate.of(2024, 12, 31))
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

    private static boolean isDurationDay(int day) {
        return EducationDay.isDuringEducationDay(LocalDate.of(2024, 12, day));
    }

    private static int computeLastAttendanceDate() {
        LocalDate now = LocalDate.now();
        if (now.getYear() > DURING_YEAR) {
            return LAST_DAY;
        }
        return now.getDayOfMonth();
    }

    public record AttendanceDetailDto(
            LocalDate attendanceDate,
            LocalTime attendanceTime,
            String attendanceType
    ) {
        public static AttendanceDetailDto fromArriveAttendance(AttendanceDate attendanceDate,
                                                               LocalTime attendanceTime) {
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
