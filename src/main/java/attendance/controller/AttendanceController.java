package attendance.controller;

import attendance.model.Crews;
import attendance.model.MenuOption;
import attendance.util.CSVReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class AttendanceController {
    private static final Path path = Paths.get("src/main/resources/attendances.csv");

    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController() {
        this.crews = new Crews();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void start() {
        crews.initCrews(CSVReader.readCSV(path));
        run();
    }

    public void run() {
        MenuOption menuOption = MenuOption.NONE;
        while (!MenuOption.QUIT.equals(menuOption)) {
            menuOption = manageOption(menuOption);
        }
    }

    private MenuOption manageOption(MenuOption menuOption) {
        try {
            menuOption = readCommand();
            executeOption(menuOption);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            run();
        }
        return menuOption;
    }

    private MenuOption readCommand() {
        LocalDate today = LocalDate.now();
        String month = String.valueOf(today.getMonthValue());
        String date = String.valueOf(today.getDayOfMonth());
        String day = today.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        return MenuOption.of(inputView.readCommand(month, date, day));
    }

    private void executeOption(MenuOption menuOption) {
        Map<MenuOption, Runnable> optionActions = Map.of(
                MenuOption.ATTEND_TODAY, this::attendToday,
                MenuOption.MODIFY_ATTENDANCE, this::modifyAttendance,
                MenuOption.SHOW_STATISTIC, this::showStatistic,
                MenuOption.CHECK_STATUS, this::checkStatus
        );

        optionActions.get(menuOption).run();
    }

    private void attendToday() {

    }

    private void modifyAttendance() {

    }

    private void showStatistic() {

    }

    private void checkStatus() {

    }

}
