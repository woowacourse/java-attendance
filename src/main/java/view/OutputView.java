package view;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import service.dto.SaveAttendanceRecordResponse;

public class OutputView {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
            Locale.KOREAN);

    public static void printSavedAttendanceRecord(SaveAttendanceRecordResponse saved) {
        System.out.printf("%s (%s)%n",
                saved.dateTime().format(DATE_TIME_FORMAT),
                saved.status().getDescription()
        );
        System.out.println();
    }

//    public static void printModifiedAttendanceRecord(ModifyAttendanceRecordResponse modified) {
//        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
//                modified.date(),
//                modified.before().time(), modified.before().status(),
//                modified.after().time(), modified.after().status()
//        );
//        System.out.println();
//    }
//
//    public static void printMonthAttendanceRecords(List<AttendanceRecordResponse> attendanceRecords) {
//        attendanceRecords.forEach(record -> {
//            System.out.printf("%s %s (%s)%n",
//                    record.date(), record.time(), record.attendanceStatus());
//        });
//        System.out.println();
//    }
//
//    public static void printMonthAttendanceStatusCount(AttendanceStatusCount statusCount) {
//        System.out.printf("출석: %d회%n", statusCount.attendanceCount());
//        System.out.printf("지각: %d회%n", statusCount.lateCount());
//        System.out.printf("결석: %d회%n", statusCount.absentCount());
//        System.out.println();
//    }
//
//    public static void printRiskRank(String riskRank) {
//        System.out.printf("%s 대상자입니다.%n", riskRank);
//        System.out.println();
//    }
//
//    public static void printRiskCrews(RiskCrewsResponse riskCrews) {
//        System.out.println("제적 위험자 조회 결과");
//        riskCrews.riskCrews().forEach(riskCrew ->
//                System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
//                        riskCrew.nickname(),
//                        riskCrew.absentCount(),
//                        riskCrew.lateCount(),
//                        riskCrew.riskRank()
//                )
//        );
//        System.out.println();
//    }
}
