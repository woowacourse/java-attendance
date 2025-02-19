package attendance.controller;

import attendance.domain.CrewManager;
import attendance.view.InputView;
import java.time.LocalDate;

public class AttendanceController {

    private final InputView inputView;
    private final CrewManager crewManager;

    public AttendanceController(InputView inputView, CrewManager crewManager) {
        this.inputView = inputView;
        this.crewManager = crewManager;
    }

    public void start() {
        String option = inputView.inputOption(LocalDate.now());
        if (option.equals("1")) {
            return;
        }
        if (option.equals("2")) {
            return;
        }
        if (option.equals("3")) {
            return;
        }
        if (option.equals("4")) {
            return;
        }
        if (option.equals("Q")) {
            return;
        }
        throw new IllegalArgumentException("잘못된 입력 입니다.");
    }
}
