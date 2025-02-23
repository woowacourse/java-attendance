package attendance.controller;

import attendance.domain.Attendances;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.domain.MenuOptionManager;
import attendance.util.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.List;

public class AttendanceController {
    private static final String FILE_NAME = "attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;
    private final Crews crews;
    private final Attendances attendances;
    private final MenuOptionManager menuOptionManager;

    public AttendanceController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.crews = new Crews();
        this.attendances = new Attendances();
        this.menuOptionManager = new MenuOptionManager(inputView, outputView, crews, attendances);
    }

    public void run() {
        initDataFromCSV();

        MenuCommand command;
        do {
            command = checkMenuCommand();
        } while (!command.equals(MenuCommand.QUIT));
    }

    private void initDataFromCSV() {
        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource(FILE_NAME);

        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);
    }

    private MenuCommand checkMenuCommand() {
        try {
            MenuCommand command = MenuCommand.toCommand(menuOptionManager.checkMenuOption());
            menuOptionManager.manageMenuOption(command);
            return command;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return MenuCommand.NONE;
        }
    }
}
