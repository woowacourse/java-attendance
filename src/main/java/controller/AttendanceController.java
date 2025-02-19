package controller;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.MenuOption;
import java.awt.Menu;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import util.AttendancesFileHandler;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() throws IOException {
        LocalDate nowDate = LocalDate.now();
        Attendance attendance = new Attendance(AttendancesFileHandler.generateAttendances(), nowDate);

        outputView.printMenuHeader(nowDate);
        String option = inputView.readOption(Arrays.asList(MenuOption.values()));

        if (option.equals(MenuOption.ATTENDANCE_CHECK.getCommand())) {
            // 1. 출석 확인
            String nickName = inputView.readNickname();
            LocalTime arrivalTime = inputView.readArrivalTime();
            attendance.attend(nickName, LocalDateTime.of(nowDate, arrivalTime));

            LocalDateTime attendanceDateTime = attendance.getAttendanceDateTime(nickName, nowDate);
            AttendanceStatus attendanceStatus = attendance.getAttendanceStatus(nickName, nowDate);

            outputView.printAttendanceMessage(attendanceDateTime, attendanceStatus);
        }


        String nickName = inputView.readEditNickname();
    }
}
