package attendance.view;

import attendance.domain.AttendanceStatus;
import attendance.dto.AttendanceEditDto;
import attendance.dto.AttendanceRemarkDto;
import attendance.utils.DateConverter;

public class OutputView {
    public void printUseEdit() {
        System.out.println("오늘 출석 기록이 있습니다. 출석 수정기능을 이용해 주세요");
    }

    public void printRemarkAttendanceResult(AttendanceRemarkDto attendanceRemarkDto) {
        System.out.printf("%s %s (%s)%n",
            DateConverter.convertToString(attendanceRemarkDto.attendanceDate()),
            DateConverter.convertToString(attendanceRemarkDto.attendanceTime()),
            parseString(attendanceRemarkDto.attendanceStatus()));
    }

    public void printEditAttendanceResult(AttendanceEditDto dto) {
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
            DateConverter.convertToString(dto.editDate()),
            DateConverter.convertToString(dto.beforeEditTime()),
            parseString(dto.beforeEditStatus()),
            DateConverter.convertToString(dto.editTime()),
            parseString(dto.editStatus())
        );
    }

    private String parseString(AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.ABSENCE) return "결석";
        if (attendanceStatus == AttendanceStatus.LATE) return "지각";
        return "출석";
    }
}
