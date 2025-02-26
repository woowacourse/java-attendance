package attendance.controller;

import attendance.controller.command.AttendCommand;
import attendance.controller.command.Command;
import attendance.controller.command.FindByCrewCommand;
import attendance.controller.command.FindDismissalCommand;
import attendance.controller.command.ModifyCommand;
import attendance.domain.model.Campus;
import attendance.domain.model.CrewHistories;
import attendance.domain.model.TodayClock;
import java.time.LocalDate;
import java.util.Map;
import attendance.view.InputView;
import attendance.view.MenuOption;
import attendance.view.ResultView;

public class AttendanceController {

    private final InputView inputView;
    private final ResultView resultView;
    private final Campus campus;
    private final TodayClock todayClock;
    private final Map<MenuOption, Command> commands;

    public AttendanceController(final InputView inputView, final ResultView resultView, final Campus campus,
                                final TodayClock todayClock) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.campus = campus;
        this.todayClock = todayClock;
        this.commands = initializeCommands();
    }

    public void start(final CrewHistories crewHistories) {
        MenuOption menuOption = MenuOption.from(inputView.readCommand(getTodayDate()));
        if (menuOption.equals(MenuOption.QUIT)) {
            return;
        }
        process(crewHistories, menuOption);
        start(crewHistories);
    }

    private Map<MenuOption, Command> initializeCommands() {
        return Map.of(
                MenuOption.CHECK_ATTENDANCE, new AttendCommand(inputView, campus, todayClock, resultView),
                MenuOption.MODIFY_ATTENDANCE, new ModifyCommand(inputView, campus, todayClock, resultView),
                MenuOption.CHECK_ATTENDANCE_BY_CREW, new FindByCrewCommand(inputView, todayClock, resultView),
                MenuOption.CHECK_DISMISSAL_CREW, new FindDismissalCommand(todayClock, resultView)
        );
    }

    private void process(final CrewHistories crewHistories, final MenuOption menuOption) {
        Command command = commands.get(menuOption);
        command.execute(crewHistories);
    }

    private LocalDate getTodayDate() {
        return todayClock.getTodayDate();
    }
}
