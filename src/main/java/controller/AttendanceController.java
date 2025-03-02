package controller;

import domain.AttendanceBook;
import domain.AttendanceDate;
import global.utils.DateTimeUtil;
import view.InputView;
import view.Menu;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static global.utils.DateTimeUtil.*;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        AttendanceBook attendanceBook = new AttendanceBook();
        // TODO: 파일 입력
        attendanceBook.initAttendance("시소", LocalDateTime.of(2024, 12, 10, 10, 0));
        selectMenu(attendanceBook);
    }

    private void selectMenu(AttendanceBook attendanceBook) {
        outputView.printSelectMenuMessage();
        try {
            Menu menuItem = Menu.of(inputView.enterMenuItem());
            evaluateMenuItem(menuItem, attendanceBook);
        }
        catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            selectMenu(attendanceBook);
        }
    }

    private void evaluateMenuItem(Menu menuItem, AttendanceBook attendanceBook) {
        if (menuItem.equals(Menu.FIRST)) {
            attendMenu(attendanceBook);
        }
        if (menuItem.equals(Menu.SECOND)) {
            editMenu(attendanceBook);
        }
        if (menuItem.equals(Menu.THIRD)) {
            retrieveAttendanceMenu(attendanceBook);
        }
        if (menuItem.equals(Menu.FOURTH)) {
            retrieveRiskStatusMenu(attendanceBook);
        }
    }

    private void attendMenu(AttendanceBook attendanceBook) {
        attendanceBook.validateIsWeekday(DateTimeUtil.getFixedRunningDate());
        attendanceBook.validateIsInRunningTime(DateTimeUtil.getFixedRunningTime());
        String name = enterCrewName(attendanceBook);
        attendanceBook.attend(name, getFixedRunningDate(), enterAttendanceTime());
        outputView.printAttendResultMessage(attendanceBook.findAttendanceDateByNameAndDate(name, getFixedRunningDate()));
        selectMenu(attendanceBook);
    }

    private void editMenu(AttendanceBook attendanceBook) {
        attendanceBook.validateIsInRunningTime(DateTimeUtil.getFixedRunningTime());
        String name = enterCrewNameForEdit(attendanceBook);
        LocalDate date = enterAttendanceDateForEdit(attendanceBook, name);
        AttendanceDate originalAttendanceDate = attendanceBook.findAttendanceDateByNameAndDate(name, date);
        attendanceBook.edit(name, date, enterAttendanceTimeForEdit());
        outputView.printEditResultMessage(originalAttendanceDate, attendanceBook.findAttendanceDateByNameAndDate(name, date));
        selectMenu(attendanceBook);
    }

    private void retrieveAttendanceMenu(AttendanceBook attendanceBook) {
        String name = enterCrewName(attendanceBook);
        List<AttendanceDate> attendanceDates = attendanceBook.findAttendanceRecordByName(name).getAttendanceDates();
        outputView.printAttendanceResult(name);
        attendanceDates.forEach(outputView::printAttendResultMessage);
        outputView.printAttendanceCountResult(attendanceBook.getRiskStatusResult(name));
        selectMenu(attendanceBook);
    }

    private void retrieveRiskStatusMenu(AttendanceBook attendanceBook) {
        outputView.printRiskStatusResult(attendanceBook.getRiskStatusResults());
        selectMenu(attendanceBook);
    }

    private String enterCrewName(AttendanceBook attendanceBook) {
        try {
            String name = inputView.enterNickname();
            attendanceBook.validateHasCrew(name);
            return name;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return enterCrewName(attendanceBook);
        }
    }

    private String enterCrewNameForEdit(AttendanceBook attendanceBook) {
        try {
            String name = inputView.enterNicknameForEdit();
            attendanceBook.validateHasCrew(name);
            return name;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return enterCrewName(attendanceBook);
        }
    }

    private LocalTime enterAttendanceTime() {
        try {
            return parseTime(inputView.enterAttendanceTime());
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return enterAttendanceTime();
        }
    }

    private LocalDate enterAttendanceDateForEdit(AttendanceBook attendanceBook, String name) {
        try {
            LocalDate date = parseDateOfThisMonth(inputView.enterAttendanceDateForEdit());
            attendanceBook.validateIsAvailableAttendance(date);
            attendanceBook.validateBeforeEdit(name, date);
            return date;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return enterAttendanceDateForEdit(attendanceBook, name);
        }
    }

    private LocalTime enterAttendanceTimeForEdit() {
        try {
            return parseTime(inputView.enterAttendanceTimeForEdit());
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return enterAttendanceTimeForEdit();
        }
    }
}

