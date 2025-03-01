package attendance.controller;

import attendance.CurrentDate;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceTime;
import attendance.domain.AttendanceType;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceHistory attendanceHistory;
    private final CurrentDate currentDate;
    private final Map<String, Runnable> functionMap = new HashMap<>();

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceHistory attendanceHistory,
        CurrentDate currentDate) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceHistory = attendanceHistory;
        this.currentDate = currentDate;
        initFunctionMap();
    }

    public void start() {
        while(true) {
            inputView.todayDateMessage(currentDate.now());
            String userChooseFunction = inputView.inputFunction();
            if (userChooseFunction.equals("Q")) {
                break;
            }
            startAttendanceProcess(userChooseFunction);
        }
    }

    private void startAttendanceProcess(String userChooseFunction) {
        Runnable function = functionMap.get(userChooseFunction);
        if (function == null) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        function.run();
    }

    private void initFunctionMap() {
        functionMap.put("1", this::attendConfirm);
        functionMap.put("2", this::modifyAttendance);
        functionMap.put("3", this::confirm);
        functionMap.put("4", this::warning);
    }

    private void attendConfirm() {
        String inputNickname = inputView.inputNickname();
        attendanceHistory.isValidCrew(inputNickname);
        LocalTime nowTime = inputView.inputAttendanceTime();
        AttendanceTime attendanceTime = AttendanceTime.from(LocalDateTime.of(currentDate.now(), nowTime));
        attendanceHistory.add(inputNickname, attendanceTime);
        outputView.printAttendanceInfo(
            attendanceTime, AttendanceType.decideAttendanceType(attendanceTime)
        );
    }

    private void modifyAttendance() {
        String inputNickname = inputView.inputModifyNickname();
        attendanceHistory.isValidCrew(inputNickname);
        int modifyDate = inputView.inputModifyAttendanceDate();
        AttendanceTime attendanceTime = attendanceHistory.getAttendanceTimeByDate(
            inputNickname, modifyDate);
        LocalTime modifyTime = inputView.inputAttendanceTime();
        AttendanceTime modifyAttendanceTime = attendanceHistory.modifyAttendance(inputNickname,
            attendanceTime, LocalDateTime.of(currentDate.now(), modifyTime));
        outputView.printModifyAttendaneTimeResult(attendanceTime, modifyAttendanceTime);
    }

    private void confirm(){};
    private void warning(){};

}
