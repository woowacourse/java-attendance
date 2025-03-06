package controller;

import domain.AbsenceLevel;
import domain.AttendanceHistory;
import domain.Attendances;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import view.CsvReader;
import view.InputView;
import view.Menu;
import view.OutputView;

public class AttendanceController {
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();
    private final CsvReader csvReader = new CsvReader();
    private final AttendanceHistory attendanceHistory;
    private final LocalDate today = LocalDate.of(2024, 12, 18);

    public AttendanceController() {
        this.attendanceHistory = initializeAttendanceData();
    }

    private AttendanceHistory initializeAttendanceData() {
        return transformToAttendanceHistory(csvReader.readAttendanceFile());
    }

    private AttendanceHistory transformToAttendanceHistory(java.util.Map<String, List<LocalDateTime>> rawData) {
        java.util.Map<String, Attendances> formattedRecords = new java.util.HashMap<>();
        for (var entry : rawData.entrySet()) {
            formattedRecords.put(entry.getKey(), new Attendances(entry.getValue()));
        }
        return new AttendanceHistory(formattedRecords);
    }

    public void run() {
        while (true) {
            try {
                if (continueMenu()) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return;
    }

    private boolean continueMenu() {
        outputView.outputMenu(today);
        Menu selectedMenu = inputView.readMenu();
        if (selectedMenu == Menu.CHECK_ATTENDANCE) {
            checkAttendance();
        }
        if (selectedMenu == Menu.EDIT_ATTENDANCE) {
            editAttendance();
        }
        if (selectedMenu == Menu.GET_ALL_ATTENDANCE) {
            getAllAttendances();
        }
        if (selectedMenu == Menu.GET_DANGEROUS_CREW) {
            checkDangerousCrews();
        }
        if (selectedMenu == Menu.QUIT) {
            return true;
        }
        return false;
    }

    private void checkAttendance() {
        String name = inputView.inputName();
        LocalTime attendanceTime = inputView.inputAttendanceTime();
        LocalDateTime attendanceDateTime = today.atTime(attendanceTime);
        attendanceHistory.checkAttendance(name, attendanceDateTime);
        outputView.outputAttendance(attendanceDateTime);
    }

    private void editAttendance() {
        String name = inputView.inputEditName();
        LocalDate newDate = inputView.inputDateForEdit();
        LocalTime newTime = inputView.inputTimeForEdit();
        LocalDateTime newDateTime = LocalDateTime.of(newDate, newTime);
        LocalDateTime oldDateTime = attendanceHistory.editAttendance(name, newDateTime);
        outputView.outputResult(oldDateTime, newDateTime);
    }

    private void getAllAttendances() {
        String name = inputView.inputName();
        Attendances records = attendanceHistory.getAttendances(name);
        outputView.outputAttendances(name, records, today);
        outputView.outputCountOfAttendances(name, attendanceHistory, today);
        AbsenceLevel absenceLevel = attendanceHistory.getAbsenceLevel(name, today);
        if (absenceLevel != AbsenceLevel.NORMAL) {
            outputView.outputAbsenceLevel(absenceLevel);
        }
    }

    private void checkDangerousCrews() {
        List<String> badCrews = attendanceHistory.getAbsenceLevelCrews(today);
        outputView.outputDangerousCrews(badCrews, attendanceHistory, today);
    }
}