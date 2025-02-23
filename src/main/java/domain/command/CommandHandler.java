package domain.command;

import domain.Crews;
import view.InputView;
import view.OutputView;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler(Crews crews, InputView inputView, OutputView outputView) {
        commands.put("1", new AttendanceCheckCommand(crews, inputView, outputView));
        commands.put("2", new AttendanceUpdateCommand(crews, inputView, outputView));
        commands.put("3", new AttendanceHistoryCommand(crews, inputView, outputView));
        commands.put("4", new PenaltyCheckCommand(crews, outputView));
    }

    public void handle(String option) {
        Command command = commands.get(option);

        if (command != null) {
            command.execute();
            return;
        }
        System.out.println("[ERROR] 유효하지 않은 옵션입니다.");
    }
}
