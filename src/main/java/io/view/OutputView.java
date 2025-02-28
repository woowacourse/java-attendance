package io.view;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.ExpelWarning;
import io.dto.ExpelWarningCrewResponse;
import io.writer.Writer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;

public class OutputView {
    
    private final Writer writer;
    
    public OutputView(final Writer writer) {
        this.writer = writer;
    }
    
    public void outputAttendResult(
            final LocalDate attendDate,
            final LocalTime attendTime,
            final AttendanceStatus status
    ) {
        writer.writeLine("\n" + parseDateTimeStatus(attendDate, attendTime, status) + "\n");
    }
    
    public void outputAttendanceModifyResult(
            final LocalDate date,
            final LocalTime oldTime,
            final AttendanceStatus oldStatus,
            final LocalTime newTime,
            final AttendanceStatus newStatus
    ) {
        String output = "\n%s -> %s 수정 완료\n".formatted(
                parseDateTimeStatus(date, oldTime, oldStatus),
                parseTimeAndStatus(newTime, newStatus)
        );
        writer.writeLine(output);
    }
    
    public void outputAttendanceModifyResult(
            final LocalDate date,
            final LocalTime newTime,
            final AttendanceStatus newStatus
    ) {
        String output = "\n%s --:-- (결석) -> %s 수정 완료\n".formatted(
                parseDate(date),
                parseTimeAndStatus(newTime, newStatus)
        );
        writer.writeLine(output);
    }
    
    public void outputAttendanceRecords(
            final String nickname,
            final Set<Attendance> attendanceResponses,
            final int attendCount,
            final int lateCount,
            final int absentCount,
            final ExpelWarning expelWarning
    ) {
        String output = """
                
                이번 달 %s의 출석 기록입니다.
                
                %s
                
                출석: %d회
                지각: %d회
                결석: %d회
                """.formatted(
                nickname,
                parseAttendances(attendanceResponses),
                attendCount,
                lateCount,
                absentCount
        ) + parseExpelWarning(expelWarning);
        writer.writeLine(output);
    }
    
    public void outputExpelWarningCrews(final Set<ExpelWarningCrewResponse> expelWarningCrewResponses) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n")
                .append("제적 위험자 조회 결과")
                .append("\n");
        
        expelWarningCrewResponses.stream()
                .sorted(new ExpelWarningCrewResponseComparator())
                .forEach(response -> sb.append(parseExpelWarningResponse(response)).append("\n"));
        
        writer.writeLine(sb.toString());
    }
    
    private static class ExpelWarningCrewResponseComparator implements Comparator<ExpelWarningCrewResponse> {
        
        @Override
        public int compare(final ExpelWarningCrewResponse o1, final ExpelWarningCrewResponse o2) {
            
            if (calculateSortingValue(o1) != calculateSortingValue(o2)) {
                return -Integer.compare(calculateSortingValue(o1), calculateSortingValue(o2));
            }
            
            if (o1.lateCount() != o2.lateCount()) {
                return -Integer.compare(o1.lateCount(), o2.lateCount());
            }
            
            return o1.nickname().compareTo(o2.nickname());
        }
        
        private int calculateSortingValue(final ExpelWarningCrewResponse response) {
            return (response.lateCount() / 3) + response.absentCount();
        }
    }
    
    private String parseExpelWarningResponse(final ExpelWarningCrewResponse response) {
        return "- %s: 결석 %d회, 지각 %d회 (%s)".formatted(
                response.nickname(),
                response.absentCount(),
                response.lateCount(),
                response.expelWarning().name()
        );
    }
    
    private String parseAttendances(final Set<Attendance> attendances) {
        return attendances.stream()
                .sorted(Comparator.comparing(Attendance::getAttendDate))
                .map(attendance -> parseDateTimeStatus(
                        attendance.getAttendDate(),
                        attendance.getAttendTime().getAttendTime().get(),
                        attendance.getStatus()))
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse("");
    }
    
    private String parseExpelWarning(final ExpelWarning expelWarning) {
        if (expelWarning == ExpelWarning.정상) {
            return "";
        }
        return "\n%s 대상자입니다.\n".formatted(expelWarning.name());
    }
    
    private String parseDateTimeStatus(final LocalDate date, final LocalTime time, final AttendanceStatus status) {
        return parseDate(date) + " " + parseTimeAndStatus(time, status);
    }
    
    private String parseDate(final LocalDate date) {
        return "%02d월 %02d일 %s".formatted(
                date.getMonth().getValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        );
    }
    
    private String parseTimeAndStatus(final LocalTime time, final AttendanceStatus status) {
        if (time == null) {
            return "--:-- (%s)".formatted(status.name());
        }
        
        return "%02d:%02d (%s)".formatted(
                time.getHour(),
                time.getMinute(),
                status.name()
        );
    }
}
