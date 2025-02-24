package controller;

import constant.CampusConstant;
import domain.Attendance;
import domain.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalTime;
import util.RepeatExecutor;

public class AttendanceEditController implements AttendanceController {

    @Override
    public void process(Attendance attendance, LocalDate nowDate) {
        String nickName = RepeatExecutor.repeatUntilSuccess(this::processEditNickNameInput, outputView::printErrorMessage, attendance);
        AttendanceTime oldAttendanceTime = RepeatExecutor.repeatUntilSuccess(this::processOldAttendanceTime, outputView::printErrorMessage, attendance, nickName);

        int editArrivalDate = oldAttendanceTime.getAttendanceDateTime().getDayOfMonth();
        LocalDate editDate = LocalDate.of(CampusConstant.YEAR, CampusConstant.DECEMBER_MONTH, editArrivalDate);

        editAttendanceTime(attendance, nickName, editArrivalDate);
        AttendanceTime newAttendanceTime = attendance.findAttendanceTime(nickName, editDate);
        outputView.printEditAttendanceMessage(oldAttendanceTime, newAttendanceTime);
    }

    private String processEditNickNameInput(Attendance attendance) {
        String nickName = inputView.readEditNickname();
        attendance.validateNickName(nickName);
        return nickName;
    }

    private AttendanceTime processOldAttendanceTime(Attendance attendance, String nickName) {
        int editArrivalDate = processEditArrivalDateInput();
        return attendance.findAttendanceTime(nickName, LocalDate.of(CampusConstant.YEAR, CampusConstant.DECEMBER_MONTH, editArrivalDate));
    }

    private void editAttendanceTime(Attendance attendance, String nickName, int editArrivalDate) {
        RepeatExecutor.repeatUntilSuccess(() -> {
            LocalTime editArrivalTime = inputView.readEditArrivalTime();
            attendance.edit(nickName, editArrivalDate, editArrivalTime);
        }, outputView::printErrorMessage);
    }

    private int processEditArrivalDateInput() {
        return inputView.readEditArrivalDate();
    }
}
