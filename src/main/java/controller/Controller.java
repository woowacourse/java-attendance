package controller;

import domain.AttendanceBook;
import service.CrewRegistrationService;
import view.Function;
import view.input.InputView;
import view.output.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewRegistrationService registration;

    public Controller(InputView inputView, OutputView outputView, CrewRegistrationService registration) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.registration = registration;
    }

    public void start() {
        String filePath = "src/main/java/resources/attendances.csv";
        AttendanceBook attendanceBook = registration.registerCrews(filePath); // 초기화

        outputView.displayFunctionPrompt();
        Function function = Function.checkFunctionNumber(inputView.askFunctionSelection());

    }
}