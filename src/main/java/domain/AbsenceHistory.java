package domain;

import dto.AbsenceHistoryDto;
import dto.AttendanceHistoryDto;
import java.util.List;

public class AbsenceHistory {

    private final List<AttendanceHistoryDto> attendanceResultDtos;

    public AbsenceHistory(final List<AttendanceHistoryDto> attendanceResultDtos) {
        this.attendanceResultDtos = attendanceResultDtos;
    }

    public AbsenceHistoryDto calculate() {
        int attendance = attendanceCalculate();
        int lateness = lateCalculate();
        int absence = absenceCalculate();

        AbsencePolicy absenceStatus = AbsencePolicy.getAbsencePolicy(absence, lateness);
        return new AbsenceHistoryDto("이름??", lateness, absence, absenceStatus.getDescription());
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
