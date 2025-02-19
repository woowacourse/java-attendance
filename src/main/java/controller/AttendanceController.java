package controller;

import domain.Crews;
import domain.util.DateUtil;
import view.InputView;
import view.OutputView;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static domain.util.DateUtil.assembleDateAndTime;

public class AttendanceController {
    InputView inputView;
    OutputView outputView;
    Crews crews;
    //TODO: 재입력

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        crews = initCrews();
        String menu = inputView.inputMenu();
        selectMenu(menu);
    }

    public Crews initCrews() {
        return inputView.getFile();
    }

    public void selectMenu(String menu) {
        if(menu.equals("1")) {
            attendCrew();
            return;
        }
        if(menu.equals("2")) {
            editAttend();
            return;
        }
        if(menu.equals("3")) {
            checkCrewsRecord();
            return;
        }
        if (menu.equals("4")) {
            return;
        }
        if (menu.equals("Q")) {
            return;
        }
        throw new IllegalArgumentException();
    }

    private void checkCrewsRecord() {
        String name = inputView.inputName();

    }

    private void editAttend() {
        String name = inputView.inputEditCrewName();
        if(!crews.hasCrewName(name)) throw new IllegalArgumentException();
        String day = inputView.inputEditDay();
        //TODO: day 검증, time 검증
        String time = inputView.inputEditTime();
        crews.editAttendStatus(name, assembleDateAndTime(DateUtil.getDateByInputDay(Integer.parseInt(day)), LocalTime.parse(time)));
    }

    public void attendCrew() {
        try {
            String name = inputView.inputName();
            if(!crews.hasCrewName(name)) throw new IllegalArgumentException();
            LocalTime time = LocalTime.parse(inputView.inputAttendTime());
            crews.addAttendStatus(name, assembleDateAndTime(DateUtil.TODAY.toLocalDate(), time));
        } catch (DateTimeParseException | IllegalArgumentException e) {
            System.out.println("[ERROR]");
        }
    }
}
