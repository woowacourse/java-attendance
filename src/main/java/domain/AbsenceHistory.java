package domain;

import dto.AttendanceResultDto;
import java.util.List;

public record AbsenceHistory(int attendance, int lateness, int absence, AbsencePolicy status) {

    public static AbsenceHistory calculate(final List<AttendanceResultDto> attendanceResultDtos) {
        int attendance = attendanceCalculate(attendanceResultDtos);
        int lateness = lateCalculate(attendanceResultDtos);
        int absence = absenceCalculate(attendanceResultDtos);

        AbsencePolicy absenceStatus = AbsencePolicy.getAbsencePolicy(absence, lateness);
        return new AbsenceHistory(attendance, lateness, absence, absenceStatus);
    }

    private static int lateCalculate(final List<AttendanceResultDto> attendanceResultDtos) {
        return calculateAbsence(attendanceResultDtos, AttendanceState.LATENESS);
    }

    private static int absenceCalculate(final List<AttendanceResultDto> attendanceResultDtos) {
        return calculateAbsence(attendanceResultDtos, AttendanceState.ABSENCE);
    }

    private static int attendanceCalculate(final List<AttendanceResultDto> attendanceResultDtos) {
        return calculateAbsence(attendanceResultDtos, AttendanceState.ATTENDANCE);
    }

    private static int calculateAbsence(final List<AttendanceResultDto> attendanceResultDtos, AttendanceState state) {
        return (int) attendanceResultDtos.stream()
                .filter(dto -> dto.isSame(state))
                .count();
    }
}
