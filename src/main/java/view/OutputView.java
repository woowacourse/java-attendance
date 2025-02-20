package view;

import dto.result.AttendResult;
import dto.result.ExpelMeasurementResult;
import dto.result.MemberAttendResult;
import dto.result.MemberAttendanceModifyResult;
import util.dataTimeProvider.DateProvider;
import util.outputHandler.OutputHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OutputView {
    
    private final OutputHandler outputHandler;
    private final DateProvider dateProvider;
    
    public OutputView(OutputHandler outputHandler, DateProvider dateProvider) {
        this.outputHandler = outputHandler;
        this.dateProvider = dateProvider;
    }
    
    public void handleAttendResult(AttendResult attendResult) {
        var result = parseAttendResultValue(attendResult);
        outputHandler.handle(result);
    }
    
    private static String parseAttendResultValue(AttendResult attendResult) {
        return String.format("%s %s (%s)",
                parseDate(attendResult.attendanceDateTime().toLocalDate()),
                parseTime(attendResult),
                attendResult.attendanceStatus()
        );
    }
    
    private static String parseTime(AttendResult attendResult) {
        if (!attendResult.triedAttend()) {
            return "--:--";
        }
        return String.format("%02d:%02d", attendResult.attendanceDateTime().getHour(), attendResult.attendanceDateTime().getMinute());
    }
    
    public void handleAttendanceModifyResult(MemberAttendanceModifyResult memberAttendanceModifyResult) {
        
        String result = String.format("%s %s (%s) -> %s (%s) 수정 완료!",
                parseDate(memberAttendanceModifyResult.attendanceDate()),
                parseTime(memberAttendanceModifyResult.oldAttendanceTime()),
                parseStatus(memberAttendanceModifyResult.oldAttendanceStatus()),
                parseTime(memberAttendanceModifyResult.newAttendanceTime()),
                memberAttendanceModifyResult.newAttendanceStatus()
        );
        System.out.println(result);
    }
    
    private static String parseStatus(String memberAttendanceModifyResult) {
        if (memberAttendanceModifyResult == null) {
            return "결석";
        }
        return memberAttendanceModifyResult;
    }
    
    
    private static String parseDate(LocalDate localDate) {
        return String.format("%02d월 %02d일 %s",
                localDate.getMonth().getValue(),
                localDate.getDayOfMonth(),
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        );
    }
    
    private static String parseTime(LocalTime localTime) {
        if (localTime == null) {
            return "--:--";
        }
        
        return String.format("%02d:%02d", localTime.getHour(), localTime.getMinute());
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
                parseAttendResults(attendanceResult.attendanceResults(), dateProvider.now()),
                attendanceResult.attendCount(),
                attendanceResult.lateCount(),
                attendanceResult.absentCount(),
                parseInterviewTarget(attendanceResult)
        );
        
        outputHandler.handle(result);
    }
    
    private static String parseAttendResults(List<AttendResult> attendResults, LocalDate now) {
        var results = new ArrayList<>(attendResults);
        
        List<AttendResult> attendResultsToAdd = new ArrayList<>();
        
        for (int i = 1; i <= now.getDayOfMonth(); i++) {
            boolean isExist = false;
            for (AttendResult attendResult : results) {
                if (attendResult.attendanceDateTime().toLocalDate().getDayOfMonth() == i) {
                    isExist = true;
                    break;
                }
            }
            
            if (!isExist) {
                attendResultsToAdd.add(new AttendResult(LocalDateTime.of(now.withDayOfMonth(i), LocalTime.of(12, 0)), "결석", false));
                
            }
            
        }
        
        results.addAll(attendResultsToAdd);
        
        return results.stream()
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
