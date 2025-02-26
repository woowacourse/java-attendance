package attendance.view;

import attendance.model.AttendanceLog;
import attendance.model.AttendanceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {

    public void printDate(LocalDate date) {
        System.out.print(date.format(DateTimeFormatter.ofPattern("오늘은 MM월 dd일 E요일입니다. ")));
    }

    public void printAttend(LocalDateTime dateTime, AttendanceType attendanceType) {
        System.out.print(dateTime.format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 HH:mm ")));
        System.out.printf("(%s)%n", attendanceType.getKoreanLabel());
    }

    public void printEditAttendanceLog(AttendanceLog beforeAttendanceLog,
                                       AttendanceType beforeType,
                                       AttendanceLog afterAttendanceLog,
                                       AttendanceType afterType) {
        if (beforeAttendanceLog.isNotRecorded()) {
            System.out.print(beforeAttendanceLog.getAttendanceDate().format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 --:-- ")));
            System.out.printf("(%s)", beforeType.getKoreanLabel());
            System.out.print(afterAttendanceLog.getAttendanceTime().format(DateTimeFormatter.ofPattern(" -> HH:mm ")));
            System.out.printf("(%s)", afterType.getKoreanLabel());
            System.out.println(" 수정 완료!");
            return;
        }
        System.out.print(beforeAttendanceLog.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 HH:mm ")));
        System.out.printf("(%s)", beforeType.getKoreanLabel());
        System.out.print(afterAttendanceLog.getAttendanceTime().format(DateTimeFormatter.ofPattern(" -> HH:mm ")));
        System.out.printf("(%s)", afterType.getKoreanLabel());
        System.out.println(" 수정 완료!");
    }
}
