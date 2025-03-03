package controller;

import controller.command.AttendCommand;
import controller.command.ControllerCommand;
import controller.command.GetPenaltyCommand;
import controller.command.GetRecordsCommand;
import controller.command.ModifyCommand;
import domain.AttendanceBook;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import service.AttendanceService;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final Map<Selection, ControllerCommand> commands
        = new EnumMap<>(Selection.class);

    public AttendanceController(AttendanceBook book, LocalDate beginDateOfEducation) {
        AttendanceService service = new AttendanceService(book);
        commands.put(Selection.ATTEND, new AttendCommand(service));
        commands.put(Selection.MODIFY, new ModifyCommand(service));
        commands.put(Selection.GET_RECORDS, new GetRecordsCommand(service));
        commands.put(Selection.GET_PENALTIES, new GetPenaltyCommand(service, beginDateOfEducation));
    }

    public void run(LocalDate today) {
        while (true) {
            try {
                Selection selection = Selection.of(InputView.readSelection());
                executeCommand(selection, today);

            } catch (QuitException q) {
                break;
            } catch (DateTimeException dte) {
                OutputView.printException(new IllegalArgumentException("잘못된 날짜입니다."));
            } catch (IllegalArgumentException e) {
                OutputView.printException(e);
            }
        }
    }

    private void executeCommand(Selection selection, LocalDate today) throws QuitException {
        if (selection == Selection.QUIT) {
            throw new QuitException();
        }

        ControllerCommand command = commands.get(selection);
        command.execute(today);
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
