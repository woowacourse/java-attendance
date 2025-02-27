package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import view.dto.AttendanceHistoryDto;
import view.dto.CrewDismissHistoryDto;

public class OutputView {
    private static final String INPUT_METHOD = "기능을 선택해 주세요.\n1. 출석 확인\n"
            + "2. 출석 수정\n"
            + "3. 크루별 출석 기록 확인\n"
            + "4. 제적 위험자 확인\n"
            + "Q. 종료";

    public static final String TODAY_FORMAT = "\n오늘은 MM월 dd일 E요일입니다. ";
    private static final DateTimeFormatter TODAY_FORMATTER = DateTimeFormatter.ofPattern(TODAY_FORMAT,
            Locale.KOREA);
    private static final String INPUT_NICKNAME = "닉네임을 입력해주세요.";
    private static final String INPUT_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String ADD_ATTENDANCE_INPUT_TIME = "등교 시간을 입력해 주세요.";
    private static final String DATE_DAY_WEEK_TIME_FORMAT = "MM월 dd일 E요일 HH:mm";
    private static final String ABSENCE_DATE_DAY_WEEK_FORMAT = "MM월 dd일 E요일 --:--";
    private static final DateTimeFormatter ABSENCE_DATE_DAY_WEEK_FORMATTER = DateTimeFormatter.ofPattern(
            ABSENCE_DATE_DAY_WEEK_FORMAT,
            Locale.KOREA);
    private static final DateTimeFormatter DATE_DAY_WEEK_FORMATTER = DateTimeFormatter.ofPattern(
            DATE_DAY_WEEK_TIME_FORMAT,
            Locale.KOREA);
    private static final String TIME_FORMAT = "HH:mm";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.KOREA);
    private static final String ATTENDANCE_STATUS_FORMAT = " (%s) ";
    private static final String NEXT_ATTENDANCE_SEPARATOR = "-> ";
    private static final String NEW_LINE = "\n";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String INPUT_ATTENDANCE_MODIFY_DATE = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String CREW_DISMISS_COUNT_FORMAT = "출석: %d회\n"
            + "지각: %d회\n"
            + "결석: %d회";
    private static final String DISMISS_STATUS_FORMAT = "%s 대상자입니다.";
    private static final String CREW_ATTENDANCE_HISTORY_START_FORMAT = "이번 달 %s의 출석 기록입니다.\n";
    private static final String CREW_DISMISS_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)\n";


    public void printError(String errorMessage) {
        println(ERROR_PREFIX + errorMessage);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printInputMethod() {
        LocalDate date = LocalDate.now();
        String inputToday = date.format(TODAY_FORMATTER);
        println(inputToday);
        println(INPUT_METHOD);
    }

    public void printInputNickname() {
        println(INPUT_NICKNAME);
    }

    public void printInputModifyNickname() {
        println(INPUT_MODIFY_NICKNAME);
    }

    public void printAddAttendanceInputTime() {
        println(ADD_ATTENDANCE_INPUT_TIME);
    }


    private void print(String message) {
        System.out.print(message);
    }

    private void printAttendanceStatus(String status) {
        print(String.format(ATTENDANCE_STATUS_FORMAT, status));
    }

    private void printAttendanceTime(LocalTime attendanceTime) {
        String resultAttendanceTime = attendanceTime.format(TIME_FORMATTER);
        print(resultAttendanceTime);
    }

    public void printInputDate() {
        println(INPUT_ATTENDANCE_MODIFY_DATE);
    }

    public void printPreviousAttendance(LocalDate attendanceDate, LocalTime attendanceTime,
                                        String status) {
        printAttendanceDateTimeStatus(status, attendanceTime, attendanceDate);
        print(NEXT_ATTENDANCE_SEPARATOR);
    }

    public void printAttendanceDateTimeStatus(String status, LocalTime attendanceTime, LocalDate attendanceDate) {
        LocalDateTime dateTime = LocalDateTime.of(attendanceDate, attendanceTime);
        String attendanceDateTime = dateTime.format(DATE_DAY_WEEK_FORMATTER);
        print(attendanceDateTime);
        printAttendanceTime(attendanceTime);
        printAttendanceStatus(status);
    }

    public void newLine() {
        print(NEW_LINE);
    }

    public void printAttendanceTimeStatus(LocalTime attendanceTime, String status) {
        printAttendanceTime(attendanceTime);
        printAttendanceStatus(status);
    }

    public void printCrewDismissCount(int attendance, int late, int absence) {
        println(String.format(CREW_DISMISS_COUNT_FORMAT, attendance, late, absence));
    }

    public void printCrewDismissStatus(String dismissStatus) {
        println(String.format(DISMISS_STATUS_FORMAT, dismissStatus));
    }

    public void printCrewAttendanceHistory(List<AttendanceHistoryDto> attendanceHistoriesDto, String crewNickname) {
        StringBuilder stringBuilder = new StringBuilder(
                String.format(CREW_ATTENDANCE_HISTORY_START_FORMAT, crewNickname));
        for (AttendanceHistoryDto attendanceHistoryDto : attendanceHistoriesDto) {
            stringBuilder.append(formattingAbsenceCheckedDateDayWeek(attendanceHistoryDto));
            stringBuilder.append(String.format(ATTENDANCE_STATUS_FORMAT, attendanceHistoryDto.attendanceStatus()));
            stringBuilder.append(NEW_LINE);
        }
        println(stringBuilder.toString());
    }

    private String formattingAbsenceCheckedDateDayWeek(AttendanceHistoryDto attendanceHistoryDto) {
        LocalDateTime dateTime = LocalDateTime.of(attendanceHistoryDto.attendanceDate(),
                attendanceHistoryDto.attendanceTime());
        if (attendanceHistoryDto.isAbsence()) {
            return dateTime.format(ABSENCE_DATE_DAY_WEEK_FORMATTER);
        }
        return dateTime.format(DATE_DAY_WEEK_FORMATTER);
    }

    public void printCrewDismisses(List<CrewDismissHistoryDto> crewDismissHistoryDtos) {
        StringBuilder crewDismissesHistoryBuilder = new StringBuilder();
        for (CrewDismissHistoryDto crewDismissHistoryDto : crewDismissHistoryDtos) {
            String nickname = crewDismissHistoryDto.crewNickname();
            int absence = crewDismissHistoryDto.absence();
            int late = crewDismissHistoryDto.late();
            String status = crewDismissHistoryDto.attendanceStatus();
            crewDismissesHistoryBuilder.append(String.format(CREW_DISMISS_FORMAT, nickname, absence, late, status));
        }
        println(crewDismissesHistoryBuilder.toString());
    }
}


