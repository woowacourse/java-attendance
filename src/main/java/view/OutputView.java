package view;

import static util.DateTimeUtil.DATE_FORMAT;
import static util.DateTimeUtil.DATE_TIME_FORMAT;
import static util.DateTimeUtil.TIME_FORMAT;

import domain.AbstractAttendanceRecord;
import domain.AttendanceRecord;
import domain.RiskRank;
import java.util.List;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.MonthAttendanceStatisticsResponse.AttendanceStatusCount;
import service.dto.RiskCrewsResponse;
import service.dto.SaveAttendanceRecordResponse;

public class OutputView {

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
                convertToTime(modified.before()), modified.before().getStatus().getDescription(),
                convertToTime(modified.after()), modified.after().getStatus().getDescription()
        );
        System.out.println();
    }

    public static void printMonthAttendanceRecords(List<AbstractAttendanceRecord> attendanceRecords) {
        attendanceRecords.forEach(record ->
                System.out.println(convertToDateTimeStatus(record))
        );
        System.out.println();
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
                        riskCrew.riskRank().getDescription()
                )
        );
        System.out.println();
    }

    private static String convertToDateTimeStatus(AbstractAttendanceRecord record) {
        return String.format("%s %s (%s)",
                record.getDate().format(DATE_FORMAT), convertToTime(record), record.getStatus().getDescription());
    }

    private static String convertToTime(AbstractAttendanceRecord record) {
        if (record.isPresent()) {
            return ((AttendanceRecord) record).getTime().format(TIME_FORMAT);
        }
        return "--:--";
    }
}
