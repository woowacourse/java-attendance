package view;

import java.util.List;
import java.util.Map;
import service.dto.AttendanceRecordResponse;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.RiskCrewsResponse;
import service.dto.SaveAttendanceRecordResponse;

public class OutputView {
    public static void printSavedAttendanceRecord(SaveAttendanceRecordResponse saved) {
        System.out.printf("%s (%s)%n",
                saved.dateTime(),
                saved.status().getTitle()
        );
        System.out.println();
    }

    public static void printModifiedAttendanceRecord(ModifyAttendanceRecordResponse modified) {
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
                modified.date(),
                modified.before().time(), modified.before().status(),
                modified.after().time(), modified.after().status()
        );
        System.out.println();
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
            System.out.printf("%s: %d회%n", status, count);
        });
        System.out.println();
    }

    public static void printRiskRank(String riskRank) {
        System.out.printf("%s 대상자입니다.%n", riskRank);
        System.out.println();
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
        System.out.println();
    }

    private static String getStatusCountPrintFormat(Map<String, Integer> statusCount) {
        return String.join(", ",
                statusCount.entrySet()
                        .stream()
                        .map(entry -> String.format("%s %d회", entry.getKey(), entry.getValue()))
                        .toList());
    }
}
