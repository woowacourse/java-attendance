package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceCheckResult;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.dto.AttendanceCheckDto;
import attendance.dto.AttendanceEditDto;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.PenaltyCrewDto;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceController {

    private final InputView inputView;
    private final AttendanceManager attendanceManager;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;

    public AttendanceController(InputView inputView, AttendanceManager attendanceManager,
                                OutputView outputView, DateGenerator dateGenerator) {
        this.inputView = inputView;
        this.attendanceManager = attendanceManager;
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
        LocalTime attendanceTime = inputView.readRemarkAttendanceTime();
        Attendance attendance = attendanceManager.remarkAttendance(name, today, attendanceTime);
        AttendanceStatus attendanceStatus = attendanceManager.getAttendanceStatus(today, attendanceTime);
        AttendanceInfoDto attendanceInfoDto = AttendanceInfoDto.of(attendance.getAttendanceDate(), attendance.getAttendanceTime(), attendanceStatus);
        outputView.printRemarkAttendanceResult(attendanceInfoDto);
    }

    private void editAttendance() {
        String name = inputView.readEditAttendanceName();
        LocalDate editAttendanceDate = inputView.readEditAttendanceDate();
        LocalTime editAttendanceTime = inputView.readEditAttendanceTime();
        Attendance beforeEditAttendance = attendanceManager.editAttendance(name, editAttendanceDate, editAttendanceTime);
        AttendanceEditDto attendanceEditDto = convertToAttendanceEditDto(editAttendanceDate, beforeEditAttendance, editAttendanceTime);
        outputView.printEditAttendanceResult(attendanceEditDto);
    }

    private AttendanceEditDto convertToAttendanceEditDto(LocalDate editAttendanceDate, Attendance beforeEditAttendance, LocalTime editAttendanceTime) {
        return AttendanceEditDto.of(
            editAttendanceDate, beforeEditAttendance.getAttendanceTime(),
            AttendanceStatus.findAttendanceStatus(editAttendanceDate, beforeEditAttendance.getAttendanceTime()),
            editAttendanceTime,
            AttendanceStatus.findAttendanceStatus(editAttendanceDate, editAttendanceTime));
    }

    private void checkAttendance(LocalDate today) {
        String name = inputView.readAttendanceName();
        AttendanceCheckResult checkResult = attendanceManager.checkAttendance(name, today);
        List<AttendanceInfoDto> attendanceDtos = convertToAttendanceInfoDto(checkResult);
        AttendanceCheckDto attendanceCheckDto = AttendanceCheckDto.of(
            checkResult.name(), attendanceDtos, checkResult.attendanceStatusCount(), checkResult.attendancePenalty()
        );
        outputView.printCheckAttendanceResult(attendanceCheckDto);
    }

    private static List<AttendanceInfoDto> convertToAttendanceInfoDto(AttendanceCheckResult checkResult) {
        return checkResult.attendanceUntilYesterday().stream()
            .map(att -> AttendanceInfoDto.of(att.getAttendanceDate(), att.getAttendanceTime(),
                AttendanceStatus.findAttendanceStatus(att.getAttendanceDate(), att.getAttendanceTime())))
            .collect(Collectors.toList());
    }

    private void findPenaltyCrews(LocalDate today) {
        List<PenaltyCrewDto> penaltyCrewsDto = attendanceManager.findPenaltyCrews(today).stream()
            .map(PenaltyCrewDto::of)
            .collect(Collectors.toList());
        outputView.printPenaltyCrews(penaltyCrewsDto);
    }
}
