package controller;

import domain.AttendanceRecord;
import domain.Crew;
import domain.CrewAttendanceRecords;
import domain.DateGenerator;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Supplier;

public class AttendanceController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    //    private final DateGenerator currentDateGenerator = new CurrentDateGenerator();
    private final DateGenerator currentDateGenerator = () -> LocalDate.of(2024, 12, 13);
    private final CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", currentDateGenerator);

    public void run() {
        String menuInput;
        do {
            menuInput = retryUntilSuccess(() -> {
                String input = inputView.readMenu(currentDateGenerator.generate());
                if (input.equals("1")) {
                    checkIn();
                }
                if (input.equals("2")) {
                    updateAttendance();
                }
                if (input.equals("3")) {
                    checkAttendanceRecords();
                }
                if (input.equals("4")) {
                    checkDisciplinaryStatus();
                }
                return input;
            });
        } while (!menuInput.matches("[Qq]"));
    }

    private String retryUntilSuccess(Supplier<String> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
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
