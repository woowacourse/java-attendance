package attendance.view;

import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceStatusChecker.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static attendance.view.GeneralView.*;

public class AttendanceConfirmView {


    public String readCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readOneLine();
    }

    public LocalDateTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String timeInput = readOneLine();
        try {
            LocalDate attendanceDate = LocalDate.now();
            LocalTime attendanceTime = LocalTime.parse(timeInput, TIME_FORMATTER);
            return LocalDateTime.of(attendanceDate, attendanceTime);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("시간을 올바르게 입력해 주세요.");
        }
    }

    public void printAttendanceResult(final AttendanceDateTime attendanceDateTime, final AttendanceStatus attendanceStatus) {
        AttendanceStatusTextMaker attendanceStatusTextMaker = new AttendanceStatusTextMaker();
        LocalDateTime attendanceLocalDateTime = attendanceDateTime.getLocalDateTime();
        String attendanceStatusText = attendanceStatusTextMaker.make(attendanceStatus);
        System.out.println(DATE_TIME_FORMATTER.format(attendanceLocalDateTime)
                + " (%s)".formatted(attendanceStatusText));
    }
}
