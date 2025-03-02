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


}

