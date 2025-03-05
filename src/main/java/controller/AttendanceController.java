package controller;

import domain.AttendanceBook;
import domain.AttendanceDate;
import domain.AttendanceStatuses;
import domain.AttendanceSystem;
import domain.AttendanceTime;
import domain.Crew;
import dto.AttendanceRecordDto;
import dto.AttendanceResultDto;
import dto.AttendanceStatusesDto;
import dto.RiskCrewDto;
import util.Dates;
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
        if (menuOperations.containsKey(menuSelection)) {
            menuOperations.get(menuSelection).run();
            return true;
        }
        throw new IllegalArgumentException("1, 2, 3, 4, Q만 입력 가능합니다.");
    }

    private void checkRiskCrews() {
        List<RiskCrewDto> riskCrews = attendanceSystem.getRiskCrews()
                .entrySet()
                .stream()
                .map(entry -> new RiskCrewDto(
                        entry.getKey().name(),
                        entry.getValue().getAttendanceStatuses().getAbsenceCount(),
                        entry.getValue().getAttendanceStatuses().getTardyCount(),
                        entry.getValue().getAttendanceStatuses().getRiskStatus()
                )).toList();
        output.printRiskCrews(riskCrews);
    }

    private void checkAttendanceRecord() {
        Crew crew = new Crew(input.getNameInput());
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        attendanceBook.getAttendanceBook();
        AttendanceStatuses attendanceStatuses = attendanceBook.getAttendanceStatuses();
        output.printAttendanceRecord(
                crew.name(),
                TODAY,
                Parser.getAttendanceBook(attendanceBook),
                new AttendanceStatusesDto(
                        Parser.getAttendanceStatuses(attendanceStatuses),
                        attendanceStatuses.getAttendCount(),
                        attendanceStatuses.getTardyCount(),
                        attendanceStatuses.getAbsenceCount(),
                        attendanceStatuses.getRiskStatus()
                ));
    }

    private void editAttendance() {
        Crew crew = new Crew(input.getNameInput());
        AttendanceBook attendanceBook = attendanceSystem.findByCrew(crew);
        AttendanceDate date = new AttendanceDate(getEditDate());
        AttendanceResultDto beforeAttendanceResult = getAttendanceResult(date, attendanceBook);
        LocalTime time = Parser.stringToLocalTime(input.getEditTimeInput());
        AttendanceTime attendanceTime = new AttendanceTime(time);
        attendanceBook.attendance(date, attendanceTime);
        AttendanceResultDto afterAttendanceResult = getAttendanceResult(date, attendanceBook);
        output.printEditAttendanceResult(beforeAttendanceResult, afterAttendanceResult);
    }

    private LocalDate getEditDate() {
        return LocalDate.of(TODAY.getYear(),
                TODAY.getMonth(),
                Integer.parseInt(input.getEditDateInput()));
    }

    private AttendanceResultDto getAttendanceResult(AttendanceDate date, AttendanceBook attendanceBook) {
        LocalTime time = attendanceBook.getAttendanceTimeByDate(date)
                .map(AttendanceTime::getTime)
                .orElse(Dates.DEFAULT_TIME);
        return new AttendanceResultDto(
                date.getDate(),
                time,
                attendanceBook.getAttendanceStatus(date)
        );
    }


    private void attend() {
        AttendanceDate attendanceDate = new AttendanceDate(TODAY);

        AttendanceBook attendanceBook = getAttendanceBookByInputName();
        validateHasNoAttendRecordToday(attendanceBook, attendanceDate);

        AttendanceTime attendanceTime = getAttendTimeInput();
        attendanceBook.attendance(attendanceDate, attendanceTime);

        AttendanceResultDto attendanceResultDto = getAttendanceResult(attendanceDate, attendanceBook);
        output.printAttendResult(attendanceResultDto);
    }

    private AttendanceBook getAttendanceBookByInputName() {
        Crew crew = new Crew(input.getNameInput());
        return attendanceSystem.findByCrew(crew);
    }

    private AttendanceTime getAttendTimeInput() {
        LocalTime time = Parser.stringToLocalTime(input.getTimeInput());
        return new AttendanceTime(time);
    }

    private void initCrew() {
        List<AttendanceRecordDto> attendanceRecords = fileInput.getFileInit();
        attendanceRecords.forEach(attendanceRecordDto ->
                attendanceSystem.editAttendance(
                        new Crew(attendanceRecordDto.nickname()),
                        new AttendanceDate(attendanceRecordDto.attendanceDateTime().toLocalDate()),
                        new AttendanceTime(attendanceRecordDto.attendanceDateTime().toLocalTime())));
    }

    private void validateHasNoAttendRecordToday(AttendanceBook attendanceBook, AttendanceDate attendanceDate) {
        if (attendanceBook.hasAttendanceRecord(attendanceDate)) {
            throw new IllegalArgumentException("수정 기능을 사용해주세요.");
        }
    }
}
