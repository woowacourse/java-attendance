package controller;

import domain.*;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    //    private final DateGenerator currentDateGenerator = new CurrentDateGenerator();
    private final DateGenerator currentDateGenerator = () -> LocalDate.of(2024, 12, 13);
    private final CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", currentDateGenerator);

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
        AttendanceRecord attendanceRecord = crewAttendanceRecords.checkIn(crew, time, currentDateGenerator);
        LocalDate date = attendanceRecord.getDate();
        Attendance attendance = attendanceRecord.getAttendance();
        Day day = Day.getDay(date);
        outputView.displayAttendanceRecord(date, day, time, attendance);
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
