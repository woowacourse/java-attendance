package controller;

import domain.AttendanceDate;
import domain.AttendanceState;
import domain.Attendances;
import domain.Calender;
import domain.Crew;
import domain.DateProvider;
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
        LocalDateTime attendanceDateTime = inputAttendanceDateTime();

        attendances.checkAttendance(name, attendanceDateTime);
        AttendanceState attendanceState = AttendanceState.findStateBy(attendanceDateTime);

        outputView.printAttendanceRecord(attendanceDateTime, attendanceState);
    }

    void attendanceUpdate() {
        String name = inputUpdateName();
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
        String name = inputName();
        Map<LocalDateTime, AttendanceState> attendanceHistory = attendances.getHistory(name,
                attendanceDate.getLocalDate());

        outputView.printAttendanceHistory(name, history);
        Map<AttendanceState, Integer> attendanceStateCounts = attendances.calculate(history);

        Integer lateCount = attendanceStateCounts.get(AttendanceState.LATE);
        Integer absenceCount = attendanceStateCounts.get(AttendanceState.ABSENCE);
        Dismissal dismissal = Dismissal.findDismissalBy(lateCount, absenceCount);

        outputView.printAttendanceStateCounts(attendanceStateCounts, dismissal);
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
