package controller;

import domain.Attend;
import domain.AttendStatus;
import domain.AttendanceBook;
import domain.AttendanceResults;
import domain.Current;
import domain.WarningCrew;
import java.net.URL;
import java.util.List;
import util.DateUtil;
import util.FileUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final String CSV_PATH = "attendances.csv";
    private static final String QUITE_COMMAND = "Q";
    private static final String ATTEND_COMMAND = "1";
    private static final String EDIT_COMMAND = "2";
    private static final String CHECK_CREW_ATTEND_COMMAND = "3";
    private static final String CHECK_WARNING_CREW_COMMAND = "4";

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<String> rows = readCsv();
        AttendanceBook attendanceBook = loadAttendanceBook(rows);
        runCommandLoop(attendanceBook);
    }

    private void runCommandLoop(AttendanceBook attendanceBook) {
        boolean isLoopContinue = true;
        while (isLoopContinue) {
            isLoopContinue = processCommand(attendanceBook);
        }
    }

    private boolean processCommand(AttendanceBook attendanceBook) {
        try {
            String command = inputView.inputCommand(Current.TODAY.getLocalDate());
            if (command.equals(QUITE_COMMAND)) {
                return false;
            }
            if (command.equals(ATTEND_COMMAND)) {
                String nickName = inputView.inputNickName();
                String time = inputView.inputTime();
                Attend attend = Attend.of(time);
                attendanceBook.attend(nickName, attend);
                AttendStatus attendStatus = attendanceBook.checkAttendance(attend);
                outputView.printAttendResult(attend, attendStatus);
                return true;
            }
            if (command.equals(EDIT_COMMAND)) {
                String nickName = inputView.inputEditNickName();
                String date = inputView.inputDate();
                String time = inputView.inputEditTime();
                Attend after = Attend.of(date, time);
                int parsedDate = Integer.parseInt(date);
                Attend before = attendanceBook.findByNameAndDay(nickName, parsedDate);
                attendanceBook.edit(nickName, after);
                AttendStatus beforeStatus = attendanceBook.checkAttendance(before);
                AttendStatus afterStatus = attendanceBook.checkAttendance(after);
                outputView.printEditResult(before, after, beforeStatus, afterStatus);
                return true;
            }
            if (command.equals(CHECK_CREW_ATTEND_COMMAND)) {
                String nickName = inputView.inputNickName();
                AttendanceResults attendResult = attendanceBook.checkAttendance(nickName,
                        DateUtil.getAttendUntilDay(Current.TODAY.getYesterday()));
                outputView.printAttendanceResult(nickName, attendResult);
                return true;
            }
            if (command.equals(CHECK_WARNING_CREW_COMMAND)) {
                List<WarningCrew> warningCrews = attendanceBook.checkWarningCrews(
                        DateUtil.getAttendUntilDay(Current.TODAY.getYesterday()));
                outputView.printWarningCrews(warningCrews);
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private AttendanceBook loadAttendanceBook(List<String> data) {
        AttendanceBook attendanceBook = new AttendanceBook();
        for (String row : data) {
            final String name = parseName(row);
            final Attend attend = parseAttend(row);
            attendanceBook.registerName(name);
            attendanceBook.attend(name, attend);
        }
        return attendanceBook;
    }

    private Attend parseAttend(String row) {
        String dateTime = row.split(",")[1];
        String day = dateTime.substring(8, 10);
        String time = dateTime.substring(11);
        return Attend.of(day, time);
    }

    private String parseName(String row) {
        return row.split(",")[0];
    }

    private List<String> readCsv() {
        URL fileURL = AttendanceController.class.getClassLoader().getResource(CSV_PATH);
        return FileUtil.readFile(fileURL);
    }
}
