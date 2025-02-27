package attendance.view;

import attendance.dto.AttendanceResponse;
import attendance.dto.ModifyAttendanceResponse;
import attendance.util.DateTimeUtil;

public class OutputView {

    private OutputView() {
    }

    public static void attendanceResponse(AttendanceResponse response) {
        System.out.printf(
            "%n" + response.dateTime().format(DateTimeUtil.DATE_TIME_FORMATTER)
                + " (" + response.status().getName() + ")%n%n");
    }

    public static void modifyAttendanceResponse(ModifyAttendanceResponse response) {
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n%n",
            response.date().format(DateTimeUtil.DATE_FORMATTER),
            response.before().time().format(DateTimeUtil.TIME_FORMATTER),
            response.before().status().getName(),
            response.after().time().format(DateTimeUtil.TIME_FORMATTER),
            response.after().status().getName()
        );
    }

    public static void exception(Exception e) {
        System.out.println(e.getMessage() + " 다시 입력하세요.");
    }
}
