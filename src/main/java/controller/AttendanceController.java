package controller;

import domain.AttendanceDate;
import domain.AttendanceReader;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.CrewAttendance;
import domain.CrewAttendances;
import domain.CrewDismiss;
import domain.CrewDismissHistory;
import domain.CrewName;
import domain.SystemTimeCrewAttendanceHistories;
import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import strategy.AttendanceCurrentDateGenerateStrategy;
import view.InputMethod;
import view.InputView;
import view.OutputView;
import view.dto.AttendanceHistoryDto;
import view.dto.CrewDismissHistoryDto;

public class AttendanceController {

    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy MM d");
    private final int SYSTEM_RUNNING_YEAR = 2024;
    private final int SYSTEM_RUNNING_MONTH = 12;
    private final String MODIFY_DATE_FORMAT = "%s %s %s";
    private final String NOT_SUPPORTED_METHOD = "아직 지원하지 않는 기능입니다.";
    private final String INVALID_TIME_FORMAT = "유효하지 않은 시간 양식 입니다. (HH:MM)";
    private final String NOT_EXIST_ATTENDNACE = "존재하지 않는 출석 기록입니다";
    private final String INVALID_DATE_FORMAT = "유효하지 않은 날짜 양식입니다. (d)";

    private final CrewAttendances crewAttendances;
    private final InputView inputView;
    private final OutputView outputView;

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
            case MODIFY -> handleModify();
            case ATTENDANCE_HISTORY -> handleAttendanceHistory();
            case DISMISS_HISTORY -> handleDismissHistory();
            case QUIT -> {
            }
            default -> throw new AttendanceException(NOT_SUPPORTED_METHOD);
        }
    }

    private void handleDismissHistory() {
        List<CrewDismissHistory> crewDismisses = crewAttendances.orderedDismissHistory();
        outputView.printCrewDismisses(crewDismisses.stream()
                .map(CrewDismissHistoryDto::from)
                .collect(Collectors.toList()));
    }

    private void handleAttendanceHistory() {
        String crewNickname = handleInput(this::handleAddAttendanceNickname);
        SystemTimeCrewAttendanceHistories systemTimeCrewAttendanceHistories = crewAttendances.crewAttendancesHistory(
                crewNickname);
        CrewDismiss crewDismiss = systemTimeCrewAttendanceHistories.crewDismiss();
        Map<LocalDate, CrewAttendance> dateCrewAttendance = systemTimeCrewAttendanceHistories.renewDateCrewAttendance();
        printDateCrewAttendanceHistory(dateCrewAttendance, crewNickname);
        outputView.printCrewDismissCount(crewDismiss.attendance(), crewDismiss.late(),
                crewDismiss.absence());
        outputView.printCrewDismissStatus(crewDismiss.dismissStatus().status);
    }

    private void printDateCrewAttendanceHistory(Map<LocalDate, CrewAttendance> dateCrewAttendance,
                                                String crewNickname) {
        List<AttendanceHistoryDto> attendanceHistoryDtos = AttendanceHistoryDto.from(dateCrewAttendance);
        outputView.printCrewAttendanceHistory(attendanceHistoryDtos, crewNickname);
    }

    private void handleModify() {
        String crewNickname = handleInput(this::handleModifyAttendanceNickname);
        validateExistAttendance(crewNickname);
        LocalDate attendanceDate = handleInput(this::handleAttendanceInputDate);
        LocalTime attendanceTime = handleInput(() -> handleAddAttendanceTime(attendanceDate));
        CrewAttendance prevAttendance = crewAttendances.crewAttendance(crewNickname, attendanceDate);
        crewAttendances.modifyAttendance(crewNickname, attendanceDate, attendanceTime);
        CrewAttendance afterCrewAttendance = crewAttendances.crewAttendance(crewNickname, attendanceDate);
        String attendanceStatusMessage = afterCrewAttendance.attendanceStatusMessage();
        printPreviousAttendanceDateTimeStatus(prevAttendance, attendanceDate);
        outputView.printAttendanceTimeStatus(afterCrewAttendance.attendanceTime(), attendanceStatusMessage);
    }

    private void printPreviousAttendanceDateTimeStatus(CrewAttendance prevAttendance, LocalDate attendanceDate) {
        outputView.printPreviousAttendance(attendanceDate, prevAttendance.attendanceTime(),
                prevAttendance.attendanceStatusMessage());
    }

    private void validateExistAttendance(String crewNickname) {
        if (!crewAttendances.isExistAttendance(crewNickname)) {
            throw new AttendanceException(NOT_EXIST_ATTENDNACE);
        }
    }

    private String handleModifyAttendanceNickname() {
        outputView.printInputModifyNickname();
        return inputNickname();
    }

    private String handleAddAttendanceNickname() {
        outputView.printInputNickname();
        return inputNickname();
    }

    private LocalDate handleAttendanceInputDate() {
        outputView.printInputDate();
        return inputDate();
    }

    private LocalDate inputDate() {
        String inputDate = inputView.input();
        String systemFormattedDate = String.format(MODIFY_DATE_FORMAT, SYSTEM_RUNNING_YEAR, SYSTEM_RUNNING_MONTH,
                inputDate);
        LocalDate date = parseDate(systemFormattedDate);
        validateAttendanceDate(date);
        return date;
    }

    private void handleAddAttendance() {
        validateAttendanceDate(LocalDate.now());
        String crewNickname = handleInput(this::handleAddAttendanceNickname);
        LocalDate attendanceDate = LocalDate.now();
        LocalTime attendanceTime = handleInput(() -> handleAddAttendanceTime(attendanceDate));
        crewAttendances.addAttendance(crewNickname, attendanceTime);
        CrewAttendance crewAttendance = crewAttendances.crewAttendance(crewNickname, attendanceDate);
        String attendanceStatus = crewAttendance.attendanceStatusMessage();
        LocalTime resultAttendanceTime = crewAttendance.attendanceTime();
        outputView.printAttendanceDateTimeStatus(attendanceStatus, resultAttendanceTime, attendanceDate);
        outputView.newLine();
    }

    private LocalTime handleAddAttendanceTime(LocalDate attendanceDate) {
        outputView.printAddAttendanceInputTime();
        return inputTime(attendanceDate);
    }

    private void validateAttendanceDate(LocalDate date) {
        new AttendanceDate(date);
    }

    private LocalTime inputTime(LocalDate attendanceDate) {
        String inputTime = inputView.input();
        LocalTime time = parseTime(inputTime);
        validateAttendanceTime(time, attendanceDate);
        return time;
    }

    private String inputNickname() {
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
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new AttendanceException(INVALID_DATE_FORMAT);
        }
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
