package attendance.controller;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.DateInfo;
import attendance.domain.DateInfos;
import attendance.domain.Register;
import attendance.domain.Time;
import attendance.domain.constant.DayOfWeek;
import attendance.domain.constant.Function;
import attendance.exception.CustomException;
import attendance.util.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDate;
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
        LocalDate now = LocalDate.now();
        Register register = new Register(crews, now);
        register.fromCrewAttendanceTimeFile(crews, lines);

        boolean isRunning = true;
        while (isRunning) {
            Function function = readFunction(now);
            isRunning = mappingFunction(function, now, crews, register);
        }
        inputView.closeScanner();
    }

    private boolean mappingFunction(Function function, LocalDate now, Crews crews, Register register) {
        if (function.equals(Function.ONE)) {
            functionOne(now, crews, register);
            return true;
        }
        if (function.equals(Function.TWO)) {
            functionTwo(crews, register);
            return true;
        }
        if (function.equals(Function.THREE)) {
            functionThree(crews, register);
            return true;
        }
        if (function.equals(Function.FOUR)) {
            functionFour(register);
            return true;
        }
        return false;
    }

    private void functionFour(Register register) {
        try {
            outputView.writeDismissCrewCheck(register.findAllExpertRiskCrews());
        } catch (CustomException customException) {
            outputView.errorMessagePrint(customException.getMessage());
        }
    }

    private void functionThree(Crews crews, Register register) {
        Crew crew = findCrew(crews);
        DateInfos dateInfos = register.checkAttendanceHistory(crew);
        outputView.writeAttendanceHistory(crew, dateInfos);
    }

    private void functionTwo(Crews crews, Register register) {
        Crew crew = findModifiyCrew(crews);
        int modifyDate = readModifyDay();
        Time modifyTime = findModifyTime();
        DateInfo beforeInfo = register.findInfo(crew, modifyDate);
        String beforeHour = beforeInfo.getTime().getHour();
        String beforeMinute = beforeInfo.getTime().getMinute();
        String beforeStatus = beforeInfo.getAttendanceStatus();
        DateInfo modifiedInfo = register.modifyInfo(crew, modifyDate, modifyTime);
        outputView.writeAttendanceModifyCheck(beforeHour, beforeMinute, beforeStatus, modifiedInfo);

    }

    private void functionOne(LocalDate now, Crews crews, Register register) {
        Crew crew = findCrew(crews);
        Time attendanceTime = findAttendanceTime();
        DayOfWeek dayOfWeek = DayOfWeek.from(now.getDayOfWeek().getValue());
        DateInfo dateInfo = DateInfo.of(now.getMonthValue(), now.getDayOfMonth(), dayOfWeek, attendanceTime);
        register.modifyInfo(crew, Integer.parseInt(dateInfo.getDay()), attendanceTime);
        outputView.writeAttendanceCheck(dateInfo);
    }

    private Crew findCrew(Crews crews) {
        return retryUntilValidInput(() -> crews.findCrew(inputView.readCrewName()));
    }

    private Crew findModifiyCrew(Crews crews) {
        return retryUntilValidInput(() -> crews.findCrew(inputView.readModifyCrewName()));
    }

    private Time findAttendanceTime() {
        return retryUntilValidInput(() -> Time.from(inputView.readAttendanceTime()));
    }

    private Time findModifyTime() {
        return retryUntilValidInput(() -> Time.from(inputView.readModifyTime()));
    }

    private int readModifyDay() {
        return retryUntilValidInput(() -> Integer.parseInt(inputView.readModifyDay()));
    }

    private Function readFunction(LocalDate now) {
        return retryUntilValidInput(() -> Function.of(inputView.readFunctionChoose(now)));
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
