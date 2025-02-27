package controller;

import domain.AttendanceDate;
import domain.AttendanceReader;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.CrewAttendance;
import domain.CrewAttendances;
import domain.CrewName;
import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Supplier;
import strategy.AttendanceCurrentDateGenerateStrategy;
import view.InputMethod;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final CrewAttendances crewAttendances;
    private final InputView inputView;
    private final OutputView outputView;
    private final String NOT_SUPPORTED_METHOD = "아직 지원하지 않는 기능입니다.";
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:MM");
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd");
    private final String INVALID_TIME_FORMAT = "유효하지 않은 시간 양식 입니다. (HH:MM)";


    public AttendanceController(String fileName, OutputView outputView, InputView inputView) {
        AttendanceReader attendanceReader = new AttendanceReader(fileName);
        crewAttendances = new CrewAttendances(new AttendanceCurrentDateGenerateStrategy(),
                attendanceReader.readAttendances());
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        InputMethod inputMethod = null;
        while (inputMethod != InputMethod.QUIT) {
            inputMethod = inputMethod();
            executeWithErrorHandling(inputMethod);
        }
    }

    private void executeWithErrorHandling(InputMethod inputMethod) {
        try {
            executeMethod(inputMethod);
        } catch (AttendanceException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void executeMethod(InputMethod inputMethod) {
        switch (inputMethod) {
            case ATTENDANCE -> handleAddAttendance();
//            case MODIFY -> handleInput(handleModify());
//            case ATTENDANCE_HISTORY -> handleInput(handleAttendanceHistory());
//            case DISMISS_HISTORY -> handleInput(handleDismissHistory());
            default -> throw new AttendanceException(NOT_SUPPORTED_METHOD);
        }
    }

    private void handleAddAttendance() {
        validateAttendanceDate(LocalDate.now());
        String crewNickname = handleInput(this::inputNickname);
        LocalDate attendanceDate = LocalDate.now();
        LocalTime attendanceTime = handleInput(() -> inputTime(attendanceDate));
        crewAttendances.addAttendance(crewNickname, attendanceTime);
        CrewAttendance crewAttendance = crewAttendances.crewAttendance(crewNickname, attendanceDate);
        AttendanceStatus attendanceStatus = crewAttendance.attendanceStatus();
        LocalTime resultAttendanceTime = crewAttendance.attendanceTime();
        outputView.printAttendanceDateTime(attendanceStatus.getStatus(), resultAttendanceTime, attendanceDate);
    }

    private void validateAttendanceDate(LocalDate date) {
        new AttendanceDate(date);
    }

    private LocalTime inputTime(LocalDate attendanceDate) {
        outputView.printAddAttendanceInputTime();
        String inputTime = inputView.input();
        LocalTime time = parseTime(inputTime);
        validateAttendanceTime(time, attendanceDate);
        return time;
    }

    private String inputNickname() {
        outputView.printInputNickname();
        String nickname = inputView.input();
        validateCrewName(nickname);
        return nickname;
    }

    private void validateAttendanceTime(LocalTime time, LocalDate attendanceDate) {
        new AttendanceTime(time, new AttendanceDate(attendanceDate));
    }

    private void validateCrewName(String nickname) {
        new CrewName(nickname);
    }

    private LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new AttendanceException(INVALID_TIME_FORMAT);
        }
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    private InputMethod inputMethod() {
        return handleInput(() -> {
            outputView.printInputMethod();
            return inputView.inputMethod();
        });
    }

    private <T> T handleInput(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (AttendanceException e) {
            outputView.printError(e.getMessage());
            return handleInput(inputSupplier);
        }
    }
}
