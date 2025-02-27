package view;


import dto.AttendanceRecordDto;
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

    public void printAttendanceStatistics(final Map<String, Integer> attendanceStatistics){
        attendanceStatistics.entrySet()
                .stream()
                .forEach(entry -> System.out.printf("%s: %d회\n", entry.getKey(), entry.getValue()));
    }

    public void printRiskOfExpulsion(final String expulsionStatus){
        System.out.printf("%s 대상자입니다.\n", expulsionStatus);
    }

}
