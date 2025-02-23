package attendance.domain;

import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class MenuOptionManager {
    private static final LocalDate LOCAL_DATE_TODAY = LocalDate.now();

    private final InputView inputView;
    private final OutputView outputView;
    private final Crews crews;
    private final Attendances attendances;
    private final List<MenuOption> menuOptions;

    public MenuOptionManager(InputView inputView, OutputView outputView, Crews crews, Attendances attendances) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crews = crews;
        this.attendances = attendances;

        this.menuOptions = List.of(
                new OptionCheckAttendance(inputView, outputView, crews, attendances),
                new OptionModifyAttendance(inputView, outputView, crews, attendances),
                new OptionLookupAttendance(inputView, outputView, crews, attendances),
                new OptionLookupExpulsion(inputView, outputView, crews, attendances)
        );
    }

    public String checkMenuOption() {
        String month = String.valueOf(LOCAL_DATE_TODAY.getMonthValue());
        String day = String.valueOf(LOCAL_DATE_TODAY.getDayOfMonth());
        String dayOfWeek = LOCAL_DATE_TODAY.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        return inputView.readCommand(month, day, dayOfWeek);
    }

    public void manageMenuOption(MenuCommand command) {
        for (MenuOption menuOption : menuOptions) {
            menuOption.executeMenuOption(command);
        }
    }
}
