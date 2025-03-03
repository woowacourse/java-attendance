package view;

import dto.AttendanceDetails;
import dto.AttendanceHistory;
import dto.PenaltyCrew;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import util.DateTimeConvertor;

public class ConsoleOutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String INTRO =
            """
                    오늘은 %s입니다. 기능을 선택해 주세요.
                    1. 출석 확인
                    2. 출석 수정
                    3. 크루별 출석 기록 확인
                    4. 제적 위험자 확인
                    Q. 종료""";
    private static final Map<Integer, String> ATTENDANCE_STATUS = Map.of(
            1, "출석",
            2, "지각",
            3, "결석"
    );
    private static final String ATTENDANCE_STATUS_COUNT =
            """
                    출석: %d회
                    지각: %d회
                    결석: %d회""";
    private static final Map<Integer, String> PENALTY = Map.of(
            1, "경고",
            2, "면담",
            3, "제적"
    );

    public void intro(final LocalDate localDate) {
        final String message = String.format(INTRO, DateTimeConvertor.convertToLocalDateKoreanFormat(localDate));
        printMessage(addLineSeparator(message));
    }

    public void askCrewNickName() {
        printMessage(addLineSeparator("닉네임을 입력해 주세요."));
    }

    public void askCrewNicknameForModification() {
        printMessage(addLineSeparator("출석을 수정하려는 크루의 닉네임을 입력해 주세요."));
    }

    public void askAttendanceDayForModification() {
        printlnMessage("수정하려는 날짜(일)를 입력해 주세요.");
    }

    public void askAttendanceTimeForModification() {
        printlnMessage("언제로 변경하겠습니까?");
    }

    public void askAttendanceTime() {
        printlnMessage("등교 시간을 입력해 주세요.");
    }

    public void printModifiedAttendanceDetails(final AttendanceDetails beforeAttendanceDetails,
                                               final AttendanceDetails afterAttendanceDetails) {
        final String beforeAttendanceMessage = convertToAttendanceDetailsMessage(beforeAttendanceDetails);
        final String afterAttendanceMessage = String.format("%s (%s) 수정 완료!",
                DateTimeConvertor.convertToLocalTimeKoreanFormat(afterAttendanceDetails.localTime()),
                ATTENDANCE_STATUS.get(afterAttendanceDetails.attendanceStatusCode()));
        printMessage(addLineSeparator(String.format("%s -> %s", beforeAttendanceMessage, afterAttendanceMessage)));
    }

    public void printAttendanceDetails(final AttendanceDetails attendanceDetails) {
        printMessage(addLineSeparator(convertToAttendanceDetailsMessage(attendanceDetails)));
    }

    public void printAttendanceHistory(final String crewName, final AttendanceHistory attendanceHistory) {
        final String historyHeader = String.format("이번 달 %s의 출석 기록입니다.", crewName);
        final String attendanceDetailsMessage = attendanceHistory.attendanceDetails().stream()
                .map(this::convertToAttendanceDetailsMessage)
                .collect(Collectors.joining(LINE_SEPARATOR));
        final String attendanceStatusMessage = String.format(
                ATTENDANCE_STATUS_COUNT, attendanceHistory.attendanceCount(), attendanceHistory.lateCount(),
                attendanceHistory.absenceCount());
        final String penaltyMessage = generatePenaltyMessage(attendanceHistory.penaltyCode());
        printMessage(appendMessage(historyHeader, attendanceDetailsMessage, attendanceStatusMessage, penaltyMessage));
    }

    public void printPenaltyCrews(final List<PenaltyCrew> penaltyCrews) {
        final String penaltyCrewsHeader = "제적 위험자 조회 결과";
        final String penaltyCrewsMessage = penaltyCrews.stream()
                .map(penaltyCrew -> String.format("- %s: 결석 %d회, 지각 %d회 (%s)", penaltyCrew.crewName(),
                        penaltyCrew.absenceCount(), penaltyCrew.lateCount(), PENALTY.get(penaltyCrew.penaltyCode())))
                .collect(Collectors.joining(LINE_SEPARATOR));
        printlnMessage(addLineSeparator(penaltyCrewsHeader) + penaltyCrewsMessage);
    }

    private String generatePenaltyMessage(final int penaltyCode) {
        String penaltyMessage = "";
        if (penaltyCode != 0) {
            penaltyMessage = String.format("%s 대상자입니다.", PENALTY.get(penaltyCode));
        }
        return penaltyMessage;
    }

    private String convertToAttendanceDetailsMessage(final AttendanceDetails attendanceDetails) {
        final String attendanceDateMessage = DateTimeConvertor.convertToLocalDateKoreanFormat(
                attendanceDetails.localDate());
        final LocalTime attendanceTime = attendanceDetails.localTime();
        String attendanceTimeMessage = "--:--";
        if (attendanceTime != null) {
            attendanceTimeMessage = DateTimeConvertor.convertToLocalTimeKoreanFormat(attendanceTime);
        }
        return String.format("%s %s (%s)", attendanceDateMessage, attendanceTimeMessage,
                ATTENDANCE_STATUS.get(attendanceDetails.attendanceStatusCode()));
    }

    private void printlnMessage(final String message) {
        System.out.println(message);
    }

    private void printMessage(final String message) {
        System.out.print(message);
    }

    private String appendMessage(final String... messages) {
        return Arrays.stream(messages)
                .map(this::addLineSeparator)
                .collect(Collectors.joining());
    }

    private String addLineSeparator(final String message) {
        return LINE_SEPARATOR + message + LINE_SEPARATOR;
    }
}
