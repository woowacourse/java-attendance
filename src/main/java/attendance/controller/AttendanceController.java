package attendance.controller;

import attendance.domain.Attendances;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.util.FileReader;
import attendance.view.Input;
import attendance.view.Output;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class AttendanceController {
    private final Input input;
    private final Output output;
    private final Crews crews;
    private final Attendances attendances;

    public AttendanceController() {
        this.input = new Input();
        this.output = new Output();
        this.crews = new Crews();
        this.attendances = new Attendances();
    }

    public void run() {
        initDataFromCSV();

        MenuCommand command = null;
        while (!MenuCommand.QUIT.equals(command)) {
            command = MenuCommand.toCommand(getMenuOption());
            executeCommand(command);
        }
    }

    private void initDataFromCSV() {
        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource("attendances.csv");

        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);
    }

    private String getMenuOption() {
        LocalDate currentDate = LocalDate.now();

        String month = String.valueOf(currentDate.getMonthValue());
        String day = String.valueOf(currentDate.getDayOfMonth());
        String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);

        return input.readCommand(month, day, dayOfWeek);
    }

    private void executeCommand(final MenuCommand command) {
        if (command.equals(MenuCommand.ATTEND)) {
            // 출석 확인 기능 구현
            checkCrewAttendance();
        }
        if (command.equals(MenuCommand.MODIFY)) {
            // 출석 수정 기능 구현
            modifyCrewAttendance();
        }
        if (command.equals(MenuCommand.LOOKUP)) {
            // 출석 기록 조회 기능 구현
            lookupCrewAttendanceHistory();
        }
        if (command.equals(MenuCommand.EXPEL)) {
            // 제적 위험자 조회 기능 구현
            lookupCrewsExpelStatus();
        }
    }

    private void checkCrewAttendance() {
        // crewName -> findCrew (Crews) -> addCrewAttendance (Attendances)

    }

    private void modifyCrewAttendance() {
        // crewName -> findCrew (Crews) -> findCrewAttendance (Attendances) -> modifyCrewAttendance (Attendances)

    }

    private void lookupCrewAttendanceHistory() {
        // crewName -> findCrew (Crews) -> findCrewAttendances (Attendances)

    }

    private void lookupCrewsExpelStatus() {
        // showExpelStatus (Crews)

    }
}
