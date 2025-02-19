package controller;

import domain.Crew;
import domain.CurrentDateGenerator;
import domain.DateGenerator;
import view.InputView;

import java.time.LocalTime;

public class AttendanceController {
    private final InputView inputView = new InputView();
    private final DateGenerator currentDateGenerator = new CurrentDateGenerator();

    public void run() {
        String menuInput = inputView.readMenu(currentDateGenerator.generate());
        if (menuInput.matches("[Qq]")) {
            return;
        }
        if (menuInput.equals("1")) {
            checkIn();
        }
        if (menuInput.equals("2")) {
            updateAttendance();
        }
        if (menuInput.equals("3")) {
            checkAttendanceRecords();
        }
        if (menuInput.equals("4")) {
            checkDisciplinaryStatus();
        }
    }

    public void checkIn() {
        Crew crew = inputView.readNickname();
        LocalTime time = inputView.readCheckInTime();
    }

    public void updateAttendance() {
        System.out.println("출석 수정");
    }

    public void checkAttendanceRecords() {
        System.out.println("출석 기록 확인");
    }

    public void checkDisciplinaryStatus() {
        System.out.println("제적 위험자 확인");
    }
}
