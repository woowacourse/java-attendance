package io.view;

import domain.AttendanceStatus;
import domain.ExpelRisk;
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
import java.util.Comparator;
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
    
    private static String parseStatus(AttendanceStatus memberAttendanceModifyResult) {
        if (memberAttendanceModifyResult == null) {
            return AttendanceStatus.결석.name();
        }
        return memberAttendanceModifyResult.name();
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
                parseAttendResults(attendanceResult.attendanceResults(), dateProvider.getCurrentDate()),
                attendanceResult.attendCount(),
                attendanceResult.lateCount(),
                attendanceResult.absentCount(),
                parseExpelRisk(attendanceResult.expelRisk())
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
                attendResultsToAdd.add(new AttendResult(LocalDateTime.of(now.withDayOfMonth(i), LocalTime.of(12, 0)), AttendanceStatus.결석, false));
            }
        }
        
        results.addAll(attendResultsToAdd);
        
        return results.stream()
                .map(OutputView::parseAttendResultValue)
                .reduce((str1, str2) -> str1 + "\n" + str2)
                .orElse("");
    }
    
    private static String parseExpelRisk(ExpelRisk expelRisk) {
        if (expelRisk == ExpelRisk.정상) {
            return "";
        }
        return String.format("%s 대상자입니다.", expelRisk.name());
    }
    
    public void handleExpelMeasurementResults(List<ExpelMeasurementResult> expelMeasurementResults) {
        StringBuilder sb = new StringBuilder("제적 위험자 조회 결과\n");
        
        expelMeasurementResults.stream()
                .sorted(new ExpelMeasurementResultComparator())
                .forEach(o -> sb.append(String.format("- %s: 결석 %d회, 지각 %d회 (%s)", o.targetName(), o.absentCount(), o.lateCount(), o.expelRisk().name())));
        outputHandler.handle(sb.toString());
    }
    
    private static final class ExpelMeasurementResultComparator implements Comparator<ExpelMeasurementResult> {
        @Override
        public int compare(ExpelMeasurementResult o1, ExpelMeasurementResult o2) {
            if (o1.expelRisk() != o2.expelRisk()) {
                return -Integer.compare(o1.expelRisk().getSeriousness(), o2.expelRisk().getSeriousness());
            }
            if (o1.lateCount() + o1.absentCount() != o2.lateCount() + o2.absentCount()) {
                return -Integer.compare(o1.lateCount() + o1.absentCount(), o2.lateCount() + o2.absentCount());
            }
            if (o1.absentCount() != o2.absentCount()) {
                return -Integer.compare(o1.absentCount(), o2.absentCount());
            }
            return o1.targetName().compareTo(o2.targetName());
        }
    }
    
    public void handleMissDecision() {
        outputHandler.handle("잘못 입력하셨습니다.");
    }
}
