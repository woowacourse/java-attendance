package attendance.view;

import attendance.dto.AttendanceDto;
import attendance.dto.AttendanceDto.AttendanceDetailDto;
import attendance.dto.WarningCrewsDto;
import attendance.model.Attendance;
import attendance.model.CustomClock;
import attendance.util.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class OutputView {

    private static final DateTimeFormatter normalFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");
    private static final DateTimeFormatter absenceFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE --:--");

    public void printAttendanceHistory(AttendanceDto attendanceDTO, LocalDate monthStart, CustomClock clock) {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.appendLine(String.format("이번 달 %s의 출석 기록입니다.", attendanceDTO.crewName()));

        appendAttendanceLines(sb, attendanceDTO, monthStart, clock);

        sb.appendLine(String.format("출석: %d회", attendanceDTO.attendanceCount()));
        sb.appendLine(String.format("지각: %d회", attendanceDTO.lateCount()));
        sb.appendLine(String.format("결석: %d회", attendanceDTO.absenceCount()));
        sb.appendLine(String.format("%s 대상자입니다.", attendanceDTO.warningType()));
        sb.print();
    }

    private void appendAttendanceLines(CustomStringBuilder sb, AttendanceDto attendanceDTO, LocalDate monthStart,
                                       CustomClock clock) {
        for (LocalDate date = monthStart; !date.isAfter(clock.nowDate()); date = date.plusDays(1)) {
            if (DateUtil.isWeekendOrHoliday(date, clock)) {
                continue;
            }
            sb.appendLine(buildAttendanceLine(date, attendanceDTO));
        }
    }

    private String buildAttendanceLine(LocalDate date, AttendanceDto attendanceDTO) {
        Optional<AttendanceDetailDto> maybeDetail = attendanceDTO.attendanceDetailDtos().stream()
                .filter(dto -> dto.attendanceDateTime().toLocalDate().equals(date))
                .findFirst();

        if (maybeDetail.isPresent()) {
            AttendanceDetailDto detail = maybeDetail.get();
            return detail.attendanceDateTime().format(normalFormatter) + " (" + detail.attendanceType() + ")";
        }
        return date.format(absenceFormatter) + " (" + Attendance.ABSENT.getTitle() + ")";
    }

    public void printAttendanceDetail(AttendanceDetailDto attendanceDetailDTO) {
        System.out.println(generateAttendanceDetail(
                attendanceDetailDTO.attendanceDateTime(),
                attendanceDetailDTO.attendanceType()
        ));
    }

    public void printModifyResult(AttendanceDetailDto before, AttendanceDetailDto after) {
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

    public void printWarningCrews(WarningCrewsDto warningCrewsDTO) {
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
