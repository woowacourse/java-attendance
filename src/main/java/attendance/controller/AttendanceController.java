package attendance.controller;

import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;

public class AttendanceController {

    private final InputView inputView;
    private final ResultView resultView;
    private final Clock clock;
    private final CampusScheduler campusScheduler;
    private final Map<CommandStatus, Command> commandProcess;

    public AttendanceController(final InputView inputView, final ResultView resultView, final Clock clock,
                                final CampusScheduler campusScheduler) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.clock = clock;
        this.campusScheduler = campusScheduler;
        this.commandProcess = initializeCommand();
    }

    public void run(final CrewHistories crewHistories) {
        CommandStatus commandStatus = makeCommandStatus();
        if (commandStatus == CommandStatus.QUIT) {
            return;
        }
        Command command = commandProcess.get(commandStatus);
        command.execute(crewHistories);
        resultView.showBlank();
        run(crewHistories);
    }

    private Map<CommandStatus, Command> initializeCommand() {
        return Map.of(
                CommandStatus.ATTEND, new AttendCommand(inputView, resultView, clock, campusScheduler),
                CommandStatus.MODIFY, new ModifyCommand(),
                CommandStatus.INQUIRY_CREW, new InQuiryCrewCommand(),
                CommandStatus.FIND_DISMISSAL, new FindDismissalCommand()
        );
    }

    private CommandStatus makeCommandStatus() {
        LocalDate now = LocalDate.now(clock);
        String commandInput = inputView.readCommand(now);
        return CommandStatus.from(commandInput);
    }
}
