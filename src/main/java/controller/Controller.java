package controller;

import domain.Option;
import java.time.LocalDate;
import view.InputView;

public class Controller {

    private static final int ATTENDANCE_YEAR = 2024;
    private static final int ATTENDANCE_MONTH = 12;
    private static final int ATTENDANCE_DAY_OF_MONTH = 16;

    private final InputView inputView;

    public Controller(InputView inputView) {
        this.inputView = inputView;
    }

    public void runSystem() {
        LocalDate nowDate = LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, ATTENDANCE_DAY_OF_MONTH);

        try {
            selectOption(nowDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void selectOption(LocalDate nowDate) {
        while (true) {
            Option option = Option.from(inputView.readOptionNumber(nowDate));

            if (option == Option.ATTEND) {
                attend();
            }
            if (option == Option.EDIT) {
                edit();
            }
            if (option == Option.CHECK_RECORDS) {
                checkRecords();
            }
            if (option == Option.CHECK_EXPULSION_RISK_CREW) {
                checkExpulsionRiskCrew();
            }
            if (option == Option.QUIT) {
                break;
            }
        }
    }

    private void attend() {
    }

    private void edit() {

    }

    private void checkRecords() {

    }

    private void checkExpulsionRiskCrew() {
    }
}
