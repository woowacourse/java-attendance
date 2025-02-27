package attendance.view;

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
            attendanceRemarkDto.attendanceStatus());
    }
}
