package controller;

import controller.dto.AttendanceResult;
import domain.Attendance;
import domain.AttendanceManager;
import domain.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalTime;
import util.ApplicationDate;
import view.ConsoleView;

public class AttendanceCheckExecutor implements MenuExecutor{

    private final ConsoleView consoleView;
    private final ApplicationDate applicationDate;
    private final AttendanceManager attendanceManager;

    public AttendanceCheckExecutor(
            ConsoleView consoleView,
            ApplicationDate applicationDate,
            AttendanceManager attendanceManager
    ) {
        this.consoleView = consoleView;
        this.applicationDate = applicationDate;
        this.attendanceManager = attendanceManager;
    }

    @Override
    public void execute() {
        String nickName = consoleView.requestNickName();
        LocalDate date = applicationDate.getApplicationDate();
        AttendanceTime attendanceTime = requestAttendanceTime();

        attendanceManager.processAttendance(nickName, date, attendanceTime);

        Attendance attendance = attendanceManager.retrieveAttendance(nickName, date);
        consoleView.printAttendanceResult(AttendanceResult.from(attendance));
    }

    private AttendanceTime requestAttendanceTime() {
        LocalTime inputTime = consoleView.requestAttendanceTime();
        return new AttendanceTime(inputTime.getHour(), inputTime.getMinute());
    }
}
