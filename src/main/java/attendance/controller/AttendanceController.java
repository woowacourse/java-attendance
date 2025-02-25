package attendance.controller;

import attendance.domain.Attendances;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.util.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class AttendanceController {
    private static final String FILE_NAME = "attendances.csv";
    private static final LocalDate LOCAL_DATE_TODAY = LocalDate.now();

    private final InputView inputView;
    private final OutputView outputView;
    private final Crews crews;
    private final Attendances attendances;

    public AttendanceController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.crews = new Crews();
        this.attendances = new Attendances();
    }

    public void run() {
        initDataFromCSV();

        Optional<MenuCommand> command;
        do {
            command = checkMenuCommand();
            command.ifPresent(this::executeMenuOption);
        } while (command.isEmpty() || command.get() != MenuCommand.QUIT);
    }

    private void initDataFromCSV() {
        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource(FILE_NAME);

        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);
    }

    private Optional<MenuCommand> checkMenuCommand() {
        try {
            return Optional.of(MenuCommand.toCommand(getMenuCommand()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    private String getMenuCommand() {
        String month = String.valueOf(LOCAL_DATE_TODAY.getMonthValue());
        String day = String.valueOf(LOCAL_DATE_TODAY.getDayOfMonth());
        String dayOfWeek = LOCAL_DATE_TODAY.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);

        return inputView.readCommand(month, day, dayOfWeek);
    }

    private void executeMenuOption(final MenuCommand command) {
        if (command == MenuCommand.QUIT) {
            return;
        }
        Class<?> optionClass = command.getOption();
        try {
            MenuOption option = (MenuOption) optionClass
                    .getConstructor(InputView.class, OutputView.class, Crews.class, Attendances.class)
                    .newInstance(inputView, outputView, crews, attendances);
            option.executeMenuOption(command);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException ignored) {
        }
    }
}
