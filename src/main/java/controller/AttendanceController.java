package controller;

import domain.Crews;
import view.InputView;
import view.OutputView;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AttendanceController {
    InputView inputView;
    OutputView outputView;
    //TODO: 재입력

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        String menu = inputView.inputMenu();
        initCrews();
    }

    public void initCrews() {
        Crews crews = inputView.getFile();
    }

    public void selectMenu(String menu) {
        if(menu.equals("1")) {

        }
    }

    public void attendCrew() {
        try {
            String nickName = inputView.inputNickName();
            LocalTime time = LocalTime.parse(inputView.inputAttendTime());
        } catch (DateTimeParseException e) {
            System.out.println("[ERROR]");
        }
    }
}
