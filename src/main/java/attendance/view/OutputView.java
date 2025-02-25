package attendance.view;

import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceStatus;
import attendance.domain.DateTimeFormatterWrapper;
import attendance.view.dto.AttendanceStatusCount;
import attendance.view.dto.ModifyAttendanceDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class OutputView {

    private final String METHOD = "1. 출석 확인\n"
            + "2. 출석 수정\n"
            + "3. 크루별 출석 기록 확인\n"
            + "4. 제적 위험자 확인\n"
            + "Q. 종료";
    private final String INPUT_NICKNAME = "닉네임을 입력해 주세요.";
    private final String INPUT_ATTENDANCE_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final String INPUT_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private final String INPUT_ATTENDANCE_MODIFY_DATE = "수정하려는 날짜(일)를 입력해 주세요.";
    private final String ATTENDANCE_RESULT_FORMAT = "%s (%s)";
    private final String ATTENDANCE_MODIFY_RESULT_FORMAT = "%s (%s) -> %s (%s) 수정 완료!";
    private final static String CREW_DISMISS_PREFIX = "제적 위험자 조회 결과\n";
    private final static String CREW_DISMISS_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
    private final String CREW_ATTENDANCE_HISTORY_PREFIX = "이번 달 %s의 출석 기록입니다.\n\n";
    private final String ATTENDANCE_HISTORY_STATUS_FORMAT = "\n출석: %d회\n지각: %d회\n결석: %d회\n";
    private final String ATTENDANCE_DISMISS_STATUS_FORMAT = "\n%s 대상자입니다.";


    public void printMethod() {
        println(DateTimeFormatterWrapper.formattingToday(LocalDate.now()));
        println(METHOD);
    }

    public void println(String string) {
        System.out.println(string);
    }

    public void printAttendanceModifyNicknameInput() {
        println(INPUT_ATTENDANCE_MODIFY_NICKNAME);
    }

    public void printAttendanceModifyDateInput() {
        println(INPUT_ATTENDANCE_MODIFY_DATE);
    }

    public void printNicknameInput() {
        println(INPUT_NICKNAME);
    }

    public void printAttendanceTimeInput() {
        println(INPUT_ATTENDANCE_TIME);
    }

    public void printAttendanceResult(String attendanceStatus, LocalTime attendanceTime,
                                      LocalDate attendanceDate) {
        var dateTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceResult(
                LocalDateTime.of(attendanceDate, attendanceTime));
        println(String.format(ATTENDANCE_RESULT_FORMAT, dateTimeFormatResult, attendanceStatus));
    }

    public void printAttendanceHistory(List<AttendanceHistory> attendanceHistories, String nickname) {
        StringBuilder stringBuilder = new StringBuilder(String.format(CREW_ATTENDANCE_HISTORY_PREFIX, nickname));
        for (AttendanceHistory attendanceHistory : attendanceHistories) {
            String attendanceTimeHistory = formattingAttendanceHistory(attendanceHistory.attendanceStatus(),
                    attendanceHistory.attendanceDateTime());
            AttendanceStatus attendanceStatus = attendanceHistory.attendanceStatus();
            stringBuilder.append(
                    String.format(ATTENDANCE_RESULT_FORMAT, attendanceTimeHistory, attendanceStatus.getStatus()));
        }
        println(stringBuilder.toString());
    }

    private String formattingAttendanceHistory(AttendanceStatus attendanceStatus, LocalDateTime localDateTime) {
        if (attendanceStatus == AttendanceStatus.ABSENCE) {
            return DateTimeFormatterWrapper.formattingAttendanceAbsenceHistory(localDateTime.toLocalDate());
        }
        return DateTimeFormatterWrapper.parsingAttendanceResult(localDateTime);
    }

    public String crewDismiss(String nickname, AttendanceStatusCount attendanceStatusCount,
                              String attendanceDismissStatus) {
        return String.format(CREW_DISMISS_FORMAT, nickname, attendanceStatusCount.absence(),
                attendanceStatusCount.late(),
                attendanceDismissStatus);
    }

    public void printCrewDismisses(String crewDismissHistories) {
        println(CREW_DISMISS_PREFIX + crewDismissHistories);
    }

    public void printModifyAttendance(ModifyAttendanceDto previousModifyAttendance,
                                      ModifyAttendanceDto afterModifyAttendanceDto) {
        var previousDateTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceResult(
                previousModifyAttendance.attendanceDateTime());
        var afterTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceTime(
                afterModifyAttendanceDto.getTime());
        println(String.format(ATTENDANCE_MODIFY_RESULT_FORMAT, previousDateTimeFormatResult,
                previousModifyAttendance.status(),
                afterTimeFormatResult,
                afterModifyAttendanceDto.status()));
    }

    public void printCrewDismisses(String attendanceDismissStatus,
                                   AttendanceStatusCount attendanceStatusCount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(ATTENDANCE_HISTORY_STATUS_FORMAT, attendanceStatusCount.attendance(),
                attendanceStatusCount.late(), attendanceStatusCount.absence()));
        stringBuilder.append(String.format(ATTENDANCE_DISMISS_STATUS_FORMAT, attendanceDismissStatus));
        println(stringBuilder.toString());
    }
}
