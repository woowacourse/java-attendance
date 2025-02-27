package controller;

import domain.AttendanceSheet;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;

import static config.AppConfig.TODAY;
import static util.ExceptionHandler.runInputCommand;

public class AttendanceController {

    public static final String CREATE_ATTENDANCE = "1";
    public static final String UPDATE_ATTENDANCE = "2";
    public static final String ATTENDANCE_SHEET = "3";
    public static final String RISK_OF_EXPLUSTION = "4";
    public static final String QUIT = "Q";

    public static final LocalDate DATE = LocalDate.of(2024, 12, 13);

    private InputView inputView;
    private OutputView outputView;
    private AttendanceSheet attendanceSheet;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceSheet attendanceSheet) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceSheet = attendanceSheet;
    }

    public void start() {
        runInputCommand(this::inputCommand);
    }

    private String inputCommand() {
        String select = inputView.inputMenu(DATE);
        switch (select) {
            case CREATE_ATTENDANCE -> attend();
            case UPDATE_ATTENDANCE -> updateAttendance();
            case ATTENDANCE_SHEET -> printAttendance();
            case RISK_OF_EXPLUSTION -> printRiskOfExpulsion();
            case QUIT -> {
                return QUIT;
            }
        }
        return select;
    }

    private void attend() {
        String nickname = inputView.inputNickname();
        LocalTime attendanceTime = inputView.inputTime();

        attendanceSheet.add(nickname, TODAY, attendanceTime);
    }

    private void updateAttendance() {
        String nickname = inputView.inputUpdateNickname();
        String day = inputView.inputUpdateDate();

    }

}
