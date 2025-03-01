package controller;

import static utils.RetryUtils.retryUntilValid;

import domain.AttendanceBook;
import service.CrewRegistrationService;
import service.FunctionService;
import view.input.Function;
import view.input.InputView;
import view.output.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewRegistrationService registration;
    private final FunctionService functionService;

    public AttendanceController(InputView inputView, OutputView outputView, CrewRegistrationService registration,
                                FunctionService functionService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.registration = registration;
        this.functionService = functionService;
    }

    public AttendanceBook init() {
        String filePath = "src/main/resources/attendances.csv";
        return registration.registerCrews(filePath); // 초기화
    }

    public void start() {
        AttendanceBook attendanceBook = init();

        while (true) {
            outputView.displayFunctionPrompt();
            Function function = retryUntilValid(this::selectFunctionByUser);
            try {
                functionService.checkAttendance(function, attendanceBook);
                functionService.modifyAttendance(function, attendanceBook);
                functionService.checkAttendanceRecord(function, attendanceBook);
                functionService.checkPenaltyCrews(function, attendanceBook);
                if (function == Function.QUIT) {
                    System.exit(1);
                }
            } catch (IllegalArgumentException e) {
                OutputView.displayErrorMessage(e.getMessage());
            }
        }
    }

    private Function selectFunctionByUser() {
        return Function.checkFunctionNumber(inputView.askFunctionSelection());
    }
}