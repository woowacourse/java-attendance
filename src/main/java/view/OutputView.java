package view;


import dto.AttendanceRecordDto;
import dto.RiskOfExpulsionCrewDto;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm");
    private static final DateTimeFormatter EMPTY_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:--");

    public void printIntroduceAttendanceRecords(final String crewName) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crewName);
    }

    public void printAttendanceRecords(final List<AttendanceRecordDto> attendanceRecordDto) {
        attendanceRecordDto.forEach(this::printAttendanceRecord);
    }

    private void printAttendanceRecord(final AttendanceRecordDto attendanceRecordDto) {
        if (attendanceRecordDto.isEmpty()) {
            System.out.printf(attendanceRecordDto.dateTime().format(EMPTY_TIME_FORMATTER) + " (%s)\n",
                    attendanceRecordDto.attendanceStatus());
            return;
        }
        System.out.printf(
                attendanceRecordDto.dateTime().format(DATE_TIME_FORMATTER) + " (%s)\n",
                attendanceRecordDto.attendanceStatus());
    }

    public void printAttendanceStatistics(final Map<String, Integer> attendanceStatistics) {
        attendanceStatistics.entrySet()
                .stream()
                .forEach(entry -> System.out.printf("%s: %d회\n", entry.getKey(), entry.getValue()));
    }

    public void printRiskOfExpulsion(final String expulsionStatus) {
        System.out.printf("%s 대상자입니다.\n", expulsionStatus);
    }


    public void printRiskOfExpulsionCrews(final List<RiskOfExpulsionCrewDto> riskOfExpulsionCrewDtos) {
        System.out.println("제적 위험자 조회 결과");
        sortByAbsenceCountAndLateCount(riskOfExpulsionCrewDtos);
        riskOfExpulsionCrewDtos.forEach(this::printRiskOfExpulsionCrew);
    }

    private void sortByAbsenceCountAndLateCount(final List<RiskOfExpulsionCrewDto> riskOfExpulsionCrewDtos) {
        riskOfExpulsionCrewDtos.sort((c1, c2) -> {
            final int c1Value = c1.absenceCount() + c1.lateCount() / 3;
            final int c2Value = c2.absenceCount() + c2.lateCount() / 3;
            if (c1Value == c2Value) {
                return c1.crewName().compareTo(c2.crewName());
            }
            return Integer.compare(c2Value, c1Value);
        });
    }

    private void printRiskOfExpulsionCrew(final RiskOfExpulsionCrewDto riskOfExpulsionCrewDto) {
        System.out.printf("- %s: 결석 %d회, 지각 %d회, (%s)\n",
                riskOfExpulsionCrewDto.crewName(), riskOfExpulsionCrewDto.absenceCount(),
                riskOfExpulsionCrewDto.lateCount(), riskOfExpulsionCrewDto.expulsionStatus());
    }

}
