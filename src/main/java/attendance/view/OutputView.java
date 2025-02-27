package attendance.view;

import attendance.dto.AttendanceResponse;
import attendance.util.DateTimeUtil;

public class OutputView {

    public static void attendanceResponse(AttendanceResponse response) {
        // 12월 05일 화요일 09:59 (출석)
        System.out.printf(
            "%n" + response.dateTime().format(DateTimeUtil.DATE_TIME_FORMATTER)
                + " (" + response.status().getName() + ")%n%n");
    }

    public static void exception(Exception e) {
        System.out.println(e.getMessage() + " 다시 입력하세요.");
    }
}
