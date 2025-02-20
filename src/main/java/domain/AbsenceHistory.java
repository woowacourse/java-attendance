package domain;

import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.util.List;

public class AbsenceHistory {

    private final List<AttendanceResultDto> attendanceResultDtos;

    public AbsenceHistory(final List<AttendanceResultDto> attendanceResultDtos) {
        this.attendanceResultDtos = attendanceResultDtos;
    }

    public AbsenceResultDto calculate() {
        int attendance = attendanceCalculate();
        int lateness = lateCalculate();
        int absence = absenceCalculate();

        String absenceStatus = AbsencePolicy.getAbsencePolicy(absence, lateness);
        return new AbsenceResultDto(attendance, lateness, absence, absenceStatus);
    }

    private int lateCalculate() {
        return calculateAbsence(AttendanceState.LATENESS);
    }

    private int absenceCalculate() {
        return calculateAbsence(AttendanceState.ABSENCE);
    }

    private int attendanceCalculate() {
        return calculateAbsence(AttendanceState.ATTENDANCE);
    }

    private int calculateAbsence(AttendanceState state) {
        return (int) attendanceResultDtos.stream().filter(dto -> dto.attendanceState().equals(state.getDescription()))
                .count();
    }
}
