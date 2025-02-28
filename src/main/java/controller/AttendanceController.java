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
        CrewName crewName = new CrewName(inputView.readName());
        LocalTime time = LocalTime.parse(inputView.readAttendanceTime());
        Attendance attendance = new Attendance(TODAY, time);
        attendanceBook.addAttendance(crewName, attendance);
        outputView.showAttendanceResult(attendance);
    }

    private void modifyAttendance() {
        CrewName crewName = getValidModifyName();
        int day = getValidDay();
        LocalTime time = getValidModifyTime();
        Attendance attendance = new Attendance(LocalDate.of(TODAY.getYear(), TODAY.getMonth(), day), time);
        ModifyingResult modifyingResult = attendanceBook.modify(crewName, attendance);
        outputView.showModifyingResult(modifyingResult);
    }

    private CrewName getValidModifyName() {
        while(true) {
            try {
                return new CrewName(inputView.readModifyName());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getValidDay() {
        while(true) {
            try {
                return Integer.parseInt(inputView.readModifyDay());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private LocalTime getValidModifyTime() {
        while(true) {
            try {
                return LocalTime.parse(inputView.readModifyTime());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showAttendanceHistory() {
        CrewName crewName = new CrewName(inputView.readName());
        AttendanceLog attendanceLog = attendanceBook.findAttendanceLogUntil(crewName, YESTERDAY);
        AttendanceCount attendanceCount = attendanceBook.findCountUntil(crewName, YESTERDAY);
        outputView.showAttendanceHistory(attendanceLog, attendanceCount);
    }

    private void showPenaltyCrews() {
        outputView.showPenaltyCrews(attendanceBook.findPenaltyCrewsSortedUntil(YESTERDAY));
    }
}
