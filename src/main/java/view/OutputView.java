package view;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.SaveAttendanceRecordResponse;

public class OutputView {
    private static final DateTimeFormatter SAVED_ATTENDANCE_RECORD_FORMAT
            = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);

    public static void printSavedAttendanceRecord(SaveAttendanceRecordResponse saved) {
        System.out.printf("%s (%s)%n",
                saved.dateTime().format(SAVED_ATTENDANCE_RECORD_FORMAT),
                saved.status().getTitle()
        );
    }

    public static void printModifiedAttendanceRecord(ModifyAttendanceRecordResponse modified) {
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
                modified.date(),
                modified.before().time(), modified.before().status(),
                modified.after().time(), modified.after().status()
        );
    }
}
