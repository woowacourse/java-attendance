package attendance.controller;

import attendance.domain.AttendanceHistories;
import attendance.domain.AttendanceHistory;
import attendance.domain.CampusTime;
import attendance.domain.DateInfo;
import attendance.domain.DateInfos;
import attendance.domain.Register;
import attendance.domain.constant.AttendanceOperation;
import attendance.domain.constant.AttendanceStatus;
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
        List<String> attendanceFiles = FileReader.fileReadLine("attendances.csv");
        Register register = Register.createRegisterByCrewAttendanceTimeFile(attendanceFiles);

        boolean flag = true;
        while (flag) {
            AttendanceOperation attendanceOperation = readFunction(LocalDate.now());
            flag = mappingFunction(attendanceOperation, LocalDate.now(), register);
        }
        inputView.closeScanner();
    }

    private boolean mappingFunction(AttendanceOperation attendanceOperation, LocalDate now, Register register) {
        if (attendanceOperation.equals(AttendanceOperation.ONE)) {
            functionOne(now, register);
            return true;
        }
        if (attendanceOperation.equals(AttendanceOperation.TWO)) {
            functionTwo(now, register);
            return true;
        }
        if (attendanceOperation.equals(AttendanceOperation.THREE)) {
            functionThree(now, register);
            return true;
        }
        if (attendanceOperation.equals(AttendanceOperation.FOUR)) {
            functionFour(register);
            return true;
        }
        return false;
    }

    private AttendanceOperation readFunction(LocalDate now) {
        return retryUntilValidInput(() -> AttendanceOperation.of(inputView.readFunctionChoose(now)));
    }

    private void functionOne(LocalDate now, Register register) {
        String crewName = inputView.readCrewName();
        String attendanceTime = inputView.readAttendanceTime();
        CampusTime attendanceCampusTime = CampusTime.fromHourColonMinute(attendanceTime);

        DateInfo dateInfo = DateInfo.fromCampusTime(now, attendanceCampusTime);
        register.addDateInfo(crewName, dateInfo);

        outputView.writeAttendanceCheck(dateInfo);
    }

    private void functionTwo(LocalDate now, Register register) {
        String crewName = inputView.readModifyCrewName();
        String modifyDay = inputView.readModifyDay();
        String modifyTime = inputView.readModifyTime();
        CampusTime campusTime = CampusTime.fromHourColonMinute(modifyTime);
        LocalDate date = LocalDate.of(now.getYear(), now.getMonth(), Integer.parseInt(modifyDay));

        boolean isInfoExist = register.hasDateInfo(crewName, modifyDay);
        if (!isInfoExist) {
            DateInfo createDateInfo = register.findOrCreateDateInfo(crewName, date, campusTime);
            outputView.writeAttendanceModifyCheck(createDateInfo);
            return;
        }
        DateInfo beforeDateInfo = register.findOrCreateDateInfo(crewName, date, campusTime);
        int beforeHour = beforeDateInfo.getCampusHour();
        int beforeMinute = beforeDateInfo.getCampusMinute();
        AttendanceStatus beforeStatus = beforeDateInfo.getAttendanceStatus();
        DateInfo afterDateInfo = register.modifyDateInfo(crewName, modifyDay, campusTime);
        outputView.writeAttendanceModifyCheck(beforeHour, beforeMinute, beforeStatus, afterDateInfo);
    }

    private void functionThree(LocalDate now, Register register) {
        String crewName = inputView.readCrewName();
        DateInfos dateInfos = register.findDateInfos(crewName);
        AttendanceHistory history = AttendanceHistory.fromDateInfos(crewName, now, dateInfos);

        outputView.writeAttendanceHistory(now, dateInfos, history);
    }

    private void functionFour(Register register) {
        AttendanceHistories histories = AttendanceHistories.fromRegister(register);
        List<AttendanceHistory> warningAttendanceHistory = histories.findWarningAttendanceHistory();

        outputView.writeWarningHistories(warningAttendanceHistory);
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
