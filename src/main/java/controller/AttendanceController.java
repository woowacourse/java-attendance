package controller;

import domain.AttendanceFileReader;
import java.util.List;
import view.InputView;

public class AttendanceController {

    private final InputView inputView;

    public AttendanceController(final InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        List<String> students = AttendanceFileReader.readFile("src/main/resources/attendances.csv");
        String command = inputView.readCommand();
        if (command.equals("1")) {
            String nickname = inputView.readNickname();
            String time = inputView.readTime();

        }
    }
}
