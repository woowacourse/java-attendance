package attendance.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.HistoryStatistic;
import attendance.domain.SanctionLevel;
import attendance.utility.DateTimeFormatterWrapper;

public class OutputView {

    private final static String METHOD_MESSAGE = """
        1. 출석 확인
        2. 출석 수정
        3. 크루별 출석 기록 확인
        4. 제적 위험자 확인
        Q. 종료""";
    private final static String REQUEST_METHOD = "\n오늘은 MM월 d일 E요일입니다. 기능을 선택해 주세요.";
    private final static String REQUEST_NICKNAME = "닉네임을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_MODIFY_DATE = "수정하려는 날짜(일)를 입력해 주세요.";

    private static final String ATTENDANCE_FORMAT = "\nMM월 dd일 E요일 HH:mm (%s)";
    private static final String FORMAT_ATTENDANCE_NOT_EXISTING = "\nMM월 dd일 E요일 --:-- (결석) ";
    private static final String MODIFY_TIME_FORM = "-> HH:mm (%s)";

    private static final String INITIALIZE_HISTORY = "\n이번 달 %s의 출석 기록입니다.";
    private static final String FORMAT_STATE = "\n%s: %d회";
    private static final String SANCTION_LEVEL = "\n%s 대상자입니다.\n";

    private static final String INITIALIZE_SANCTION_STATISTIC = "\n제적 위험자 조회 결과";
    private static final String FORMAT_SANCTION = "\n- %s: 결석 %d회, 지각 %d회 (%s)";

    public void println(String string) {
        System.out.println(string);
    }

    public void printError(String message) {
        println(message);
    }

    public void printRequestMessage(LocalDateTime date) {
        String message = DateTimeFormatterWrapper.getFormatter(REQUEST_METHOD).format(date);
        println(message);
    }

    public void printMethod() {
        println(METHOD_MESSAGE);
    }

    public void printRequestNickname() {
        println(REQUEST_NICKNAME);
    }

    public void printRequestNicknameForModify() {
        println(REQUEST_ATTENDANCE_MODIFY_NICKNAME);
    }

    public void printRequestTime() {
        println(REQUEST_ATTENDANCE_TIME);
    }

    public void printRequestDate() {
        println(REQUEST_ATTENDANCE_MODIFY_DATE);
    }

    public void printlnRegister(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {
        String format = String.format(ATTENDANCE_FORMAT, attendanceStatus.getStatus());
        String formattedMessage = DateTimeFormatterWrapper.getFormatter(format).format(dateTime);

        println(formattedMessage);
    }

    public void printlnModifyOldAttendance(Attendance oldAttendance, Attendance newAttendance) {
        String formatOld = String.format(ATTENDANCE_FORMAT, oldAttendance.attendanceStatus().getStatus());
        String formattedMessageOld = DateTimeFormatterWrapper.getFormatter(formatOld).format(oldAttendance.dateTime());

        String formatNew = String.format(MODIFY_TIME_FORM, newAttendance.attendanceStatus().getStatus());
        String formattedMessageNew = DateTimeFormatterWrapper.getFormatter(formatNew).format(newAttendance.dateTime());

        println(formattedMessageOld + formattedMessageNew);
    }

    public void printlnModifyNewAttendance(Attendance newAttendance) {
        LocalTime time = newAttendance.dateTime().toLocalTime();
        String formatNew = String.format(MODIFY_TIME_FORM, newAttendance.attendanceStatus().getStatus());
        String formattedMessageNew = DateTimeFormatterWrapper.getFormatter(formatNew).format(time);

        println(FORMAT_ATTENDANCE_NOT_EXISTING + formattedMessageNew);
    }

    public void printlnInitializeHistory(String nickname) {
        println(String.format(INITIALIZE_HISTORY, nickname));
    }

    public void printlnHistory(Map<LocalDate, Optional<Attendance>> history) {
        List<LocalDate> sortedDates = history.keySet().stream()
            .sorted()
            .toList();
        StringBuilder builder = new StringBuilder();
        for (LocalDate date : sortedDates) {
            String formattedMessage = formatAttendanceMessage(date, history);
            builder.append(formattedMessage);
        }
        println(builder.toString());
    }

    private String formatAttendanceMessage(LocalDate date, Map<LocalDate, Optional<Attendance>> history) {
        Optional<Attendance> attendance = history.get(date);
        if (attendance.isPresent()) {
            Attendance attendanceData = attendance.get();
            String format = String.format(ATTENDANCE_FORMAT, attendanceData.attendanceStatus().getStatus());
            return DateTimeFormatterWrapper.getFormatter(format)
                .format(attendanceData.dateTime());
        }
        return DateTimeFormatterWrapper.getFormatter(FORMAT_ATTENDANCE_NOT_EXISTING)
            .format(date);
    }

    public void printlnHistoryStatistic(EnumMap<AttendanceStatus, Integer> statistic) {
        var builder = new StringBuilder();
        var keySet = statistic.keySet().stream().sorted(Comparator.reverseOrder()).toList();
        for (AttendanceStatus status : keySet) {
            String formattedMessage = String.format(FORMAT_STATE, status.getStatus(),
                statistic.getOrDefault(status, 0));
            builder.append(formattedMessage);
        }
        println(builder.toString());

    }

    public void printJudgedSanctionLevel(SanctionLevel sanctionLevel) {
        println(String.format(SANCTION_LEVEL, sanctionLevel.getValues()));
    }

    public void printlnInitializeSanctionStatistic() {
        println(INITIALIZE_SANCTION_STATISTIC);
    }

    public void printlnSanctionStatistic(List<HistoryStatistic> historyStatistics) {
        var builder = new StringBuilder();
        for (HistoryStatistic historyStatistic : historyStatistics) {
            String nickname = historyStatistic.nickname();
            int absenceCount = historyStatistic.statistic().getOrDefault(AttendanceStatus.ABSENCE, 0);
            int LateCount = historyStatistic.statistic().getOrDefault(AttendanceStatus.LATE, 0);
            String sanctionLevel = historyStatistic.judgeSanctionLevel().getValues();
            String formattedMessage = String.format(FORMAT_SANCTION, nickname, absenceCount, LateCount, sanctionLevel);
            builder.append(formattedMessage);
        }
        println(builder.toString());
    }
}
