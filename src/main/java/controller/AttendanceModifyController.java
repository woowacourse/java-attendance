package controller;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.CrewAttendanceStorage;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceModifyController implements Controller{
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewAttendanceStorage crewAttendanceStorage;

    public AttendanceModifyController(
            InputView inputView,
            OutputView outputView,
            CrewAttendanceStorage crewAttendanceStorage
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewAttendanceStorage = crewAttendanceStorage;
    }

    @Override
    public void run() {
        String crewName = inputView.readName();
        LocalDate modifyDate = inputView.readModifyDate();
        LocalTime modifyTime = inputView.readModifyTime();

        Attendance beforeAttendance = crewAttendanceStorage.findAttendance(crewName, modifyDate);
        crewAttendanceStorage.modify(crewName, modifyDate, modifyTime);
        Attendance afterAttendance = crewAttendanceStorage.findAttendance(crewName, modifyDate);

        LocalTime beforeTime = beforeAttendance.getTime();
        AttendanceStatus beforeStatus = beforeAttendance.getStatus();
        AttendanceStatus afterStatus = afterAttendance.getStatus();
        LocalTime modifiedTime = afterAttendance.getTime();

        outputView.printModifyResult(modifyDate, beforeTime, beforeStatus, modifiedTime, afterStatus);
    }
}
