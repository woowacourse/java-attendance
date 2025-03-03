package controller;

import domain.AttendanceState;
import domain.Attendances;
import domain.Calender;
import domain.DateProvider;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.FileManager;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final DateProvider dateProvider;
    private final Attendances attendances;

    public AttendanceController(final InputView inputView, final OutputView outputView,
                                final DateProvider dateProvider, final String filaPath) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.dateProvider = dateProvider;
        this.attendances = FileManager.readFile(filaPath);
    }

    public void run() {
        String command;
        do {
            outputView.printWellComeMessage(dateProvider);
            command = inputView.readOption();
            executeFeature(command);
        } while (isExit(command));
    }

    private void executeFeature(String feature) {
        FeatureType featureType = FeatureType.findBy(feature);
        featureType.execute(this);
    }

    boolean isExit(final String feature) {
        return !FeatureType.isExitType(feature);
    }

    void attendanceCheck() {
        Calender.validateHolyDay(dateProvider.getLocalDate());

        String name = inputName();
        LocalDateTime attendanceDateTime = inputAttendanceDateTime();

        attendances.checkAttendance(name, attendanceDateTime);
        AttendanceState attendanceState = AttendanceState.findStateBy(attendanceDateTime);

        outputView.printAttendanceRecord(attendanceDateTime, attendanceState);
    }

    private String inputName() {
        String name = inputView.readName();
        attendances.validateFindCrew(name);
        return name;
    }

    private LocalDateTime inputAttendanceDateTime() {
        LocalTime attendanceTime = inputView.readAttendanceTime();
        return dateProvider.createLocalDateTime(attendanceTime);
    }

    void attendanceUpdate() {
        String name = inputView.readUpdateName();
        int updateDate = inputView.readUpdateDate();
        LocalTime updateTime = inputView.readUpdateTime();

        LocalDateTime updateLocalDateTime = dateProvider.createLocalDateTime(updateDate, updateTime);

        LocalDateTime beforeAttendance = attendances.getAttendanceRecordBy(name, updateLocalDateTime.toLocalDate());
        AttendanceState beforeAttendanceState = AttendanceState.findStateBy(beforeAttendance);

        attendances.updateAttendance(name, updateLocalDateTime);

        LocalDateTime afterAttendance = attendances.getAttendanceRecordBy(name, updateLocalDateTime.toLocalDate());
        AttendanceState afterAttendanceState = AttendanceState.findStateBy(afterAttendance);

        outputView.printUpdateAttendanceRecord(beforeAttendance, beforeAttendanceState, afterAttendance,
                afterAttendanceState);


    }

    void attendanceHistory() {

    }

    void dismissalHistory() {

    }

    void exit() {
        outputView.printExit();
    }
}
