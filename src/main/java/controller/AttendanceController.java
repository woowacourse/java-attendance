package controller;

import domain.AttendanceBook;
import domain.AttendanceSystem;
import dto.AttendanceRecordDto;
import dto.AttendanceResultDto;
import dto.RiskCrewDto;
import util.Dates;
import util.Parser;
import view.FileInput;
import view.Input;
import view.Output;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static util.Dates.TODAY;

public class AttendanceController {
    private final FileInput fileInput;
    private final AttendanceSystem attendanceSystem;
    private final Input input;
    private final Output output;

    public AttendanceController(FileInput fileInput, Input input, Output output) {
        this.fileInput = fileInput;
        this.input = input;
        this.output = output;
        attendanceSystem = new AttendanceSystem();
    }

    public void start() {
        initCrew();
        startMenuLoop();
    }

    private void startMenuLoop() {
        boolean continueLoop = true;
        while (continueLoop) {
            continueLoop = handleMenuSelection();
        }
    }

    private boolean handleMenuSelection() {
        String menuSelection = input.getMenuInput(TODAY);
        if (menuSelection.equals("1")) {
            attend();
            return true;
        }
        if (menuSelection.equals("2")) {
            editAttendance();
            return true;
        }
        if (menuSelection.equals("3")) {
            checkAttendanceRecord();
            return true;
        }
        if (menuSelection.equals("4")) {
            checkRiskCrews();
            return true;
        }
        if(menuSelection.equals("Q")) {
            return false;
        }
        throw new IllegalArgumentException("[ERROR] 1, 2, 3, 4, Q만 입력 가능합니다.");
    }

    private void checkRiskCrews() {
        List<RiskCrewDto> riskCrews = attendanceSystem.getRiskCrews().entrySet().stream()
                .map(entry -> new RiskCrewDto(
                        entry.getKey(),
                        entry.getValue().getAbsenceCount(TODAY),
                        entry.getValue().getTardyCount(TODAY),
                        entry.getValue().getRiskStatus(TODAY)
                ))
                .toList();
        output.printRiskCrews(riskCrews);

    }

    private void checkAttendanceRecord() {
        String name = input.getNameInput();
        AttendanceBook attendanceBook = attendanceSystem.findByName(name);
        attendanceBook.getAttendanceBook();
        output.printAttendanceRecord(
                name,
                TODAY,
                attendanceBook.getAttendanceBook(),
                attendanceBook.getAttendanceStatuses());
    }

    private void editAttendance() {
        String name = input.getNameInput();
        AttendanceBook attendanceBook = attendanceSystem.findByName(name);
        LocalDate date = LocalDate.of(2024,
                12,
                Integer.parseInt(input.getEditDateInput()));
        AttendanceResultDto beforeAttendanceResult = new AttendanceResultDto(
                date,
                attendanceBook.getAttendanceTimeByDate(date),
                attendanceBook.getAttendanceStatus(date)
        );
        LocalTime time = Parser.stringToLocalTime(input.getEditTimeInput());
        attendanceBook.attendance(date, time);
        AttendanceResultDto afterAttendanceResult = new AttendanceResultDto(
                date,
                attendanceBook.getAttendanceTimeByDate(date),
                attendanceBook.getAttendanceStatus(date)
        );
        output.printEditAttendanceResult(beforeAttendanceResult, afterAttendanceResult);

    }

    private void attend() {
        if(Dates.isHoliday(TODAY)) {
            throw new IllegalArgumentException("");
        }
        String name = input.getNameInput();
        AttendanceBook attendanceBook = attendanceSystem.findByName(name);
        LocalTime time = Parser.stringToLocalTime(input.getTimeInput());
        attendanceBook.attendance(TODAY, time);
        AttendanceResultDto attendanceResultDto = new AttendanceResultDto(
                TODAY,
                attendanceBook.getAttendanceTimeByDate(TODAY),
                attendanceBook.getAttendanceStatus(TODAY));
        output.printAttendResult(attendanceResultDto);
    }

    private void initCrew() {
        List<AttendanceRecordDto> attendanceRecords = fileInput.getFileInit();
        attendanceRecords.forEach(attendanceRecordDto -> {
            attendanceSystem.editAttendance(attendanceRecordDto.nickname(),
                    attendanceRecordDto.attendanceDateTime().toLocalDate(),
                    attendanceRecordDto.attendanceDateTime().toLocalTime());
        });
    }
}
