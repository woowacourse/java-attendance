package attendance.controller;

import attendance.controller.command.AttendCommand;
import attendance.controller.command.Command;
import attendance.controller.command.FindByCrewCommand;
import attendance.controller.command.FindDismissalCommand;
import attendance.controller.command.ModifyCommand;
import attendance.domain.model.Campus;
import attendance.domain.model.CrewHistories;
import attendance.view.InputView;
import attendance.view.MenuOption;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

public class AttendanceController {

    private final InputView inputView;
    private final ResultView resultView;
    private final Campus campus;
    private final Clock clock;
    private final Map<MenuOption, Command> commands;

    public AttendanceController(final InputView inputView, final ResultView resultView, final Campus campus,
                                final Clock clock) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.campus = campus;
        this.clock = clock;
        this.commands = initializeCommands();
    }

    public void start(final CrewHistories crewHistories) {
        MenuOption menuOption = MenuOption.from(inputView.readCommand(LocalDate.now(clock)));
        if (menuOption.equals(MenuOption.QUIT)) {
            return;
        }
        process(crewHistories, menuOption);
        start(crewHistories);
    }

    private Map<MenuOption, Command> initializeCommands() {
        return Map.of(
                MenuOption.CHECK_ATTENDANCE, new AttendCommand(inputView, campus, clock, resultView),
                MenuOption.MODIFY_ATTENDANCE, new ModifyCommand(inputView, campus, clock, resultView),
                MenuOption.CHECK_ATTENDANCE_BY_CREW, new FindByCrewCommand(inputView, clock, resultView),
                MenuOption.CHECK_DISMISSAL_CREW, new FindDismissalCommand(clock, resultView)
        );
    }

    private void process(final CrewHistories crewHistories, final MenuOption menuOption) {
        Command command = commands.get(menuOption);
        command.execute(crewHistories);
    }
}
