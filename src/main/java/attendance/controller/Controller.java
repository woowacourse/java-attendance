package attendance.controller;

import attendance.dto.AttendanceDTO;
import attendance.dto.AttendanceDTO.AttendanceDetailDTO;
import attendance.dto.WarningCrewsDTO;
import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceHistory;
import attendance.model.AttendanceRegister;
import attendance.model.AttendanceTime;
import attendance.model.CustomLocalDateTime;
import attendance.view.InputView;
import attendance.view.OutputView;

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
        String s = inputView.inputCommand();
        if (s.equals("1")) {
            process(this::addAttendance);
        }
        if (s.equals("2")) {
            process(this::modifyAttendance);
        }
        if (s.equals("3")) {
            process(this::displayAttendanceHistory);
        }
        if (s.equals(("4"))) {
            process(this::displayWarningCrew);
        }
        if (s.equals("Q")) {
            System.exit(1);
        }
        run();
    }

    private void addAttendance() {
        AttendanceHistory attendanceHistory = attendanceRegister
                .findAttendanceHistoryByCrewName(inputView.inputCrewName());
        CustomLocalDateTime customLocalDateTime = new CustomLocalDateTime();
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(customLocalDateTime.nowDate()),
                new AttendanceTime(Parser.parseTime(inputView.inputEntryTime()))
        );
        attendanceHistory.addAttendanceDateTime(attendanceDateTime);
        outputView.printAttendanceDetail(AttendanceDetailDTO.fromArriveAttendance(attendanceDateTime));
    }

    private void modifyAttendance() {
        AttendanceHistory attendanceHistory = attendanceRegister
                .findAttendanceHistoryByCrewName(inputView.inputCrewName());
        AttendanceDateTime attendanceDateTime = attendanceHistory.findAttendanceDateTime(new AttendanceDate(
                Parser.parseDate(inputView.inputModifyAttendanceDate())
        ));
        AttendanceDateTime beforeModify = attendanceDateTime.copy();
        attendanceDateTime.modifyAttendanceTime(new AttendanceTime(
                Parser.parseTime(inputView.inputModifyAttendanceTime())
        ));
        outputView.printModifyResult(
                AttendanceDetailDTO.fromArriveAttendance(beforeModify),
                AttendanceDetailDTO.fromArriveAttendance(attendanceDateTime)
        );
    }

    private void displayAttendanceHistory() {
        String crewName = inputView.inputCrewName();
        outputView.printAttendanceHistory(AttendanceDTO.from(
                crewName,
                attendanceRegister.findAttendanceHistoryByCrewName(crewName)
        ));
    }

    private void displayWarningCrew() {
        outputView.printWarningCrews(WarningCrewsDTO.from(attendanceRegister));
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
