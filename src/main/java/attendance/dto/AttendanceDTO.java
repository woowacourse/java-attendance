package attendance.dto;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceHistory;
import attendance.model.WoowaDurationTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

public record AttendanceDTO(
        String crewName,
        List<AttendanceDetailDTO> attendanceDetailDTOs,
        String warningType,
        long attendanceCount,
        long lateCount,
        long absenceCount
) {
    public static AttendanceDTO from(String crewName, AttendanceHistory attendanceHistory) {
        LocalDate attendanceStartDate = LocalDate.of(2024, 12, 1);
        LocalDate attendanceEndDate = attendanceHistory.computeLastAttendableDate();
        return new AttendanceDTO(
                crewName,
                IntStream.range(1, (int) (ChronoUnit.DAYS.between(attendanceStartDate, attendanceEndDate) + 2))
                        .filter(day -> WoowaDurationTime.isDurationDay(LocalDate.of(2024, 12, day)))
                        .mapToObj(day -> {
                            AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, day));
                            if (attendanceHistory.containsAttendance(attendanceDate)) {
                                return AttendanceDetailDTO.fromArriveAttendance(
                                        attendanceHistory.findAttendanceDateTime(attendanceDate)
                                );
                            }
                            return AttendanceDetailDTO.fromNonArriveAttendance(LocalDate.of(2024, 12, day));
                        }).toList(),
                attendanceHistory.getAttendanceWarning().name(),
                attendanceHistory.computeAttendanceCount(),
                attendanceHistory.computeLateCount(),
                attendanceHistory.computeAbsenceCount()
        );
    }

    public record AttendanceDetailDTO(
            LocalDate attendanceDate,
            LocalTime attendanceTime,
            String attendanceType
    ) {
        public static AttendanceDetailDTO fromArriveAttendance(AttendanceDateTime attendanceDateTime) {
            return new AttendanceDetailDTO(
                    attendanceDateTime.getAttendanceDate().localDate(),
                    attendanceDateTime.getAttendanceTime().localTime(),
                    attendanceDateTime.getAttendanceType().name()
            );
        }

        public static AttendanceDetailDTO fromNonArriveAttendance(LocalDate localDate) {
            return new AttendanceDetailDTO(
                    localDate,
                    null,
                    "결석"
            );
        }
    }

}
