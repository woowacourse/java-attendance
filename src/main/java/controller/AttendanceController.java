package controller;

import domain.*;
import util.AttendanceBookParser;
import view.InputView;
import view.OutputView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceController {
    public static final String FILE_PATH = "src/main/resources/attendances.csv";
    public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
    public static final String HOUR_MINUTE_FORMAT = "HH:mm";

    private final SystemDateProvider systemDateProvider;
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBook attendanceBook;

    public AttendanceController(SystemDateProvider systemDateProvider, InputView inputView, OutputView outputView) {
        this.systemDateProvider = systemDateProvider;
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBook = AttendanceBookParser.parseToAttendanceBook(
                FILE_PATH,
                DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_FORMAT),
                DateTimeFormatter.ofPattern(HOUR_MINUTE_FORMAT)
        );
    }

    public void run() {
        while (true) {
            String featureNumber = inputView.readFeatureNumber(systemDateProvider.now());
            if (featureNumber.equals("1")) {
                checkIn();
            }
            if (featureNumber.equals("2")) {
                modify();
            }
            if (featureNumber.equals("3")) {
                viewCrewHistory();
            }
            if (featureNumber.equals("4")) {
                viewDangerCrews();
            }
            if (featureNumber.equals("Q")) {
                break;
            }
        }
    }

    private void checkIn() {
        CheckInDate checkInDate = CheckInDate.of(systemDateProvider.now());
        String nickname = inputView.readNickName();
        CheckInHistory historyByCrew = getCheckInHistoryByName(nickname);
        CheckInTime checkInTime = getCheckInTime();
        attendanceBook.checkIn(historyByCrew, checkInDate, checkInTime);
        outputView.printTodayCheckInTime(checkInDate, checkInTime);
    }

    private void modify() {
        String nickname = inputView.readNickNameForModify();
        CheckInHistory checkInHistoryByName = getCheckInHistoryByName(nickname);
        CheckInDate dateToModify = getDayToModify();
        CheckInTime afterTime = getCheckInTimeForModify();
        CheckInTime beforeTime = checkInHistoryByName.modifyCheckInTime(dateToModify, afterTime);
        outputView.printModifiedChSeckInTime(dateToModify, beforeTime, afterTime);
    }

    private void viewCrewHistory() {
        String nickname = inputView.readNickName();
        CheckInHistory checkInHistoryByName = getCheckInHistoryByName(nickname);
        outputView.printAttendanceHistory(nickname, systemDateProvider.now(), checkInHistoryByName);
    }

    private void viewDangerCrews() {
        List<DangerCrew> dangerCrews = attendanceBook.findDangerCrews(systemDateProvider.now());
        outputView.printDangerCrews(dangerCrews);
    }

    private CheckInTime getCheckInTimeForModify() {
        String time = inputView.readTimeForModify();
        LocalTime parsedTime = LocalTime.parse(time);
        return CheckInTime.of(parsedTime);
    }

    private CheckInDate getDayToModify() {
        String date = inputView.readDateForModify();
        int parsedDate = Integer.parseInt(date);
        return CheckInDate.of(2024, 12, parsedDate);
    }

    private CheckInHistory getCheckInHistoryByName(String nickname) {
        Crew crew = Crew.of(nickname);
        return attendanceBook.findHistoryByCrew(crew);
    }

    private CheckInTime getCheckInTime() {
        String time = inputView.readTimeForCheckIn();
        LocalTime parsedTime = LocalTime.parse(time);
        return CheckInTime.of(parsedTime);
    }
}
