package controller;

import controller.dto.AfterAttendanceInfo;
import controller.dto.BeforeAttendanceInfo;
import domain.Attendance;
import domain.AttendanceManager;
import domain.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalTime;
import util.ApplicationDate;
import view.ConsoleView;

public class AttendanceUpdateExecutor implements MenuExecutor{

    private final ConsoleView consoleView;
    private final ApplicationDate applicationDate;
    private final AttendanceManager attendanceManager;

    public AttendanceUpdateExecutor(
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
        String nickName = consoleView.requestNickNameForUpdate();
        LocalDate updateDate = requestUpdateDate();
        AttendanceTime attendanceTime = requestUpdateApplicationTime();
        Attendance attendance = attendanceManager.retrieveAttendance(nickName, updateDate);

        BeforeAttendanceInfo beforeAttendanceInfo = BeforeAttendanceInfo.from(attendance);
        attendanceManager.updateAttendanceTime(attendance, attendanceTime);
        AfterAttendanceInfo afterAttendanceInfo = AfterAttendanceInfo.from(attendance);

        consoleView.printUpdateResult(beforeAttendanceInfo, afterAttendanceInfo);
    }

    private LocalDate requestUpdateDate() {
        LocalDate date = applicationDate.getApplicationDate();
        int dayOfMonth = consoleView.requestDayOfMonth();
        return LocalDate.of(date.getYear(), date.getMonth(), dayOfMonth);
    }

    private AttendanceTime requestUpdateApplicationTime() {
        LocalTime requestUpdateTime = consoleView.requestUpdateTime();
        return new AttendanceTime(requestUpdateTime.getHour(), requestUpdateTime.getMinute());
    }
}
