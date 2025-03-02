package controller;

import domain.*;
import util.AttendanceBookParser;
import view.InputView;
import view.OutputView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
