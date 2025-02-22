package attendance.view;

import attendance.domain.AttendanceRecord;
import attendance.domain.RiskStatistic;
import attendance.domain.RiskType;
import attendance.dto.UpdateResult;
import attendance.view.message.OutputMessage;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printMenu(LocalDate today) {
        String menuContent = makeMenuContent(today);
        System.out.println(menuContent);
    }

    public void printAttendanceRecord(AttendanceRecord record) {
        String content = makeRecordContent(record);
        System.out.println(content);
        printBlankLine();
    }

    public void printAttendUpdateResult(UpdateResult updateResult) {
        String oldRecordContent = makeRecordContent(updateResult.oldRecord());
        String updateContent = makeUpdateContent(updateResult.newRecord());
        System.out.println(oldRecordContent + updateContent);
        printBlankLine();
    }

    public void printRecordsInMonth(List<AttendanceRecord> records) {
        String header = makeMonthlyRecordHeader(records.getFirst().getDate().getMonth());
        System.out.println(header);
        printBlankLine();

        records.stream()
                .map(this::makeRecordContent)
                .forEach(System.out::println);
        printBlankLine();
    }

    public void printAttendanceState(RiskStatistic riskStatistic) {
        String content = makeStateContent(riskStatistic);
        System.out.println(content);
        printBlankLine();

        String resultContent = makeStateResultContent(riskStatistic.getRiskType());
        System.out.println(resultContent);
        printBlankLine();
    }

    public void printRiskStatistics(List<RiskStatistic> riskStatistics) {
        System.out.println(OutputMessage.RISK_HEADER.getContent());
        riskStatistics.forEach(this::makeRiskStatisticContent);
        printBlankLine();
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
        printBlankLine();
    }

    private void printBlankLine() {
        System.out.println();
    }

    private String makeMenuContent(LocalDate today) {
        return String.format(
                OutputMessage.MENU.getContent(),
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    private String makeRecordContent(AttendanceRecord record) {
        LocalDate date = record.getDate();
        return String.format(
                OutputMessage.RECORD.getContent(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                record.getTime().toString(),
                record.getType().getName());
    }

    private String makeUpdateContent(AttendanceRecord newRecord) {
        return String.format(
                OutputMessage.UPDATED.getContent(),
                newRecord.getTime(),
                newRecord.getType().getName());
    }

    private String makeMonthlyRecordHeader(Month month) {
        return String.format(OutputMessage.MONTHLY_RECORD_HEADER.getContent(), month.getValue());
    }

    private String makeStateContent(RiskStatistic riskStatistic) {
        return String.format(OutputMessage.ATTENDANCE_STATE.getContent(),
                riskStatistic.getAttendanceCount(),
                riskStatistic.getLateCount(),
                riskStatistic.getExpulsionCount());
    }

    private String makeStateResultContent(RiskType riskType) {
        return String.format(OutputMessage.ATTENDANCE_STATE_RESULT.getContent(), riskType.getName());
    }

    private String makeRiskStatisticContent(RiskStatistic statistic) {
        return String.format(OutputMessage.RISK_INFO.getContent(),
                statistic.getNickname(),
                statistic.getExpulsionCount(),
                statistic.getLateCount(),
                statistic.getRiskType().getName());
    }
}
