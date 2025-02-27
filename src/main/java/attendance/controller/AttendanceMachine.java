package attendance.controller;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.AttendanceChecker;
import attendance.domain.AttendanceRegistry;
import attendance.domain.Register;
import attendance.domain.constant.CommandOption;
import attendance.exception.CustomException;
import attendance.util.AttendanceParser;
import attendance.util.FileReader;
import attendance.util.Parser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Supplier;

public class AttendanceMachine {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceMachine(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() throws IOException {
        AttendanceParser attendanceParser = new AttendanceParser(FileReader.fileReadLine("attendances.csv"));
        Crews crews = attendanceParser.getCrews();
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        Register register = new Register(crews, now);
        register.fromCrewAttendanceTimeFile(attendanceParser.getAttendanceRecords());

        boolean isRunning = true;
        while (isRunning) {
            CommandOption commandOption = readCommand(now);
            isRunning = mappingCommand(commandOption, now, crews, register);
        }
        inputView.closeScanner();
    }

    private boolean mappingCommand(CommandOption commandOption, LocalDate now, Crews crews, Register register) {
        if (commandOption.equals(CommandOption.ONE)) {
            confirmAttendance(now, crews, register);
            return true;
        }
        if (commandOption.equals(CommandOption.TWO)) {
            modifyAttendance(crews, register, now);
            return true;
        }
        if (commandOption.equals(CommandOption.THREE)) {
            showAttendanceHistory(crews, register);
            return true;
        }
        if (commandOption.equals(CommandOption.FOUR)) {
            showExclusionCrews(register);
            return true;
        }
        return false;
    }

    private void showExclusionCrews(Register register) {
        try {
            outputView.writeDismissCrewCheck(register.findAllExpertRiskCrews());
        } catch (CustomException customException) {
            outputView.errorMessagePrint(customException.getMessage());
        }
    }

    private void showAttendanceHistory(Crews crews, Register register) {
        Crew crew = findCrew(crews);
        AttendanceRegistry attendanceRegistry = register.checkAttendanceHistory(crew);
        outputView.writeAttendanceHistory(crew, attendanceRegistry);
    }

    private void modifyAttendance(Crews crews, Register register, LocalDate now) {
        Crew crew = findModifiyCrew(crews);
        LocalDateTime targetDateTime = readModifyDateTime(now);
        AttendanceChecker beforeInfo = register.findInfo(crew, targetDateTime);
        LocalTime beforeTime = LocalTime.of(beforeInfo.getLocalDateTime().getHour(), beforeInfo.getLocalDateTime().getMinute());
        String beforeStatus = beforeInfo.getAttendanceStatus();
        AttendanceChecker modifiedInfo = register.modifyInfo(crew, targetDateTime);
        outputView.writeAttendanceModifyCheck(beforeTime, beforeStatus, modifiedInfo);
    }

    private LocalDateTime readModifyDateTime(LocalDate now) {
        int modifyDate = readModifyDay();
        List<String> modifyTime = findModifyTime();
        return now.withDayOfMonth(modifyDate).atTime(
                Parser.convertToNumber(modifyTime.getFirst()),
                Parser.convertToNumber(modifyTime.getLast())
        );
    }

    private void confirmAttendance(LocalDate now, Crews crews, Register register) {
        Crew crew = findCrew(crews);
        LocalDateTime attendanceTime = readAttendanceDateTime(now);
        register.modifyInfo(crew, attendanceTime);
        outputView.writeAttendanceCheck(new AttendanceChecker(attendanceTime));
    }

    private LocalDateTime readAttendanceDateTime(LocalDate now) {
        List<String> attendanceTime = findAttendanceTime();
        return now.atTime(Parser.convertToNumber(attendanceTime.getFirst()),
                Parser.convertToNumber(attendanceTime.getLast()));
    }

    private Crew findCrew(Crews crews) {
        return retryUntilValidInput(() -> crews.findCrew(inputView.readCrewName()));
    }

    private Crew findModifiyCrew(Crews crews) {
        return retryUntilValidInput(() -> crews.findCrew(inputView.readModifyCrewName()));
    }

    private List<String> findAttendanceTime() {
        return Parser.convertToGroup(retryUntilValidInput(inputView::readAttendanceTime));
    }

    private List<String> findModifyTime() {
        return Parser.convertToGroup(retryUntilValidInput(inputView::readModifyTime));
    }

    private int readModifyDay() {
        return retryUntilValidInput(() -> Integer.parseInt(inputView.readModifyDay()));
    }

    private CommandOption readCommand(LocalDate now) {
        return retryUntilValidInput(() -> CommandOption.of(inputView.readFunctionChoose(now)));
    }

    private <T> T retryUntilValidInput(final Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (CustomException e) {
                outputView.errorMessagePrint(e.getMessage());
            }
        }
    }

}
