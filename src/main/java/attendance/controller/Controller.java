package attendance.controller;

import attendance.dto.AttendanceDto;
import attendance.dto.AttendanceDto.AttendanceDetailDto;
import attendance.dto.WarningCrewsDto;
import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRegister;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceRegister attendanceRegister;

    public Controller(InputView inputView, OutputView outputView, AttendanceRegister attendanceRegister) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceRegister = attendanceRegister;
    }

    public void run() {
        String command = inputView.inputCommand();
        if (command.equals("1")) {
            process(this::addAttendance);
        }
        if (command.equals("2")) {
            process(this::modifyAttendance);
        }
        if (command.equals("3")) {
            process(this::displayAttendanceHistory);
        }
        if (command.equals(("4"))) {
            process(this::displayWarningCrew);
        }
        if (command.equals("Q")) {
            System.exit(1);
        }
        run();
    }

    private void addAttendance() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.now());
        String crewName = inputView.inputCrewName();
        attendanceRegister.validateContainsCrewName(crewName);
        LocalTime time = LocalTime.now();
        attendanceRegister.attend(crewName, attendanceDate, time);
        outputView.printAttendanceDetail(AttendanceDetailDto.fromArriveAttendance(new AttendanceDateTime(
                attendanceDate,
                time
        )));
    }

    private void modifyAttendance() {
        String crewName = inputView.inputCrewName();
        AttendanceDate modifyDate = new AttendanceDate(LocalDate.parse(
                String.format("2024-12-%s",
                        inputView.inputModifyAttendanceDate())
        ));
        LocalTime modifyTime = LocalTime.parse(inputView.inputModifyAttendanceTime());
        AttendanceDateTime beforeModify = attendanceRegister
                .findAttendanceDateTimeByCrewName(crewName, modifyDate)
                .copy();
        attendanceRegister.modify(crewName, modifyDate, modifyTime);
        outputView.printModifyResult(
                AttendanceDetailDto.fromArriveAttendance(beforeModify),
                AttendanceDetailDto.fromArriveAttendance(
                        attendanceRegister.findAttendanceDateTimeByCrewName(crewName, modifyDate)
                )
        );
    }

    private void displayAttendanceHistory() {
        String crewName = inputView.inputCrewName();
        outputView.printAttendanceHistory(AttendanceDto.from(
                crewName,
                attendanceRegister.findAttendanceRecordByCrewName(crewName)
        ));
    }

    private void displayWarningCrew() {
        outputView.printWarningCrews(WarningCrewsDto.from(attendanceRegister));
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
