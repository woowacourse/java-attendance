package controller;

import domain.AttendanceSheet;
import domain.policy.AttendanceState;
import view.InputView;
import view.OutputView;

import java.time.LocalTime;
import java.util.Map;

import static config.AppConfig.TODAY;
import static util.ExceptionHandler.runInputCommand;

public class AttendanceController {

    public static final String CREATE_ATTENDANCE = "1";
    public static final String UPDATE_ATTENDANCE = "2";
    public static final String ATTENDANCE_SHEET = "3";
    public static final String RISK_OF_EXPLUSTION = "4";
    public static final String QUIT = "Q";

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
        String select = inputView.inputMenu();
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
        int day = inputView.inputUpdateDate();
        LocalTime updateTime = inputView.inputUpdateTime();

        attendanceSheet.update(nickname, day, updateTime);
    }

    private void printAttendance() {
        String nickname = inputView.inputNickname();
        outputView.printAttendanceSheetIntro(nickname);

        outputView.printAttendancesSheet(nickname, attendanceSheet.findAttendanceByNickname(nickname));

        Map<AttendanceState, Long> attendanceState = attendanceSheet.countAttendanceState(nickname);
        outputView.printAttendanceStatistics(attendanceState);
        outputView.printAbsentPolicy(attendanceSheet.calculateExpellStatus(attendanceState));
    }

    private void printRiskOfExpulsion() {
        Map<String, Map<AttendanceState, Long>> attendanceStatus = attendanceSheet.countAttendancesState();

        outputView.printRiskOfExpulsion(attendanceStatus, attendanceSheet.calculateAllExpellStatus(attendanceStatus));
    }


}
