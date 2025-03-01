package controller;

import domain.Attend;
import domain.AttendCount;
import domain.AttendReader;
import domain.AttendStatus;
import domain.AttendanceBook;
import domain.Command;
import domain.Current;
import domain.WarningCrew;
import domain.WarningStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class AttendController {

    private static final String CSV_PATH = "attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Command, Runnable> commands;
    private final AttendanceBook attendanceBook;

    public AttendController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.commands = Map.of(
                Command.ADD, this::addAttend,
                Command.EDIT, this::edit,
                Command.SEARCH, this::searchAttend,
                Command.FIND_WARNING, this::findWarningCrew,
                Command.EXIT, this::close
        );
        this.attendanceBook = new AttendReader(CSV_PATH).loadAttendanceBook();
    }

    private void addAttend() {
        String name = inputView.inputName();
        LocalTime time = inputView.inputAttendTime();
        Attend attend = new Attend(Current.TODAY.getDate(), time);
        attendanceBook.addAttend(name, attend);
        AttendStatus attendStatus = attend.checkStatus();
        outputView.printAttendResult(attend, attendStatus);
    }

    private void edit() {
        String name = inputView.inputName();
        LocalDate editDate = inputView.inputDate();
        LocalTime editTime = inputView.inputChangeTime();
        Attend after = new Attend(editDate, editTime);
        Attend before = attendanceBook.edit(name, after);
        AttendStatus beforeStatus = before.checkStatus();
        AttendStatus afterStatus = after.checkStatus();
        outputView.printEditResult(before, beforeStatus, after, afterStatus);
    }

    private void searchAttend() {
        String name = inputView.inputName();
        List<Attend> attends = attendanceBook.searchAttend(name, Current.TODAY.getDay());
        List<AttendStatus> attendStatuses = attends.stream()
                .map(Attend::checkStatus)
                .toList();
        AttendCount attendCount = attendanceBook.countAttend(name);
        WarningStatus warningStatus = attendanceBook.judgeAttendStatus(name);
        outputView.printSearchedAttend(name, attends, attendStatuses, attendCount, warningStatus);
    }

    private void findWarningCrew() {
        List<WarningCrew> warningCrews = attendanceBook.searchWarningCrew();
        outputView.printWarningCrew(warningCrews);
    }

    private void close() {
        inputView.close();
    }

    public void run() {
        String command;
        do {
            command = inputView.inputCommand(Current.TODAY.getDate());
            runCommand(command);
        } while (!command.equals("Q"));
    }

    private void runCommand(final String command) {
        try {
            Command targetCommand = Command.findCommand(command);
            Runnable commandRunner = commands.get(targetCommand);
            commandRunner.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
    }
}
