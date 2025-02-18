package attendance.controller;

import attendance.domain.MenuCommand;
import attendance.view.Input;
import attendance.view.Output;

public class AttendanceController {
    private final Input input;
    private final Output output;
//    private final Crews crews;

    public AttendanceController() {
        this.input = new Input();
        this.output = new Output();
        //this.crews = 블라블라
    }

    public void run() {
        MenuCommand command = null;
        // TODO 24년 12월 14일 localdatetime 객체 만들어서 readCommand 매개변수로 넣어주기
        while (!MenuCommand.QUIT.equals(command)) {
            command = MenuCommand.toCommand(input.readCommand());
            executeCommand(command);
        }
    }

    private void executeCommand(MenuCommand command) {
        if (command.equals(MenuCommand.ATTEND)) {
            // 출석기능 구현
        }
        if (command.equals(MenuCommand.MODIFY)) {
            // 출석 수정 기능 구현
        }
        if (command.equals(MenuCommand.LOOKUP)) {
            // 출석 기록 조회 기능 구현
        }
        if (command.equals(MenuCommand.EXPEL)) {
            // 제적 위험자 조회 기능 구현
        }
    }
}
