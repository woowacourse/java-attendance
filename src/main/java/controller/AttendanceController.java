package controller;

import domain.AttendanceDate;
import domain.AttendanceState;
import domain.Attendances;
import domain.Crew;
import domain.Dismissal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import util.FileManager;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceDate attendanceDate;
    private final Attendances attendances;

    public AttendanceController(final InputView inputView, final OutputView outputView,
                                final AttendanceDate attendanceDate, final String filaPath) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceDate = attendanceDate;
        this.attendances = FileManager.readFile(filaPath);
    }

    public void run() {
        String command;
        do {
            outputView.printWellComeMessage(attendanceDate);
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
        attendanceDate.possibleAttendance();

        String name = inputName();
        if (isAlreadyAttendance(name)) {
            return;
        }
        LocalDateTime attendanceDateTime = inputAttendanceDateTime();
        attendanceDate.validateInTime(attendanceDateTime.toLocalTime());

        attendances.checkAttendance(name, attendanceDateTime);
        AttendanceState attendanceState = getAttendanceState(attendanceDateTime);

        outputView.printAttendanceRecord(attendanceDateTime, attendanceState);
    }

    private boolean isAlreadyAttendance(String name) {
        try {
            attendances.validateAlreadyAttendance(name, attendanceDate.getLocalDate());
        } catch (IllegalArgumentException e) {
            outputView.printAlreadyAttendance(e.getMessage());
            return true;
        }
        return false;
    }

    void attendanceUpdate() {
        String name = inputUpdateName();
        LocalDateTime updateDateTime = inputUpdateDateTime();

        LocalDateTime beforeAttendance = attendances.getAttendanceRecordBy(name, updateDateTime.toLocalDate());
        AttendanceState beforeAttendanceState = getAttendanceState(beforeAttendance);

        attendances.updateAttendance(name, updateDateTime);

        LocalDateTime afterAttendance = attendances.getAttendanceRecordBy(name, updateDateTime.toLocalDate());
        AttendanceState afterAttendanceState = getAttendanceState(afterAttendance);

        outputView.printUpdateAttendanceRecord(beforeAttendance, beforeAttendanceState, afterAttendance,
                afterAttendanceState);
    }

    private LocalDateTime inputUpdateDateTime() {
        int updateDate = inputView.readUpdateDate();
        LocalTime updateTime = inputView.readUpdateTime();

        return attendanceDate.createLocalDateTime(updateDate, updateTime);
    }

    private AttendanceState getAttendanceState(LocalDateTime attendanceDateTime) {
        return AttendanceState.findStateBy(attendanceDateTime);
    }

    void attendanceHistory() {
        String name = inputName();
        Map<LocalDateTime, AttendanceState> attendanceHistory = attendances.getHistory(name,
                attendanceDate.getLocalDate());

        outputView.printAttendanceHistory(name, attendanceHistory);
        Map<AttendanceState, Integer> attendanceStateCounts = attendances.calculate(attendanceHistory);

        Dismissal dismissal = getDismissal(attendanceStateCounts);

        outputView.printAttendanceStateCounts(attendanceStateCounts, dismissal);
    }

    private Dismissal getDismissal(Map<AttendanceState, Integer> attendanceStateCounts) {
        int lateCount = attendanceStateCounts.get(AttendanceState.LATE);
        int absenceCount = attendanceStateCounts.get(AttendanceState.ABSENCE);
        return Dismissal.findDismissalBy(lateCount, absenceCount);
    }

    void dismissalHistory() {
        Map<Crew, Map<AttendanceState, Integer>> absenceRecord = attendances.calculateAbsence(
                attendanceDate.getLocalDate());

        outputView.printAbsenceRecord(absenceRecord);
    }

    void exit() {
        outputView.printExit();
    }

    private String inputName() {
        String name = inputView.readName();
        attendances.validateFindCrew(name);
        return name;
    }

    private String inputUpdateName() {
        String name = inputView.readUpdateName();
        attendances.validateFindCrew(name);
        return name;
    }

    private LocalDateTime inputAttendanceDateTime() {
        LocalTime inputAttendanceTime = inputView.readAttendanceTime();
        return attendanceDate.createLocalDateTime(inputAttendanceTime);
    }
}
