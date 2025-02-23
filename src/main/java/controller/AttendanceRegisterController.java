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

        String nickName = processNickNameInput(attendance);
        repeatExecutor.repeatUntilSuccess(() -> {
            LocalTime arrivalTime = processArrivalTimeInput();
            attendance.attend(nickName, LocalDateTime.of(nowDate, arrivalTime));
            return RepeatExecutor.SUCCESS;
        });

        AttendanceTime attendanceTime = attendance.findAttendanceTime(nickName, nowDate);
        outputView.printCheckAttendanceMessage(attendanceTime);
    }

    private String processNickNameInput(Attendance attendance) {
        return repeatExecutor.repeatUntilSuccess(() -> {
            String nickName = inputView.readNickname();
            attendance.validateNickName(nickName);
            return nickName;
        });
    }

    private LocalTime processArrivalTimeInput() {
        return repeatExecutor.repeatUntilSuccess(inputView::readArrivalTime);
    }
}
