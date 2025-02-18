package attendance.controller;

import attendance.domain.MenuCommand;
import attendance.view.Input;
import attendance.view.Output;

public class AttendanceController {
    private final Input input;
    private final Output output;
    private final Crews crews;
    private final Attendances attendances;

    public AttendanceController() {
        this.input = new Input();
        this.output = new Output();
        this.crews = new Crews();
        this.attendances = new Attendances();
    }

    public void run() {
        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource("attendances.csv");

        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);

        MenuCommand command = null;
        // TODO 24년 12월 14일 localdate 객체 만들어서 readCommand 매개변수로 넣어주기
        LocalDate currentDate = LocalDate.of(2024, 12, 14);

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
