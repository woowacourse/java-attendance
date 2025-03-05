package controller;

import static global.utils.DateTimeUtil.getFixedRunningDate;
import static global.utils.DateTimeUtil.parseDateOfThisMonth;
import static global.utils.DateTimeUtil.parseTime;

import domain.AttendanceBook;
import domain.AttendanceDate;
import domain.Crew;
import domain.Crews;
import global.utils.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import view.InputView;
import view.Menu;
import view.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crews crews = new Crews();
        inputView.readFile(attendanceBook, crews);
        selectMenu(attendanceBook, crews);
    }

    private void selectMenu(AttendanceBook attendanceBook, Crews crews) {
        outputView.printSelectMenuMessage();
        try {
            Menu menuItem = Menu.of(inputView.enterMenuItem());
            evaluateMenuItem(menuItem, attendanceBook, crews);
        }
        catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            selectMenu(attendanceBook, crews);
        }
    }

    private void evaluateMenuItem(Menu menuItem, AttendanceBook attendanceBook, Crews crews) {
        if (menuItem.equals(Menu.FIRST)) {
            attendMenu(attendanceBook, crews);
        }
        if (menuItem.equals(Menu.SECOND)) {
            editMenu(attendanceBook, crews);
        }
        if (menuItem.equals(Menu.THIRD)) {
            retrieveAttendanceMenu(attendanceBook, crews);
        }
        if (menuItem.equals(Menu.FOURTH)) {
            retrieveRiskStatusMenu(attendanceBook, crews);
        }
    }

    private void attendMenu(AttendanceBook attendanceBook, Crews crews) {
        attendanceBook.validateIsWeekday(DateTimeUtil.getFixedRunningDate());
        attendanceBook.validateIsInRunningTime(DateTimeUtil.getFixedRunningTime());
        Crew crew = enterCrewName(crews);
        attendanceBook.validateBeforeAdd(crew);
        attendanceBook.attend(crew, getFixedRunningDate(), enterAttendanceTime());
        outputView.printAttendResultMessage(attendanceBook.findAttendanceDateByDate(crew, getFixedRunningDate()));
        selectMenu(attendanceBook, crews);
    }

    private void editMenu(AttendanceBook attendanceBook, Crews crews) {
        attendanceBook.validateIsInRunningTime(DateTimeUtil.getFixedRunningTime());
        Crew crew = enterCrewNameForEdit(crews);
        LocalDate date = enterAttendanceDateForEdit(attendanceBook, crew);
        AttendanceDate originalAttendanceDate = attendanceBook.findAttendanceDateByDate(crew, date);
        attendanceBook.edit(crew, date, enterAttendanceTimeForEdit());
        outputView.printEditResultMessage(originalAttendanceDate, attendanceBook.findAttendanceDateByDate(crew, date));
        selectMenu(attendanceBook, crews);
    }

    private void retrieveAttendanceMenu(AttendanceBook attendanceBook, Crews crews) {
        Crew crew = enterCrewName(crews);
        List<AttendanceDate> attendanceDates = attendanceBook.findAttendanceRecord(crew).getAttendanceDates();
        outputView.printAttendanceResult(crew);
        attendanceDates.forEach(outputView::printAttendResultMessage);
        outputView.printAttendanceCountResult(attendanceBook.getRiskStatusResult(crew));
        selectMenu(attendanceBook, crews);
    }

    private void retrieveRiskStatusMenu(AttendanceBook attendanceBook, Crews crews) {
        outputView.printRiskStatusResult(attendanceBook.getRiskStatusResults());
        selectMenu(attendanceBook, crews);
    }

    private Crew enterCrewName(Crews crews) {
        try {
            String name = inputView.enterNickname();
            crews.validateHasCrew(name);
            return crews.getCrew(name);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return enterCrewName(crews);
        }
    }

    private Crew enterCrewNameForEdit(Crews crews) {
        try {
            String name = inputView.enterNicknameForEdit();
            crews.validateHasCrew(name);
            return crews.getCrew(name);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return enterCrewNameForEdit(crews);
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

    private LocalDate enterAttendanceDateForEdit(AttendanceBook attendanceBook, Crew crew) {
        try {
            LocalDate date = parseDateOfThisMonth(inputView.enterAttendanceDateForEdit());
            attendanceBook.validateIsAvailableAttendance(date);
            attendanceBook.validateBeforeEdit(crew, date);
            return date;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return enterAttendanceDateForEdit(attendanceBook, crew);
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

