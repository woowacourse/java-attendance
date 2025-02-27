package attendance.view;

import attendance.dto.AttendanceDto;
import attendance.dto.AttendanceDto.AttendanceDetailDto;
import attendance.dto.WarningCrewsDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;

public class OutputView {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final Map<String, String> ATTENDANCE_TYPE = Map.of(
            "ATTEND", "출석",
            "LATE", "지각",
            "ABSENCE", "결석"
    );
    private static final Map<String, String> PANALTY_TYPE = Map.of(
            "WARN", "경고",
            "INTERVIEW", "면담",
            "DISMISSAL", "제적"
    );

    public void printAttendanceHistory(AttendanceDto attendanceDto) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        stringBuilder.appendLine(String.format("이번 달 %s의 출석 기록입니다.", attendanceDto.crewName()));
        attendanceDto.attendanceDetailDTOs().stream()
                .sorted(Comparator.comparing(AttendanceDetailDto::attendanceDate))
                .forEach(attendanceDetail -> stringBuilder.appendLine(generateAttendanceDetail(
                        attendanceDetail.attendanceDate(),
                        attendanceDetail.attendanceTime(),
                        attendanceDetail.attendanceType()
                )));
        stringBuilder.appendLine(String.format("출석: %d회", attendanceDto.attendanceCount()));
        stringBuilder.appendLine(String.format("지각: %d회", attendanceDto.lateCount()));
        stringBuilder.appendLine(String.format("결석: %d회", attendanceDto.absenceCount()));
        stringBuilder.appendLine(String.format("%s 대상자입니다.", PANALTY_TYPE.get(attendanceDto.warningType())));
        stringBuilder.print();
    }

    public void printAttendanceDetail(AttendanceDetailDto attendanceDetailDto) {
        System.out.println(generateAttendanceDetail(
                attendanceDetailDto.attendanceDate(),
                attendanceDetailDto.attendanceTime(),
                attendanceDetailDto.attendanceType()
        ));
    }

    public void printModifyResult(AttendanceDetailDto before, AttendanceDetailDto after) {
        String beforeDetail = generateAttendanceDetail(
                before.attendanceDate(),
                before.attendanceTime(),
                before.attendanceType()
        );
        String afterDetail = generateAttendanceDetail(
                after.attendanceDate(),
                after.attendanceTime(),
                after.attendanceType()
        );
        System.out.println(String.format("%s -> %s 수정 완료!", beforeDetail, afterDetail));
    }

    private String generateAttendanceDetail(
            LocalDate localDate,
            LocalTime localTime,
            String attendanceType
    ) {
        String date = localDate.format(dateFormatter);
        String time = "--:--";
        if (localTime != null) {
            time = localTime.format(timeFormatter);
        }
        return String.format("%s %s (%s)", date, time, ATTENDANCE_TYPE.get(attendanceType));
    }

    public void printWarningCrews(WarningCrewsDto warningCrewsDTO) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        stringBuilder.appendLine("제적 위험자 조회 결과");
        warningCrewsDTO.warningCrewDetailDTO().stream()
                .sorted((o1, o2) -> Long.compare(o2.convertLateCount(), o1.convertLateCount()))
                .forEach(warningCrewDetailDTO -> stringBuilder.appendLine(String.format("- %s: 결석 %d회, 지각: %d회 (%s)",
                        warningCrewDetailDTO.crewName(),
                        warningCrewDetailDTO.absenceCount(),
                        warningCrewDetailDTO.lateCount(),
                        PANALTY_TYPE.get(warningCrewDetailDTO.warningType())
                )));
        stringBuilder.print();
    }

    public void printError(String message) {
        System.out.println(String.format("[ERROR] %s", message));
    }
}
