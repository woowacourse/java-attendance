import dto.AttendanceRecordResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public void displayCheckAttendanceResult(AttendanceRecordResponse response) {
        int day = response.date().getDayOfMonth();
        String koreanDayOfWeek = response.date().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String formattedTime = response.time().format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.printf("12월 %02d일 %s %s %s%n", day, koreanDayOfWeek, formattedTime, response.attendanceStatus());
    } // 출석 확인 결과
}