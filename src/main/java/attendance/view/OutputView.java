package attendance.view;

import attendance.domain.dto.AttendanceResult;
import attendance.domain.dto.ModifyAttendanceResult;

public class OutputView {

    public void displayAttendanceResult(AttendanceResult attendanceResult) {
        /*
        12월 05일 화요일 09:59 (출석)
         */
        String output = "%d월 %02d일 %s %02d:%02d (%s)\n";
        System.out.printf(output,
            attendanceResult.attendanceMonth(),
            attendanceResult.attendanceDay(),
            attendanceResult.attendanceDayOfWeek(),
            attendanceResult.attendanceHour(),
            attendanceResult.attendanceMinute(),
            attendanceResult.attendanceStatus()
        );
    }

    public void displayModifyResult(ModifyAttendanceResult modifyAttendanceResult) {
        //12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
        String output = "%d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s)\n";
        System.out.printf(output,
            modifyAttendanceResult.modifyMonth(),
            modifyAttendanceResult.modifyDay(),
            modifyAttendanceResult.modifyDayOfWeek(),
            modifyAttendanceResult.modifyOldHour(),
            modifyAttendanceResult.modifyOldMinute(),
            modifyAttendanceResult.modifyOldStatus(),
            modifyAttendanceResult.modifyNewHour(),
            modifyAttendanceResult.modifyNewMinute(),
            modifyAttendanceResult.modifyOldStatus()
            );

    }
}
