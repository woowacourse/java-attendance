package attendance.view;

import attendance.domain.record.AttendanceRecord;
import attendance.domain.risk.RiskType;
import attendance.dto.AttendanceState;
import attendance.dto.RecordUpdateResult;
import attendance.utility.DateTimeUtility;
import attendance.view.message.OutputMessage;
import java.time.LocalTime;
import java.util.List;

public class OutputView {

    public void printRecord(AttendanceRecord record) {
        String content = makeRecordContent(record);
        System.out.println(content);
        System.out.println();
    }

    public void printRecordUpdateResult(RecordUpdateResult result) {
        String oldRecordContent = makeRecordContent(result.oldRecord());
        String newRecordContent = makeNewRecordContent(result.newRecord());
        System.out.println(oldRecordContent + newRecordContent);
        System.out.println();
    }

    public void printRecordSearchResult(List<AttendanceRecord> records) {
        String headerContent = makeSearchResultHeader(records.getFirst().getNickname());
        System.out.println(headerContent);
        records.stream()
                .map(this::makeRecordContent)
                .forEach(System.out::println);
        System.out.println();
    }

    public void printAttendanceState(AttendanceState state) {
        String stateContent = String.format(OutputMessage.ATTENDANCE_STATE.getContent(),
                state.getAttendanceCount(), state.getLateCount(), state.getAbsenceCount());
        System.out.println(stateContent);
        if (state.getRiskTyp() != RiskType.NONE) {
            return;
        }
        String riskTypContent = String.format(OutputMessage.RISK_TYPE.getContent(), state.getRiskTyp().getName());
        System.out.println(riskTypContent);
        System.out.println();
    }

    public void printRiskCrews(List<AttendanceState> states) {
        System.out.println(OutputMessage.RISK_CREW_HEADER.getContent());
        states.stream().map(this::makeRiskCrewContent)
                .forEach(System.out::println);
        System.out.println();
    }

    private String makeRecordContent(AttendanceRecord record) {
        String date = DateTimeUtility.formatDateTime(record.getArrivalDateTime());
        String time = makeTimeContent(record.getArrivalDateTime().toLocalTime());
        String typeName = record.getAttendanceType().getName();
        return String.format(OutputMessage.RECORD.getContent(), date, time, typeName);
    }

    private String makeNewRecordContent(AttendanceRecord record) {
        String time = makeTimeContent(record.getArrivalDateTime().toLocalTime());
        String typeName = record.getAttendanceType().getName();
        return String.format(OutputMessage.UPDATE_RESULT.getContent(), time, typeName);
    }

    private String makeSearchResultHeader(String nickname) {
        return String.format(OutputMessage.SEARCH_RESULT_HEADER.getContent(), nickname);
    }

    private String makeRiskCrewContent(AttendanceState state) {
        return String.format(OutputMessage.RISK_CREW_CONTENT.getContent(),
                state.getNickname(), state.getAbsenceCount(), state.getLateCount(), state.getRiskTyp().getName());
    }

    private String makeTimeContent(LocalTime time) {
        if (time.equals(LocalTime.MIN)) {
            return OutputMessage.EMPTY_TIME.getContent();
        }
        return time.toString();
    }
}
