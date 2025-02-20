package view;

import dto.AttendanceRecordResponse;
import dto.CrewPenaltyResponse;
import dto.ModifyAttendanceResponse;
import dto.TotalRecordsResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class OutputView {
    public void displayPrompt() {
        System.out.println(OutputMessages.DATE_PROMPT.getFormat());
        System.out.println(OutputMessages.FIRST_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.SECOND_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.THIRD_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.FOURTH_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.QUIT_FUNCTION_PROMPT.getFormat());
        System.out.println(OutputMessages.GUIDE_PROMPT.getFormat());
    }

    public static void displaySpacing() {
        System.out.println();
    }

    public void displayCheckAttendanceResult(AttendanceRecordResponse response) {
        int day = response.date().getDayOfMonth();
        String koreanDayOfWeek = response.date().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String formattedTime = response.time().format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.printf("12월 %02d일 %s %s %s%n", day, koreanDayOfWeek, formattedTime, response.attendanceStatus());
    }

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
    }

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

    public void displayPenaltyCrew(List<CrewPenaltyResponse> responses) {

        responses.sort(Comparator.comparing(CrewPenaltyResponse::penaltyStatus)
                .thenComparing(response -> response.absentCount() + response.lateCount() / 3)
                .reversed()
                .thenComparing(CrewPenaltyResponse::name)
        );

        System.out.println("제적 위험자 조회 결과");
        for (CrewPenaltyResponse response : responses) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                    response.name(), response.absentCount(), response.lateCount(), response.penaltyStatus());
        }
    }
}