package view;

import dto.AttendanceDetails;
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


    public void printAskNickName() {
        printlnMessage("닉네임을 입력해 주세요.");
    }

    public void printAskAttendanceTime() {
        printlnMessage("등교 시간을 입력해 주세요.");
    }

    public void printAttendanceDetails(final AttendanceDetails attendanceDetails) {
        printlnMessage(LINE_SEPARATOR + convertToAttendanceDetailsMessage(attendanceDetails));
    }

    public String convertToAttendanceDetailsMessage(final AttendanceDetails attendanceDetails) {
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
}
