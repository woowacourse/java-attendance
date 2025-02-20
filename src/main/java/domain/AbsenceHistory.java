package domain;

import dto.AttendanceResultDto;
import java.util.List;

public class AbsenceHistory {

    private final List<AttendanceResultDto> attendanceResultDtos;

    public AbsenceHistory(final List<AttendanceResultDto> attendanceResultDtos) {
        this.attendanceResultDtos = attendanceResultDtos;
    }

    public int lateCalculate() {
        return calculateAbsence(AttendanceState.LATENESS);
    }

    public int absenceCalculate() {
        return calculateAbsence(AttendanceState.ABSENCE);
    }

    public int attendanceCalculate() {
        return calculateAbsence(AttendanceState.ATTENDANCE);
    }

    private int calculateAbsence(AttendanceState state) {
        return (int) attendanceResultDtos.stream()
                .filter(dto -> dto.attendanceState().equals(state.getDescription()))
                .count();
    }
}
