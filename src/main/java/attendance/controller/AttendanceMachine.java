package attendance.controller;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.DateInfo;
import attendance.domain.AttendanceRegistry;
import attendance.domain.Register;
import attendance.domain.constant.CommandOption;
import attendance.exception.CustomException;
import attendance.util.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        List<String> lines = FileReader.fileReadLine("attendances.csv");
        Crews crews = Crews.fromCrewsFile(lines);
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        Register register = new Register(crews, now);
        register.fromCrewAttendanceTimeFile(crews, lines);

        boolean isRunning = true;
        while (isRunning) {
            CommandOption commandOption = readCommand(now);
            isRunning = mappingCommand(commandOption, now, crews, register);
        }
        inputView.closeScanner();
    }

    //TODO : 객체지향 생활 원칙에서 else를 사용하지 말라고 하였는데, else if도 사용하지 말아야 하는 것인지 궁금합니다.
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
        int modifyDate = readModifyDay();
        List<String> modifyTime = List.of(findModifyTime().split(":"));
        LocalDateTime localDateTime = LocalDateTime.of(now.getYear(), now.getMonthValue(), modifyDate, Integer.parseInt(modifyTime.get(0)), Integer.parseInt(modifyTime.get(1)));
        DateInfo beforeInfo = register.findInfo(crew, localDateTime);
        int beforeHour = beforeInfo.getLocalDateTime().getHour();
        int beforeMinute = beforeInfo.getLocalDateTime().getMinute();
        String beforeStatus = beforeInfo.getAttendanceStatus();
        DateInfo modifiedInfo = register.modifyInfo(crew, localDateTime);
        outputView.writeAttendanceModifyCheck(beforeHour, beforeMinute, beforeStatus, modifiedInfo);
    }

    private void confirmAttendance(LocalDate now, Crews crews, Register register) {
        Crew crew = findCrew(crews);
        List<String> attendanceTime = List.of(findAttendanceTime().split(":"));
        LocalDateTime localDateTime = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), Integer.parseInt(attendanceTime.get(0)), Integer.parseInt(attendanceTime.get(1)));
        DateInfo dateInfo = DateInfo.of(localDateTime);
        register.modifyInfo(crew, localDateTime);
        outputView.writeAttendanceCheck(dateInfo);
    }

    private Crew findCrew(Crews crews) {
        return retryUntilValidInput(() -> crews.findCrew(inputView.readCrewName()));
    }

    private Crew findModifiyCrew(Crews crews) {
        return retryUntilValidInput(() -> crews.findCrew(inputView.readModifyCrewName()));
    }

    private String findAttendanceTime() {
        return retryUntilValidInput(inputView::readAttendanceTime);
    }

    private String findModifyTime() {
        return retryUntilValidInput(inputView::readModifyTime);
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
