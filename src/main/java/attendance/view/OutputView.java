package attendance.view;

import attendance.domain.dto.AttendanceResult;

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
            attendanceResult.attendanceStatus());
    }
}
