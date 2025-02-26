package attendance.view;

import attendance.domain.dto.AttendanceState;
import attendance.domain.dto.RecordUpdateResult;
import attendance.record.AttendanceRecord;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class OutputView {

    private final DateTimeFormatter dateTimeformatter =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.KOREA);

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
        String stateContent = String.format("""
                 출석: %d회
                 지각: %d회
                 결석: %d회
                """, state.getAttendanceCount(), state.getLateCount(), state.getAbsenceCount());
        System.out.println(stateContent);
        String riskTypContent = String.format("%s 대상자입니다.", state.getRiskTyp().getName());
        System.out.println(riskTypContent);
        System.out.println();
    }

    public void printRiskCrews(List<AttendanceState> states) {
        System.out.println("제적 위험자 조회 결과");
        states.stream().map(this::makeStateContent)
                .forEach(System.out::println);
        System.out.println();
    }

    private String makeRecordContent(AttendanceRecord record) {
        String date = dateTimeformatter.format(record.getArrivalDateTime());
        String time = makeTimeContent(record.getArrivalDateTime().toLocalTime());
        String typeName = record.getAttendanceType().getName();
        return String.format("%s %s (%s)", date, time, typeName);
    }

    private String makeNewRecordContent(AttendanceRecord record) {
        String time = makeTimeContent(record.getArrivalDateTime().toLocalTime());
        String typeName = record.getAttendanceType().getName();
        return String.format(" -> %s (%s) 수정 완료!", time, typeName);
    }

    private String makeSearchResultHeader(String nickname) {
        return String.format("이번 달 %s의 출석 기록입니다.", nickname);
    }

    private String makeStateContent(AttendanceState state) {
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)",
                state.getNickname(), state.getAbsenceCount(), state.getLateCount(), state.getRiskTyp().getName());
    }

    private String makeTimeContent(LocalTime time) {
        if (time.equals(LocalTime.MIN)) {
            return "--:--";
        }
        return time.toString();
    }

}
