package attendance.view;

import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import attendance.dto.AttendanceCheckDto;
import attendance.dto.AttendanceEditDto;
import attendance.dto.AttendanceInfoDto;
import attendance.utils.DateConverter;

import java.util.List;
import java.util.Map;

public class OutputView {
    public void printUseEdit() {
        System.out.println("오늘 출석 기록이 있습니다. 출석 수정기능을 이용해 주세요");
    }

    public void printRemarkAttendanceResult(AttendanceInfoDto attendanceInfoDto) {
        System.out.printf("%s %s (%s)%n",
            DateConverter.convertToString(attendanceInfoDto.attendanceDate()),
            DateConverter.convertToString(attendanceInfoDto.attendanceTime()),
            parseStatusToString(attendanceInfoDto.attendanceStatus()));
    }

    public void printEditAttendanceResult(AttendanceEditDto dto) {
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
            DateConverter.convertToString(dto.editDate()),
            DateConverter.convertToString(dto.beforeEditTime()),
            parseStatusToString(dto.beforeEditStatus()),
            DateConverter.convertToString(dto.editTime()),
            parseStatusToString(dto.editStatus())
        );
    }

    public void printCheckAttendanceResult(AttendanceCheckDto dto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", dto.name());
        printAttendanceRecord(dto);
        printAttendanceStatusCount(dto);
        printPenalty(dto);
    }

    private void printAttendanceRecord(AttendanceCheckDto dto) {
        List<AttendanceInfoDto> attendanceInfoDtos = dto.attendanceInfos();
        for (AttendanceInfoDto attendanceInfoDto : attendanceInfoDtos) {
            printRemarkAttendanceResult(attendanceInfoDto);
        }
    }

    private static void printAttendanceStatusCount(AttendanceCheckDto dto) {
        Map<AttendanceStatus, Integer> statusCount = dto.attendanceStatusCount();
        System.out.printf("출석: %d회%n지각: %d회%n결석: %d회%n%n",
            statusCount.getOrDefault(AttendanceStatus.PRESENCE, 0),
            statusCount.getOrDefault(AttendanceStatus.LATE, 0),
            statusCount.getOrDefault(AttendanceStatus.ABSENCE, 0));
    }

    private void printPenalty(AttendanceCheckDto dto) {
        AttendancePenalty penalty = dto.penalty();
        if (penalty == AttendancePenalty.NONE) return;
        System.out.printf("%s 대상자 입니다.%n%n", parsePenaltyToString(penalty));
    }

    private String parseStatusToString(AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.ABSENCE) return "결석";
        if (attendanceStatus == AttendanceStatus.LATE) return "지각";
        return "출석";
    }

    private String parsePenaltyToString(AttendancePenalty penalty) {
        if (penalty == AttendancePenalty.EXPULSION) return "제적";
        if (penalty == AttendancePenalty.COUNSELING) return "면담";
        return "경고";
    }
}
