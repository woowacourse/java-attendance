package attendance.controller;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.DateInfo;
import attendance.domain.DateInfos;
import attendance.domain.Register;
import attendance.domain.CampusTime;
import attendance.domain.constant.DayOfWeek;
import attendance.domain.constant.AttendanceOperation;
import attendance.exception.CustomException;
import attendance.util.FileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Supplier;

public class AttendanceMachine {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceMachine(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() throws IOException {
        Crews crews = Crews.fromCrewNames(FileReader.fileReadCrewNames("attendances.csv"));
        LocalDate now = LocalDate.now();
        Register register = new Register(crews, now);
        register.fromCrewAttendanceTimeFile(crews, FileReader.fileReadLine("attendances.csv"));

        boolean flag = true;
        while (flag) {
            AttendanceOperation attendanceOperation = readFunction(now);
            flag = mappingFunction(attendanceOperation, now, crews, register);
        }
        inputView.closeScanner();
    }

    private boolean mappingFunction(AttendanceOperation attendanceOperation, LocalDate now, Crews crews, Register register) {
        if (attendanceOperation.equals(AttendanceOperation.ONE)) {
            functionOne(now, crews, register);
            return true;
        }
        if (attendanceOperation.equals(AttendanceOperation.TWO)) {
            functionTwo(crews, register);
            return true;
        }
        if (attendanceOperation.equals(AttendanceOperation.THREE)) {
            functionThree(crews, register);
            return true;
        }
        if (attendanceOperation.equals(AttendanceOperation.FOUR)) {
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
        CampusTime modifyCampusTime = findModifyTime();
        DateInfo beforeInfo = register.findInfo(crew, modifyDate);
        String beforeHour = beforeInfo.getTime().getHour();
        String beforeMinute = beforeInfo.getTime().getMinute();
        String beforeStatus = beforeInfo.getAttendanceStatus();
        DateInfo modifiedInfo = register.modifyInfo(crew, modifyDate, modifyCampusTime);
        outputView.writeAttendanceModifyCheck(beforeHour, beforeMinute, beforeStatus, modifiedInfo);
    }

    private void functionOne(LocalDate now, Crews crews, Register register) {
        Crew crew = findCrew(crews);
        CampusTime attendanceCampusTime = findAttendanceTime();
        DayOfWeek dayOfWeek = DayOfWeek.from(now.getDayOfWeek().getValue());
        DateInfo dateInfo = DateInfo.of(now.getMonthValue(), now.getDayOfMonth(), dayOfWeek, attendanceCampusTime);
        register.modifyInfo(crew, Integer.parseInt(dateInfo.getDay()), attendanceCampusTime);
        outputView.writeAttendanceCheck(dateInfo);
    }

    private Crew findCrew(Crews crews) {
        return retryUntilValidInput(() -> crews.findCrew(inputView.readCrewName()));
    }

    private Crew findModifiyCrew(Crews crews) {
        return retryUntilValidInput(() -> crews.findCrew(inputView.readModifyCrewName()));
    }

    private CampusTime findAttendanceTime() {
        return retryUntilValidInput(() -> CampusTime.fromHourColonMinute(inputView.readAttendanceTime()));
    }

    private CampusTime findModifyTime() {
        return retryUntilValidInput(() -> CampusTime.fromHourColonMinute(inputView.readModifyTime()));
    }

    private int readModifyDay() {
        return retryUntilValidInput(() -> Integer.parseInt(inputView.readModifyDay()));
    }

    private AttendanceOperation readFunction(LocalDate now) {
        return retryUntilValidInput(() -> AttendanceOperation.of(inputView.readFunctionChoose(now)));
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
