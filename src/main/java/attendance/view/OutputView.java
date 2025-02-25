package attendance.view;

import attendance.dto.AttendanceDTO;
import attendance.dto.AttendanceDTO.AttendanceDetailDTO;
import attendance.dto.WarningCrewsDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class OutputView {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public void printAttendanceHistory(AttendanceDTO attendanceDTO) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        stringBuilder.appendLine(String.format("이번 달 %s의 출석 기록입니다.", attendanceDTO.crewName()));
        attendanceDTO.attendanceDetailDTOs().stream()
                .sorted(Comparator.comparing(AttendanceDetailDTO::attendanceDate))
                .forEach(attendanceDetail -> stringBuilder.appendLine(generateAttendanceDetail(
                        attendanceDetail.attendanceDate(),
                        attendanceDetail.attendanceTime(),
                        attendanceDetail.attendanceType()
                )));
        stringBuilder.appendLine(String.format("출석: %d회", attendanceDTO.attendanceCount()));
        stringBuilder.appendLine(String.format("지각: %d회", attendanceDTO.lateCount()));
        stringBuilder.appendLine(String.format("결석: %d회", attendanceDTO.absenceCount()));
        stringBuilder.appendLine(String.format("%s 대상자입니다.", attendanceDTO.warningType()));
        stringBuilder.print();
    }

    public void printAttendanceDetail(AttendanceDetailDTO attendanceDetailDTO) {
        System.out.println(generateAttendanceDetail(
                attendanceDetailDTO.attendanceDate(),
                attendanceDetailDTO.attendanceTime(),
                attendanceDetailDTO.attendanceType()
        ));
    }

    public void printModifyResult(AttendanceDetailDTO before, AttendanceDetailDTO after) {
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
        return String.format("%s %s (%s)", date, time, attendanceType);
    }

    public void printWarningCrews(WarningCrewsDTO warningCrewsDTO) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        stringBuilder.appendLine("제적 위험자 조회 결과");
        warningCrewsDTO.warningCrewDetailDTO().stream()
                .sorted((o1, o2) -> Long.compare(o2.convertLateCount(), o1.convertLateCount()))
                .forEach(warningCrewDetailDTO -> stringBuilder.appendLine(String.format("- %s: 결석 %d회, 지각: %d회 (%s)",
                        warningCrewDetailDTO.crewName(),
                        warningCrewDetailDTO.absenceCount(),
                        warningCrewDetailDTO.lateCount(),
                        warningCrewDetailDTO.warningType()
                )));
        stringBuilder.print();
    }

    public void printError(String message) {
        System.out.println(String.format("[ERROR] %s", message));
    }
}
