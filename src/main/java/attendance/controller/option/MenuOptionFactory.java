package attendance.controller.option;

import attendance.domain.Attendances;
import attendance.domain.CrewNames;
import attendance.domain.MenuCommand;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.Optional;

public class MenuOptionFactory {
    private final InputView inputView;
    private final CrewNames crewNames;
    private final Attendances attendances;
    private final OutputView outputView;

    public MenuOptionFactory(InputView inputView, CrewNames crewNames, Attendances attendances, OutputView outputView) {
        this.inputView = inputView;
        this.crewNames = crewNames;
        this.attendances = attendances;
        this.outputView = outputView;
    }

    public Optional<MenuOption> createMenuOption(final MenuCommand menuCommand) {
        if (menuCommand.equals(MenuCommand.CHECK)) {
            return Optional.of(new AttendanceCheckOption(inputView, crewNames, attendances, outputView));
        }
        if (menuCommand.equals(MenuCommand.MODIFY)) {
            return Optional.of(new AttendanceModifyOption(inputView, crewNames, attendances, outputView));
        }
        if (menuCommand.equals(MenuCommand.LOOKUP)) {
            return Optional.of(new AttendanceLookupOption(inputView, crewNames, attendances, outputView));
        }
        if (menuCommand.equals(MenuCommand.EXPEL)) {
            return Optional.of(new AttendanceExpelOption(inputView, crewNames, attendances, outputView));
        }
        return Optional.empty();
    }
}
