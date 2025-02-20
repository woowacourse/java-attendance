package attendance.view;

import attendance.dto.AttendanceDTO;
import attendance.dto.AttendanceDTO.AttendanceDetailDTO;
import attendance.dto.WarningCrewsDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class OutputView {

    private final DateTimeFormatter normalFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");
    private final DateTimeFormatter absenceFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE --:--");

    public void printAttendanceHistory(AttendanceDTO attendanceDTO) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        stringBuilder.appendLine(String.format("이번 달 %s의 출석 기록입니다.", attendanceDTO.crewName()));
        attendanceDTO.attendanceDetailDTOs().stream()
                .sorted(Comparator.comparing(AttendanceDetailDTO::attendanceDateTime))
                .forEach(attendanceDetail -> stringBuilder.appendLine(generateAttendanceDetail(
                        attendanceDetail.attendanceDateTime(),
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
                attendanceDetailDTO.attendanceDateTime(),
                attendanceDetailDTO.attendanceType()
        ));
    }

    public void printModifyResult(AttendanceDetailDTO before, AttendanceDetailDTO after) {
        String beforeDetail = generateAttendanceDetail(before.attendanceDateTime(), before.attendanceType());
        String afterDetail = generateAttendanceDetail(after.attendanceDateTime(), after.attendanceType());
        System.out.println(String.format("%s -> %s 수정 완료!", beforeDetail, afterDetail));
    }

    private String generateAttendanceDetail(LocalDateTime attendanceDateTime, String attendanceType) {
        String dateTime = attendanceDateTime.format(normalFormatter);
        if (attendanceType.equals("결석")) {
            dateTime = attendanceDateTime.format(absenceFormatter);
        }
        return String.format("%s (%s)", dateTime, attendanceType);
    }

    public void printWarningCrews(WarningCrewsDTO warningCrewsDTO) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        stringBuilder.appendLine("제적 위험자 조회 결과");
        warningCrewsDTO.warningCrewDetailDTO().stream()
                .sorted((o1, o2) -> Long.compare(o1.convertLateCount(), o2.convertLateCount()))
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
