package view;

import dto.AttendanceRecordResponse;
import dto.ModifyAttendanceResponse;
import dto.TotalRecordsResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {
    public void displayCheckAttendanceResult(AttendanceRecordResponse response) {
        int day = response.date().getDayOfMonth();
        String koreanDayOfWeek = response.date().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String formattedTime = response.time().format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.printf("12월 %02d일 %s %s %s%n", day, koreanDayOfWeek, formattedTime, response.attendanceStatus());
    } // 출석 확인 결과

    public void displayModifyAttendanceResult(ModifyAttendanceResponse response) {
        int day = response.date().getDayOfMonth();
        String koreanDayOfWeek = response.date().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedOriginalTime = response.originalTime().format(dateTimeFormatter);
        String formattedModifiedTime = response.modifiedTime().format(dateTimeFormatter);

        System.out.printf("12월 %02d일 %s %s %s -> %s %s 수정 완료!%n",
                day, koreanDayOfWeek, formattedOriginalTime, response.originalStatus(), formattedModifiedTime,
                response.modifiedStatus());
    } // 출석 확인 결과

    public void displayAttendanceRecordByName(String name, List<AttendanceRecordResponse> attendanceRecords,
                                              TotalRecordsResponse totalRecords, String penalty) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n", name);
        displaySpacing();
        for (AttendanceRecordResponse attendanceRecord : attendanceRecords) {
            int day = attendanceRecord.date().getDayOfMonth();
            String koreanDayOfWeek = attendanceRecord.date().getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.KOREAN);
            String time = "--:--";
            if (attendanceRecord.time() != null) {
                time = attendanceRecord.time().format(DateTimeFormatter.ofPattern("HH:mm"));
            }
            System.out.printf("12월 %02d일 %s %s %s%n", day, koreanDayOfWeek, time,
                    attendanceRecord.attendanceStatus().getMessage());
        }
        displaySpacing();
        System.out.printf("출석: %d회%n", totalRecords.attendanceCount());
        System.out.printf("지각: %d회%n", totalRecords.lateCount());
        System.out.printf("결석: %d회%n", totalRecords.absentCount());
        displaySpacing();
        System.out.printf("%s 대상자입니다.%n", penalty);
    }

    public static void displaySpacing() {
        System.out.println();
    }
}