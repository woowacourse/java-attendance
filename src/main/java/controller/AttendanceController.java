package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.Attendance;
import model.AttendanceBook;
import model.AttendanceFileReader;
import model.CrewAttendances;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final LocalDate today;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(LocalDate today, InputView inputView, OutputView outputView) {
        this.today = today;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        AttendanceFileReader reader = new AttendanceFileReader();
        AttendanceBook book = reader.read();

        while (true) {
            String select = inputView.inputMenu(today);

            if (select.equals("1")) {
                attend(book);
                continue;
            }

            if (select.equals("2")) {
                update(book);
                continue;
            }

            if (select.equals("3")) {
                printAttendanceSheetsByCrew(book);
                continue;
            }

            if (select.equals("4")) {
                printRiskOfDismissal(book);
                continue;
            }

            if (select.equals("Q")) {
                return;
            }
        }
    }

    private void attend(AttendanceBook book) {
        System.out.print(System.lineSeparator());
        String nickname = inputView.inputNickname();
        String time = inputView.inputTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        Attendance attendance = book.check(nickname, today, LocalTime.of(hour, minute));
        outputView.printAddInformation(attendance);
    }

    private void update(AttendanceBook book) {
        System.out.print(System.lineSeparator());
        String updateNickname = inputView.inputUpdateNickname();
        LocalDate updateDate = today.withDayOfMonth(Integer.parseInt(inputView.inputUpdateDate()));
        LocalTime updateTime = LocalTime.parse(inputView.inputTime());

        Attendance attendance = book.findAttendance(updateNickname, updateDate);
        Attendance updateAttendance = book.update(updateNickname, updateDate, updateTime);

        outputView.printUpdateInformation(attendance, updateAttendance);
    }

    private void printAttendanceSheetsByCrew(AttendanceBook book) {
        System.out.print(System.lineSeparator());
        String nickname = inputView.inputNickname();

        CrewAttendances crewAttendances = book.findCrewAttendance(nickname);
        outputView.printCrewAttendanceRecords(crewAttendances, today);
    }

    // 4번 기능
    private void printRiskOfDismissal(AttendanceBook book) {
        List<CrewAttendances> riskOfDismissalCrews = book.findSortedRiskOfDismissalCrews(today);

        outputView.printRiskOfDismissal(riskOfDismissalCrews, today);
    }
}
