package attendance.controller;

import attendance.dto.AttendanceCheckDto;
import attendance.dto.AttendanceEditDto;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.PenaltyCrewDto;
import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        if (option.equals(AttendanceOption.CHECK)) {
            checkAttendance(today);
        }
        if (option.equals(AttendanceOption.WARNING)) {
            findPenaltyCrews(today);
        }
    }

    private void remarkAttendance(LocalDate today) {
        String name = inputView.readAttendanceName();
        attendanceService.validateNameExists(name);
        if (attendanceService.hasAttendance(name, today)) {
            outputView.printUseEdit();
            return;
        }

        LocalTime attendanceTime = inputView.readRemarkAttendanceTime();
        AttendanceInfoDto attendanceInfoDto = attendanceService.remarkAttendance(name, today, attendanceTime);
        outputView.printRemarkAttendanceResult(attendanceInfoDto);
    }

    private void editAttendance() {
        String name = inputView.readEditAttendanceName();
        attendanceService.validateNameExists(name);

        LocalDate editAttendanceDate = inputView.readEditAttendanceDate();
        LocalTime editAttendanceTime = inputView.readEditAttendanceTime();
        AttendanceEditDto attendanceEditDto = attendanceService.editAttendance(name, editAttendanceDate, editAttendanceTime);
        outputView.printEditAttendanceResult(attendanceEditDto);
    }

    private void checkAttendance(LocalDate today) {
        String name = inputView.readAttendanceName();
        attendanceService.validateNameExists(name);

        AttendanceCheckDto attendanceCheckDto = attendanceService.checkAttendance(name, today);
        outputView.printCheckAttendanceResult(attendanceCheckDto);
    }

    private void findPenaltyCrews(LocalDate today) {
        List<PenaltyCrewDto> penaltyCrewsDto = attendanceService.findPenaltyCrews(today);
        outputView.printPenaltyCrews(penaltyCrewsDto);
    }
}
