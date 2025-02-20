package view;

import dto.result.AttendResult;
import dto.result.ExpelMeasurementResult;
import dto.result.MemberAttendResult;
import dto.result.MemberAttendanceModifyResult;
import util.outputHandler.OutputHandler;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {
    
    private final OutputHandler outputHandler;
    
    public OutputView(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }
    
    public void handleAttendResult(AttendResult attendResult) {
        var result = parseAttendResultValue(attendResult);
        outputHandler.handle(result);
    }
    
    private static String parseAttendResultValue(AttendResult attendResult) {
        return String.format("%02d월 %02d일 %s %02d:%02d (%s)",
                attendResult.attendanceDateTime().getMonth().getValue(),
                attendResult.attendanceDateTime().getDayOfMonth(),
                attendResult.attendanceDateTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendResult.attendanceDateTime().getHour(),
                attendResult.attendanceDateTime().getMinute(),
                attendResult.attendanceStatus()
        );
    }
    
    public void handleAttendanceModifyResult(MemberAttendanceModifyResult memberAttendanceModifyResult) {
        String result = String.format("%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!",
                memberAttendanceModifyResult.attendanceDate().getMonth().getValue(),
                memberAttendanceModifyResult.attendanceDate().getDayOfMonth(),
                memberAttendanceModifyResult.attendanceDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                memberAttendanceModifyResult.oldAttendanceTime().getHour(),
                memberAttendanceModifyResult.oldAttendanceTime().getMinute(),
                memberAttendanceModifyResult.oldAttendanceStatus(),
                memberAttendanceModifyResult.newAttendanceTime().getHour(),
                memberAttendanceModifyResult.newAttendanceTime().getMinute(),
                memberAttendanceModifyResult.newAttendanceStatus()
        );
        System.out.println(result);
    }
    
    public void handleMemberAttendanceResult(MemberAttendResult attendanceResult) {
        String result = String.format("""
                        이번 달 %s의 출석 기록입니다.
                        
                        %s
                        
                        출석: %d회
                        지각: %d회
                        결석: %d회
                        
                        %s
                        """,
                attendanceResult.name(),
                parseAttendResults(attendanceResult.attendanceResults()),
                attendanceResult.attendCount(),
                attendanceResult.lateCount(),
                attendanceResult.absentCount(),
                parseInterviewTarget(attendanceResult)
        );
        
        outputHandler.handle(result);
    }
    
    private static String parseAttendResults(List<AttendResult> attendResults) {
        return attendResults.stream()
                .map(OutputView::parseAttendResultValue)
                .reduce((str1, str2) -> str1 + "\n" + str2)
                .orElse("");
    }
    
    private static String parseInterviewTarget(MemberAttendResult attendanceResult) {
        return attendanceResult.interviewee() != null ? String.format("%s 대상자입니다.", attendanceResult.interviewee()) : "";
    }
    
    public void handleExpelMeasurementResults(List<ExpelMeasurementResult> expelMeasurementResults) {
        StringBuilder sb = new StringBuilder("제적 위험자 조회 결과\n");
        
        for (ExpelMeasurementResult expelMeasurementResult : expelMeasurementResults) {
            sb.append(String.format("""
                            - %s: 결석 %d회, 지각 %d회 (%s)
                            """,
                    expelMeasurementResult.targetName(),
                    expelMeasurementResult.absentCount(),
                    expelMeasurementResult.lateCount(),
                    expelMeasurementResult.measurementName()
            ));
        }
        outputHandler.handle(sb.toString());
    }
    
    public void handleMissDecision() {
        outputHandler.handle("잘못 입력하셨습니다.");
    }
}
