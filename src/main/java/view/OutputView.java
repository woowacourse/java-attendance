package view;

import controller.dto.SavedAttendanceRecord;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OutputView {
    private static final DateTimeFormatter SAVED_ATTENDANCE_RECORD_FORMAT
            = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);

    public static void printSavedAttendanceRecord(SavedAttendanceRecord saved) {
        System.out.printf("%s (%s)%n",
                saved.dateTime().format(SAVED_ATTENDANCE_RECORD_FORMAT),
                saved.status().getTitle()
        );
    }
}
