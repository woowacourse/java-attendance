package view;

import domain.AbstractAttendanceRecord;
import domain.AttendanceRecord;
import domain.EmptyAttendanceRecord;
import domain.RiskRank;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import service.dto.AttendanceStatusCount;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.RiskCrewsResponse;
import service.dto.SaveAttendanceRecordResponse;

public class OutputView {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
            Locale.KOREAN);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM월 dd일 E요일",
            Locale.KOREAN);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static void printSavedAttendanceRecord(SaveAttendanceRecordResponse saved) {
        System.out.printf("%s (%s)%n",
                saved.dateTime().format(DATE_TIME_FORMAT),
                saved.status().getDescription()
        );
        System.out.println();
    }

    public static void printModifiedAttendanceRecord(ModifyAttendanceRecordResponse modified) {
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
                modified.before().getDate().format(DATE_FORMAT),
                modified.before().getTime().format(TIME_FORMAT), modified.before().getStatus().getDescription(),
                modified.after().getTime(), modified.after().getStatus().getDescription()
        );
        System.out.println();
    }

    public static void printMonthAttendanceRecords(List<AbstractAttendanceRecord> attendanceRecords) {
        attendanceRecords.forEach(record -> {
            System.out.println(convertToPrintFormat(record));
        });
        System.out.println();
    }

    private static String convertToPrintFormat(AbstractAttendanceRecord record) {
        if (record instanceof EmptyAttendanceRecord) {
            return String.format("%s --:-- (%s)",
                    record.getDate().format(DATE_FORMAT), record.getStatus().getDescription());
        }
        AttendanceRecord attendanceRecord = (AttendanceRecord) record;
        return String.format("%s %s (%s)",
                attendanceRecord.getDate().format(DATE_FORMAT), attendanceRecord.getTime().format(TIME_FORMAT),
                attendanceRecord.getStatus().getDescription());
    }

    public static void printMonthAttendanceStatusCount(AttendanceStatusCount statusCount) {
        System.out.printf("출석: %d회%n", statusCount.attendanceCount());
        System.out.printf("지각: %d회%n", statusCount.lateCount());
        System.out.printf("결석: %d회%n", statusCount.absentCount());
        System.out.println();
    }

    public static void printRiskRank(RiskRank riskRank) {
        System.out.printf("%s 대상자입니다.%n", riskRank.getDescription());
        System.out.println();
    }

    public static void printRiskCrews(RiskCrewsResponse riskCrews) {
        System.out.println("제적 위험자 조회 결과");
        riskCrews.riskCrews().forEach(riskCrew ->
                System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                        riskCrew.nickname(),
                        riskCrew.absentCount(),
                        riskCrew.lateCount(),
                        riskCrew.riskRank()
                )
        );
        System.out.println();
    }
}
