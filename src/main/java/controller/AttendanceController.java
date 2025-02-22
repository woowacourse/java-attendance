package controller;

import domain.Attend;
import domain.AttendReader;
import domain.AttendStatus;
import domain.AttendanceBook;
import domain.AttendanceResults;
import domain.Current;
import domain.WarningCrew;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        AttendanceBook attendanceBook = loadAttendanceBook();
        String command = "";
        while (!command.equals("Q")) {
            command = inputView.inputCommand(Current.TODAY.getLocalDate());
            runCommand(command, attendanceBook);
        }
    }

    private AttendanceBook loadAttendanceBook() {
        AttendReader attendReader = new AttendReader();
        return attendReader.loadAttendanceBook();
    }

    private void runCommand(String command, AttendanceBook attendanceBook) {
        try {
            registerAttend(command, attendanceBook);
            editAttend(command, attendanceBook);
            searchAttendance(command, attendanceBook);
            searchWarningCrews(command, attendanceBook);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerAttend(String command, AttendanceBook attendanceBook) {
        if (!command.equals("1")) {
            return;
        }
        String nickName = inputView.inputNickName();
        LocalTime time = inputView.inputTime();
        Attend attend = Attend.fromTime(time);
        attendanceBook.attend(nickName, attend);
        AttendStatus attendStatus = attendanceBook.checkAttendance(attend);
        outputView.printAttendResult(attend, attendStatus);
    }

    private void editAttend(String command, AttendanceBook attendanceBook) {
        if (!command.equals("2")) {
            return;
        }
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

    private void searchAttendance(String command, AttendanceBook attendanceBook) {
        if (!command.equals("3")) {
            return;
        }
        String nickName = inputView.inputNickName();
        AttendanceResults attendResult = attendanceBook.checkAttendance(nickName, Current.TODAY.getAttendUntilDay());
        outputView.printAttendanceResult(nickName, attendResult);
    }

    private void searchWarningCrews(String command, AttendanceBook attendanceBook) {
        if (!command.equals("4")) {
            return;
        }
        List<WarningCrew> warningCrews = attendanceBook.checkWarningCrews(Current.TODAY.getAttendUntilDay());
        outputView.printWarningCrews(warningCrews);
    }
}
