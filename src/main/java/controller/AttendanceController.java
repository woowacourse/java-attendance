package controller;

import static util.Constants.*;

import domain.Attendance;
import domain.AttendanceBook;
import domain.CrewName;
import dto.AttendanceCount;
import dto.AttendanceLog;
import dto.ModifyingResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceBook attendanceBook;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
        this.inputView = new InputView(attendanceBook);
        this.outputView = new OutputView();
    }

    public void run() {
        Map<MenuOption, Runnable> menuActions = Map.of(
                MenuOption.ADDING_ATTENDANCE, this::addAttendance,
                MenuOption.UPDATING_ATTENDANCE, this::modifyAttendance,
                MenuOption.SHOWING_CREW_ATTENDANCE_HISTORY, this::showAttendanceHistory,
                MenuOption.SHOWING_PENALTY_CREWS, this::showPenaltyCrews
        );
        String selectedMenu = inputView.readSelectedMenu();
        menuActions.getOrDefault(MenuOption.from(selectedMenu), () -> {}).run();
    }

    private void addAttendance() {
        String name = handleWithRetry(inputView::readName);
        String time = handleWithRetry(inputView::readAttendanceTime);
        CrewName crewName = new CrewName(name);
        Attendance attendance = new Attendance(TODAY, LocalTime.parse(time));
        handleWithErrorMessage(() -> attendanceBook.addAttendance(crewName, attendance));
        outputView.showAttendanceResult(attendance);
    }

    private void modifyAttendance() {
        String crewName = handleWithRetry(inputView::readModifyName);
        String day = handleWithRetry(inputView::readModifyDay);
        String time = handleWithRetry(inputView::readModifyTime);
        ModifyingResult modifyingResult = attendanceBook.modify(
                new CrewName(crewName),
                new Attendance(
                        LocalDate.of(TODAY.getYear(), TODAY.getMonth(), Integer.parseInt(day)),
                        LocalTime.parse(time)));
        outputView.showModifyingResult(modifyingResult);
    }

    private void showAttendanceHistory() {
        String name = handleWithRetry(inputView::readName);
        CrewName crewName = new CrewName(name);
        AttendanceLog attendanceLog = attendanceBook.findAttendanceLogUntil(crewName, YESTERDAY);
        AttendanceCount attendanceCount = attendanceBook.findCountUntil(crewName, YESTERDAY);
        outputView.showAttendanceHistory(attendanceLog, attendanceCount);
    }

    private void showPenaltyCrews() {
        outputView.showPenaltyCrews(attendanceBook.findPenaltyCrewsSortedUntil(YESTERDAY));
    }

    private <T> T handleWithRetry(Supplier<T> task) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                outputView.showErrorMessage(e.getMessage());
            }
        }
    }

    private void handleWithErrorMessage(Runnable task) {
        try {
            task.run();
        } catch (IllegalArgumentException e) {
            outputView.showErrorMessage(e.getMessage());
        }
    }
}
