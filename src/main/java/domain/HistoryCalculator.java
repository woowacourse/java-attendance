package domain;

import dto.AbsenceHistoryDto;
import dto.AbsenceRecordDto;
import dto.AttendanceHistoryDto;
import dto.AttendanceRecord;
import dto.AttendanceStatus;
import java.util.ArrayList;
import java.util.List;

public class HistoryCalculator {

    private final List<AttendanceHistoryDto> attendanceResultDtos;

    public HistoryCalculator(final List<AttendanceHistoryDto> attendanceResultDtos) {
        this.attendanceResultDtos = attendanceResultDtos;
    }

    public static AttendanceStatus calculateAttendanceRecordBy(List<AttendanceRecord> attendanceRecords) {
        AbsenceHistoryDto absenceHistoryDto = calculateAbsenceHistory(attendanceRecords);
        AbsencePolicy absencePolicy = calculateAbsencePolicy(absenceHistoryDto);
        return new AttendanceStatus(absenceHistoryDto, absencePolicy);
    }

    private static AbsenceHistoryDto calculateAbsenceHistory(List<AttendanceRecord> attendanceRecords) {
        int attendance = 0;
        int lateness = 0;
        int absence = 0;
        for (AttendanceRecord attendanceRecord : attendanceRecords) {
            AttendanceState state = attendanceRecord.time().state();
            if (state.equals(AttendanceState.ATTENDANCE)) {
                attendance++;
            }
            if (state.equals(AttendanceState.LATENESS)) {
                lateness++;
            }
            if (state.equals(AttendanceState.ABSENCE)) {
                absence++;
            }
        }
        return new AbsenceHistoryDto(attendance, lateness, absence);
    }

    private static AbsencePolicy calculateAbsencePolicy(AbsenceHistoryDto absenceHistoryDto) {
        return AbsencePolicy.getAbsencePolicy(absenceHistoryDto.lateness(), absenceHistoryDto.absence());
    }

    public static List<AbsenceRecordDto> calculateAbsenceRecordBy(Attendance attendance) {
        List<AbsenceRecordDto> absenceRecordDtos = new ArrayList<>();
        attendance.getAttendanceMap()
                .forEach((crew, attendanceRecords) -> {
                    AttendanceStatus attendanceStatus = calculateAttendanceRecordBy(attendanceRecords);
                    absenceRecordDtos.add(new AbsenceRecordDto(crew, attendanceStatus.absenceHistory().lateness(),
                            attendanceStatus.absenceHistory().absence(), attendanceStatus.absencePolicy()));
                });
        return absenceRecordDtos;
    }
}
