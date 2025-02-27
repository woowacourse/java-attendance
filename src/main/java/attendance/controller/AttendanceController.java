package attendance.controller;

import attendance.dto.AttendanceEditDto;
import attendance.dto.AttendanceRemarkDto;
import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {

    private final InputView inputView;
    private final AttendanceService attendanceService;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;

    public AttendanceController(InputView inputView, AttendanceService attendanceService,
                                OutputView outputView, DateGenerator dateGenerator) {
        this.inputView = inputView;
        this.attendanceService = attendanceService;
        this.outputView = outputView;
        this.dateGenerator = dateGenerator;
    }

    public void run() {
        LocalDate today = dateGenerator.generate();
        AttendanceOption option;

        do {
            option = inputView.readAttendanceOption(today);
            execute(option, today);
        } while (option != AttendanceOption.QUIT);
    }

    private void execute(AttendanceOption option, LocalDate today) {
        if (option.equals(AttendanceOption.MARK)) {
            remarkAttendance(today);
        }
        if (option.equals(AttendanceOption.EDIT)) {
            editAttendance();
        }
    }

    private void remarkAttendance(LocalDate today) {
        String name = inputView.readRemarkAttendanceName();
        attendanceService.validateNameExists(name);
        if (attendanceService.hasAttendance(name, today)) {
            outputView.printUseEdit();
            return;
        }

        LocalTime attendanceTime = inputView.readRemarkAttendanceTime();
        AttendanceRemarkDto attendanceRemarkDto = attendanceService.remarkAttendance(name, today, attendanceTime);
        outputView.printRemarkAttendanceResult(attendanceRemarkDto);
    }

    private void editAttendance() {
        String name = inputView.readEditAttendanceName();
        attendanceService.validateNameExists(name);

        LocalDate editAttendanceDate = inputView.readEditAttendanceDate();
        LocalTime editAttendanceTime = inputView.readEditAttendanceTime();
        AttendanceEditDto dto = attendanceService.editAttendance(name, editAttendanceDate, editAttendanceTime);
        outputView.printEditAttendanceResult(dto);
    }
}
