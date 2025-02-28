package controller;

import controller.menu.AttendanceEditController;
import controller.menu.AttendanceMenuController;
import controller.menu.AttendanceRegisterController;
import controller.menu.CrewAttendanceController;
import controller.menu.ExpulsionRiskController;
import controller.store.AttendanceCsvController;
import controller.store.AttendanceStoreController;
import domain.attendance.AttendanceBook;
import domain.menu.Menu;
import exception.ExceptionHandler;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import view.InputView;

public class AttendanceController {

    private final AttendanceBook attendanceBook;
    private final Map<Menu, AttendanceMenuController> menuControllerRegistry;

    public AttendanceController() throws IOException {
        this.attendanceBook = initailizeAttendanceBook();
        this.menuControllerRegistry = initializeMenuControllerRegistry();
    }

    public void run() {
        boolean continueRunning = true;
        while (continueRunning) {
            continueRunning = selectAttendanceMenu();
        }
    }

    private AttendanceBook initailizeAttendanceBook() throws IOException {
        AttendanceStoreController attendanceStoreController = new AttendanceCsvController();
        return attendanceStoreController.store();
    }

    private Map<Menu, AttendanceMenuController> initializeMenuControllerRegistry() {
        Map<Menu, AttendanceMenuController> menuControllerRegistry = new EnumMap<>(Menu.class);
        menuControllerRegistry.put(Menu.ATTENDANCE_REGISTER, new AttendanceRegisterController(attendanceBook));
        menuControllerRegistry.put(Menu.ATTENDANCE_EDIT, new AttendanceEditController(attendanceBook));
        menuControllerRegistry.put(Menu.CREW_ATTENDANCE, new CrewAttendanceController(attendanceBook));
        menuControllerRegistry.put(Menu.EXPULSION_RISK, new ExpulsionRiskController(attendanceBook));
        return menuControllerRegistry;
    }

    private boolean selectAttendanceMenu() {
        return ExceptionHandler.repeatUntilSuccess(() -> {
            LocalDate runDate = generateRunDate();
            Menu menu = InputView.readAttendanceMenu(runDate);
            if (menu == Menu.QUIT) {
                return false;
            }
            menuControllerRegistry.get(menu).run(runDate);
            return true;
        });
    }

    private LocalDate generateRunDate() {
        LocalDate todayDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        if (todayDate.isAfter(endDate)) {
            return endDate;
        }
        return todayDate;
    }
}
