package controller.dto;

import domain.attendance.AttendanceTypeCount;
import domain.crew.Crew;
import domain.attendance.PenaltyType;

public record AttendanceTypeCountDto(
        String nickname,
        int attendanceCount,
        int absenceCount,
        int lateCount,
        PenaltyType penaltyType
) {
    public static AttendanceTypeCountDto of(Crew crew, AttendanceTypeCount attendanceTypeCount,
                                            PenaltyType penaltyType) {
        return new AttendanceTypeCountDto(
                crew.getName(),
                attendanceTypeCount.getAttendanceCount(),
                attendanceTypeCount.getAbsenceCount(),
                attendanceTypeCount.getLateCount(),
                penaltyType
        );
    }
}
