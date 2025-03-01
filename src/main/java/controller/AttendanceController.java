package controller;

import controller.command.AttendCommand;
import controller.command.ControllerCommand;
import domain.AttendanceBook;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final Map<Selection, ControllerCommand> commands = new HashMap<>();

    static {
        commands.put(Selection.ATTEND, new AttendCommand());
    }

    public static void run(AttendanceBook book) {
        while (true) {
            try {
                Selection selection = Selection.of(InputView.readSelection());
                executeCommand(selection, book);

            } catch (QuitException q) {
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printException(e);
            }
        }
    }

    private static void executeCommand(Selection selection, AttendanceBook book) throws QuitException {
        if (selection == Selection.QUIT) {
            throw new QuitException();
        }

        ControllerCommand command = commands.get(selection);
        command.execute(book);
    }

    private enum Selection {
        ATTEND("1"),
        MODIFY("2"),
        GET_RECORDS("3"),
        GET_PENALTIES("4"),
        QUIT("Q");

        private final String userInput;

        Selection(String userInput) {
            this.userInput = userInput;
        }

        private static Selection of(String userInput) {
            return Arrays.stream(values())
                .filter(selection -> selection.userInput.equals(userInput))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."));
        }
    }

    private static class QuitException extends Exception {

    }
}
