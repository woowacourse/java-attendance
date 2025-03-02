package attendance.controller;

import attendance.controller.option.MenuOption;
import attendance.controller.option.MenuOptionFactory;
import attendance.domain.Attendances;
import attendance.domain.CrewNames;
import attendance.domain.MenuCommand;
import attendance.util.FileHandler;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class AttendanceController {
    private static final LocalDate TODAY = LocalDate.now();
    private static final int TODAY_MONTH = TODAY.getMonthValue();
    private static final int TODAY_DAY = TODAY.getDayOfMonth();

    private static final String TODAY_DAY_OF_WEEK = TODAY.getDayOfWeek()
            .getDisplayName(TextStyle.NARROW, Locale.KOREAN);

    private final InputView inputView;
    private final CrewNames crewNames;
    private final Attendances attendances;
    private final MenuOptionFactory menuOptionFactory;

    public AttendanceController() {
        this.inputView = new InputView();
        this.crewNames = new CrewNames();
        this.attendances = new Attendances();
        OutputView outputView = new OutputView();
        this.menuOptionFactory = new MenuOptionFactory(inputView, crewNames, attendances, outputView);
    }

    public void run() {
        initializeFromCSV();

        Optional<MenuCommand> menuCommand;
        do {
            String commandInput = inputView.readMenuCommand(TODAY_MONTH, TODAY_DAY, TODAY_DAY_OF_WEEK);
            menuCommand = checkMenuCommand(commandInput);
            menuCommand.flatMap(menuOptionFactory::createMenuOption).ifPresent(MenuOption::execute);
        } while (menuCommand.isEmpty() || menuCommand.get() != MenuCommand.QUIT);
    }

    private void initializeFromCSV() {
        FileHandler fileHandler = new FileHandler();
        Map<String, List<LocalDateTime>> data = fileHandler.provideDataFromFile();
        crewNames.initializeCrewNames(data.keySet());
        attendances.initializeAttendances(data);
    }

    private static Optional<MenuCommand> checkMenuCommand(final String commandInput) {
        try {
            return Optional.of(MenuCommand.of(commandInput));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
