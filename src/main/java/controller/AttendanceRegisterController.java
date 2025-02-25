package controller;

import domain.Attendance;
import domain.AttendanceTime;
import domain.Campus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.RepeatExecutor;

public class AttendanceRegisterController implements AttendanceController {

    @Override
    public void process(Attendance attendance, LocalDate nowDate) {
        Campus.validateCampusOpenDate(nowDate);

        String nickName = RepeatExecutor.repeatUntilSuccess(this::processNickNameInput, outputView::printErrorMessage, attendance);
        RepeatExecutor.repeatUntilSuccess(() -> processAttend(attendance, nowDate, nickName), outputView::printErrorMessage);

        AttendanceTime attendanceTime = attendance.findAttendanceTime(nickName, nowDate);
        outputView.printCheckAttendanceMessage(attendanceTime);
    }

    private void processAttend(Attendance attendance, LocalDate nowDate, String nickName) {
        LocalTime arrivalTime = inputView.readArrivalTime();
        LocalDateTime arrivalDateTime = LocalDateTime.of(nowDate, arrivalTime);
        attendance.attend(nickName, arrivalDateTime);
    }

    private String processNickNameInput(Attendance attendance) {
        String nickName = inputView.readNickname();
        attendance.validateNickName(nickName);
        return nickName;
    }
}
