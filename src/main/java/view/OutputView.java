package view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import service.dto.AttendanceRecordResponse;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.RiskCrewsResponse;
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

    public static void printMonthAttendanceRecords(List<AttendanceRecordResponse> attendanceRecords) {
        attendanceRecords.forEach(record -> {
            System.out.printf("%s %s (%s)%n",
                    record.date(), record.time(), record.attendanceStatus());
        });
        System.out.println();
    }

    public static void printMonthAttendanceStatusCount(Map<String, Integer> statusCount) {
        statusCount.forEach((status, count) -> {
            System.out.printf("%s: %d회%n%n", status, count);
        });
    }

    public static void printRiskRank(String riskRank) {
        System.out.printf("%s 대상자입니다.%n%n", riskRank);
    }

    public static void printRiskCrews(RiskCrewsResponse riskCrews) {
        System.out.println("제적 위험자 조회 결과");
        riskCrews.riskCrews().forEach(riskCrew ->
                System.out.printf("- %s: %s (%s)%n",
                        riskCrew.nickname(),
                        getStatusCountPrintFormat(riskCrew.attendanceStatusCount()),
                        riskCrew.riskRank()
                )
        );
    }

    private static String getStatusCountPrintFormat(Map<String, Integer> statusCount) {
        return String.join(", ",
                statusCount.entrySet()
                        .stream()
                        .map(entry -> String.format("%s %d회", entry.getKey(), entry.getValue()))
                        .toList());
    }
}
