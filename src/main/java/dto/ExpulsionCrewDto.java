package dto;

import domain.AttendanceAnalyze;
import domain.Username;

public record ExpulsionCrewDto(
        String username,
        int lateCount,
        int absenceCount,
        String result
) {
    public static ExpulsionCrewDto of(Username username, AttendanceAnalyze analyze) {
        return new ExpulsionCrewDto(username.getName(), analyze.getLateCount(), analyze.getAbsenceCount(),
                analyze.getAttendanceStatus().getStatus());
    }
}
