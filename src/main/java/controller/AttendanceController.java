package controller;

import domain.AttendanceManager;
import domain.DateProvider;
import infrastructure.AttendanceFileReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(AttendanceFileReader attendanceFileReader, DateProvider dateProvider) {
        AttendanceManager attendanceManager = new AttendanceManager(dateProvider);
        attendanceFileReader.readFiles(attendanceManager);

        String command;
        do {
            command = inputView.readCommand(dateProvider.getDate());
            execute(command, attendanceManager);
        } while (!command.equals("Q"));
    }

    private void execute(String command, AttendanceManager attendanceManager) {
        try {
            switch (command) {

                default -> throw new IllegalArgumentException("[ERROR] 올바른 명령어를 입력해주세요.");
            }
        } catch (Exception e) {

        }
    }

}
