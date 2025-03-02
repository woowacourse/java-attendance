package view;

import dto.AttendanceDetails;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import util.DateTimeConvertor;

public class ConsoleOutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final Map<Integer, String> ATTENDANCE_STATUS = Map.of(
            1, "출석",
            2, "지각",
            3, "결석"
    );

    public void intro(final LocalDate localDate) {
        final String message = String.format("""
                 오늘은 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """, DateTimeConvertor.convertToLocalDateKoreanFormat(localDate));
        printMessage(LINE_SEPARATOR + message);
    }

    public void askCrewNickName() {
        printlnMessage(LINE_SEPARATOR + "닉네임을 입력해 주세요.");
    }

    public void askCrewNicknameForModification() {
        printlnMessage(LINE_SEPARATOR + "출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
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
        printlnMessage(LINE_SEPARATOR + String.format("%s -> %s", beforeAttendanceMessage, afterAttendanceMessage));
    }

    public void printAttendanceDetails(final AttendanceDetails attendanceDetails) {
        printlnMessage(LINE_SEPARATOR + convertToAttendanceDetailsMessage(attendanceDetails));
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
}
