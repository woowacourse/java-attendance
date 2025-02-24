package controller;

import domain.Attend;
import domain.AttendCount;
import domain.AttendReader;
import domain.AttendStatus;
import domain.AttendanceBook;
import domain.AttendanceResult;
import domain.AttendanceResults;
import domain.Command;
import domain.Current;
import domain.WarningCrew;
import dto.AttendResultDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final static String CSV_PATH = "attendance.csv";

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Command, Consumer<AttendanceBook>> commands;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.commands = Map.of(
                Command.ATTEND, this::registerAttend,
                Command.EDIT, this::editAttend,
                Command.SEARCH_ATTEND, this::searchAttendance,
                Command.SEARCH_WARNING_CREW, this::searchWarningCrews
        );
    }

    private void registerAttend(AttendanceBook attendanceBook) {
        String nickName = inputView.inputNickName();
        LocalTime time = inputView.inputTime();
        Attend attend = Attend.fromTime(time);
        attendanceBook.attend(nickName, attend);
        AttendStatus attendStatus = attendanceBook.checkAttendance(attend);
        outputView.printAttendResult(attend, attendStatus);
    }

    private void editAttend(AttendanceBook attendanceBook) {
        String nickName = inputView.inputEditNickName();
        LocalDate date = inputView.inputDate();
        LocalTime time = inputView.inputEditTime();
        Attend after = Attend.of(date, time);
        Attend before = attendanceBook.findByNameAndDay(nickName, date.getDayOfMonth());
        attendanceBook.edit(nickName, after);
        printChangeStatus(attendanceBook, before, after);
    }

    private void printChangeStatus(AttendanceBook attendanceBook, Attend before, Attend after) {
        AttendStatus beforeStatus = attendanceBook.checkAttendance(before);
        AttendStatus afterStatus = attendanceBook.checkAttendance(after);
        outputView.printEditResult(before, after, beforeStatus, afterStatus);
    }

    private void searchAttendance(AttendanceBook attendanceBook) {
        String nickName = inputView.inputNickName();
        AttendanceResults attendResult = attendanceBook.checkAttendance(nickName, Current.TODAY.getAttendUntilDay());
        AttendCount attendCount = attendResult.countAttendStatus();
        List<AttendanceResult> attendanceResults = attendResult.getAttendanceResults();
        AttendResultDto attendResultDto = new AttendResultDto(nickName, attendanceResults, attendCount);
        outputView.printAttendanceResult(attendResultDto);
    }

    private void searchWarningCrews(AttendanceBook attendanceBook) {
        List<WarningCrew> warningCrews = attendanceBook.checkWarningCrews(Current.TODAY.getAttendUntilDay());
        outputView.printWarningCrews(warningCrews);
    }

    public void run() {
        AttendanceBook attendanceBook = loadAttendanceBook();
        String command = "";
        while (!command.equals("Q")) {
            command = inputView.inputCommand(Current.TODAY.getDate());
            findAndRunCommand(command, attendanceBook);
        }
    }

    private AttendanceBook loadAttendanceBook() {
        AttendReader attendReader = new AttendReader(CSV_PATH);
        return attendReader.loadAttendanceBook();
    }

    private void findAndRunCommand(String commandInput, AttendanceBook attendanceBook) {
        try {
            Command command = Command.judgeCommand(commandInput);
            runCommand(command, attendanceBook);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void runCommand(Command command, AttendanceBook attendanceBook) {
        if (commands.containsKey(command)) {
            Consumer<AttendanceBook> attendCommand = commands.get(command);
            attendCommand.accept(attendanceBook);
        }
    }
}
