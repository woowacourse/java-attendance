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
            try {
                if (command.equals("1")) {
                    registerAttend(attendanceBook);
                }
                if (command.equals("2")) {
                    editAttend(attendanceBook);
                }
                if (command.equals("3")) {
                    searchAttendance(attendanceBook);
                }
                if (command.equals("4")) {
                    searchWarningCrews(attendanceBook);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private AttendanceBook loadAttendanceBook() {
        AttendReader attendReader = new AttendReader();
        return attendReader.loadAttendanceBook();
    }

    private void searchWarningCrews(AttendanceBook attendanceBook) {
        List<WarningCrew> warningCrews = attendanceBook.checkWarningCrews(Current.TODAY.getAttendUntilDay());
        outputView.printWarningCrews(warningCrews);
    }

    private void searchAttendance(AttendanceBook attendanceBook) {
        String nickName = inputView.inputNickName();
        AttendanceResults attendResult = attendanceBook.checkAttendance(nickName, Current.TODAY.getAttendUntilDay());
        outputView.printAttendanceResult(nickName, attendResult);
    }

    private void editAttend(AttendanceBook attendanceBook) {
        String nickName = inputView.inputEditNickName();
        LocalDate date = inputView.inputDate();
        LocalTime time = inputView.inputEditTime();
        Attend after = Attend.of(date, time);
        Attend before = attendanceBook.findByNameAndDay(nickName, date.getDayOfMonth());
        attendanceBook.edit(nickName, after);
        AttendStatus beforeStatus = attendanceBook.checkAttendance(before);
        AttendStatus afterStatus = attendanceBook.checkAttendance(after);
        outputView.printEditResult(before, after, beforeStatus, afterStatus);
    }

    private void registerAttend(AttendanceBook attendanceBook) {
        String nickName = inputView.inputNickName();
        LocalTime time = inputView.inputTime();
        Attend attend = Attend.fromTime(time);
        attendanceBook.attend(nickName, attend);
        AttendStatus attendStatus = attendanceBook.checkAttendance(attend);
        outputView.printAttendResult(attend, attendStatus);
    }
}
