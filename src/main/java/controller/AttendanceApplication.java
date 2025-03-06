package controller;

import util.ApplicationDate;
import view.ConsoleView;
import view.Menu;

public class AttendanceApplication {

    private final AttendanceHandler attendanceHandler;
    private final ConsoleView consoleView;
    private final ApplicationDate applicationDate;

    public AttendanceApplication(
            AttendanceHandler attendanceHandler,
            ConsoleView consoleView,
            ApplicationDate applicationDate
    ) {
        this.attendanceHandler = attendanceHandler;
        this.consoleView = consoleView;
        this.applicationDate = applicationDate;
    }

    public void execute() {
        Menu menu = null;
        do {
            try {
                menu = consoleView.requestMenu(applicationDate.getApplicationDate());
                attendanceHandler.handle(menu);
            } catch (Exception e) {
                consoleView.printMessage(e.getMessage());
            }
        } while (!menu.isQuit());
    }
}
