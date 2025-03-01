package controller;

import domain.AttendanceBook;
import domain.AttendanceSystem;
import domain.Crew;
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
import java.util.Map;

import static util.Dates.TODAY;

public class AttendanceController {
    private final FileInput fileInput;
    private final AttendanceSystem attendanceSystem;
    private final Input input;
    private final Output output;
    private final Map<String, Runnable> menuOperations = Map.of(
            "1", this::attend,
            "2", this::editAttendance,
            "3", this::checkAttendanceRecord,
            "4", this::checkRiskCrews
    );

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
        try {
            return operateMenu();
        } catch (IllegalArgumentException e) {
            output.printError(e.getMessage());
            return true;
        }
    }

    private boolean operateMenu() {
        String menuSelection = input.getMenuInput(TODAY);
        if (menuSelection.equals("Q")) {
            return false;
        }
        if(menuOperations.containsKey(menuSelection)) {
            menuOperations.get(menuSelection).run();
            return true;
        }
        throw new IllegalArgumentException("1, 2, 3, 4, Q만 입력 가능합니다.");
    }

    private void checkRiskCrews() {
        List<RiskCrewDto> riskCrews = attendanceSystem.getRiskCrews().entrySet().stream()
                .map(entry -> new RiskCrewDto(
                        entry.getKey().name(),
                        entry.getValue().getAbsenceCount(TODAY),
                        entry.getValue().getTardyCount(TODAY),
                        entry.getValue().getRiskStatus(TODAY)
                )).toList();
        output.printRiskCrews(riskCrews);

    }

    private void checkAttendanceRecord() {
        Crew crew = new Crew(input.getNameInput());
        AttendanceBook attendanceBook = attendanceSystem.findByName(crew);
        attendanceBook.getAttendanceBook();
        output.printAttendanceRecord(
                crew.name(),
                TODAY,
                attendanceBook.getAttendanceBook(),
                attendanceBook.getAttendanceStatuses());
    }

    private void editAttendance() {
        Crew crew = new Crew(input.getNameInput());
        AttendanceBook attendanceBook = attendanceSystem.findByName(crew);
        LocalDate date = getEditDate();
        AttendanceResultDto beforeAttendanceResult = getAttendanceResult(date, attendanceBook);
        LocalTime time = Parser.stringToLocalTime(input.getEditTimeInput());
        attendanceBook.attendance(date, time);
        AttendanceResultDto afterAttendanceResult = getAttendanceResult(date, attendanceBook);
        output.printEditAttendanceResult(beforeAttendanceResult, afterAttendanceResult);
    }

    private LocalDate getEditDate() {
        return LocalDate.of(TODAY.getYear(),
                TODAY.getMonth(),
                Integer.parseInt(input.getEditDateInput()));
    }

    private AttendanceResultDto getAttendanceResult(LocalDate date, AttendanceBook attendanceBook) {
        return new AttendanceResultDto(
                date,
                attendanceBook.getAttendanceTimeByDate(date),
                attendanceBook.getAttendanceStatus(date)
        );
    }

    private void attend() {
        validateTodayIsHoliday();
        Crew crew = new Crew(input.getNameInput());
        AttendanceBook attendanceBook = attendanceSystem.findByName(crew);
        LocalTime time = Parser.stringToLocalTime(input.getTimeInput());
        attendanceBook.attendance(TODAY, time);
        AttendanceResultDto attendanceResultDto = getAttendanceResult(TODAY, attendanceBook);
        output.printAttendResult(attendanceResultDto);
    }

    private static void validateTodayIsHoliday() {
        if (Dates.isHoliday(TODAY)) {
            throw new IllegalArgumentException(String.format("%s는 등교일이 아닙니다", Parser.localDateToDateMessage(TODAY)));
        }
    }

    private void initCrew() {
        List<AttendanceRecordDto> attendanceRecords = fileInput.getFileInit();
        attendanceRecords.forEach(attendanceRecordDto -> {
            attendanceSystem.editAttendance(new Crew(attendanceRecordDto.nickname()),
                    attendanceRecordDto.attendanceDateTime().toLocalDate(),
                    attendanceRecordDto.attendanceDateTime().toLocalTime());
        });
    }
}
