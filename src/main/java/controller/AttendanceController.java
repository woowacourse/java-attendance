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

    public AttendanceController(SystemDateProvider systemDateProvider, InputView inputView, OutputView outputView) {
        this.systemDateProvider = systemDateProvider;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        AttendanceBook attendanceBook = AttendanceBookParser.parseToAttendanceBook(
                FILE_PATH,
                DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_FORMAT),
                DateTimeFormatter.ofPattern(HOUR_MINUTE_FORMAT)
        );

        while (true) {
            String featureNumber = inputView.readFeatureNumber(systemDateProvider.now());
            if (featureNumber.equals("1")) {
                CheckInDate checkInDate = CheckInDate.of(systemDateProvider.now());
                String nickname = inputView.readNickName();
                Crew crew = Crew.of(nickname);
                CheckInHistory historyByCrew = attendanceBook.findHistoryByCrew(crew);
                String time = inputView.readTimeForCheckIn();
                LocalTime parsedTime = LocalTime.parse(time);
                CheckInTime checkInTime = CheckInTime.of(parsedTime);
                attendanceBook.checkIn(historyByCrew, checkInDate, checkInTime);
                outputView.printTodayCheckInTime(checkInDate, checkInTime);
            }
            if (featureNumber.equals("Q")) {
                break;
            }
        }
    }


}
