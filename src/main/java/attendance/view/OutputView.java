package attendance.view;

import attendance.domain.dto.AttendanceResult;
import attendance.domain.dto.AttendanceStatusResult;
import attendance.domain.dto.ModifyAttendanceResult;
import java.util.List;

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

    public void displayAttendanceRecord(List<AttendanceResult> attendanceResults, AttendanceStatusResult attendanceStatusResult) {
        String outputResult = "%d월 %02d일 %s %02d:%02d (%s)\n";
        for (AttendanceResult attendanceResult : attendanceResults) {
            System.out.printf(outputResult,
                attendanceResult.attendanceMonth(),
                attendanceResult.attendanceDay(),
                attendanceResult.attendanceDayOfWeek(),
                attendanceResult.attendanceHour(),
                attendanceResult.attendanceMinute(),
                attendanceResult.attendanceStatus()
            );
        }
        //출석: 5회
        //지각: 2회
        //결석: 3회
        //
        //면담 대상자입니다.
        String outputStatus = """
            출석: %d회
            지각: %d회
            결석: %d회
            
            %s
            """;
        System.out.printf(outputStatus,
            attendanceStatusResult.attendanceCount(),
            attendanceStatusResult.lateCount(),
            attendanceStatusResult.absentCount(),
            attendanceStatusResult.subjectStatus()
            );
    }
}
