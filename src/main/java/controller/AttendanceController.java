package controller;

import domain.AttendanceRecord;
import domain.Crew;
import domain.CrewAttendanceRecords;
import domain.DateGenerator;
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
        outputView.displayAttendanceRecord(attendanceRecord);
    }

    public void updateAttendance() {
        Crew crew = inputView.readUpdateNickname();
        LocalDate date = inputView.readUpdateDate();
        LocalTime time = inputView.readUpdateTime();
        AttendanceRecord newRecord = new AttendanceRecord(date, time);
        AttendanceRecord oldRecord = crewAttendanceRecords.updateAttendanceRecord(crew, newRecord);
        outputView.displayUpdatedRecord(oldRecord, newRecord);
    }

    public void checkAttendanceRecords() {
        Crew crew = inputView.readNickname();
        outputView.displayAttendanceRecords(crew, crewAttendanceRecords);
    }

    public void checkDisciplinaryStatus() {
        outputView.displayWarnedCrews(crewAttendanceRecords.getWarnedCrews(), crewAttendanceRecords);
    }
}
