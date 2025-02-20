package controller;

import static global.util.DateUtil.assembleDateAndTime;
import static global.util.Validator.validateIsFutureDate;
import static global.util.Validator.validateIsNotWorkingDay;

import domain.Crews;
import global.util.DateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import view.InputView;
import view.OutputView;

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
        if (menu.equals("1")) {
            validateIsNotWorkingDay(DateUtil.TODAY.toLocalDate());
            attendCrew();
            return;
        }
        if (menu.equals("2")) {
            editAttend();
            return;
        }
        if (menu.equals("3")) {
            checkCrewsRecord();
            return;
        }
        if (menu.equals("4")) {
            checkRiskCrews();
            return;
        }
        if (menu.equals("Q")) {
            return;
        }
        throw new IllegalArgumentException("메뉴는 1, 2, 3, 4, Q만 입력할 수 있습니다.");
    }

    private void checkRiskCrews() {
        outputView.printRiskCrews(crews.getCrewResponseWithRisk());
    }

    private void checkCrewsRecord() {
        String name = inputView.inputName();
        outputView.printCrewAttendanceRecord(crews.createCrewResponseByName(name));
    }

    public void attendCrew() {
        try {
            String name = inputView.inputName();
            if (!crews.hasCrewName(name)) throw new IllegalArgumentException();
            LocalTime time = LocalTime.parse(inputView.inputAttendTime());
            crews.addAttendStatus(name, assembleDateAndTime(DateUtil.TODAY.toLocalDate(), time));
        } catch (DateTimeParseException | IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            attendCrew();
        }
    }

    private void editAttend() {
        String name = inputView.inputEditCrewName();
        if (!crews.hasCrewName(name)) throw new IllegalArgumentException();
        LocalDate day = DateUtil.getDateByInputDay(Integer.parseInt(inputView.inputEditDay()));
        validateIsFutureDate(day);
        LocalTime time = LocalTime.parse(inputView.inputEditTime());
        crews.editAttendStatus(name, assembleDateAndTime(day, time));
    }
}
