package controller;

import controller.dto.AttendanceTimeDto;
import domain.AttendanceDate;
import domain.AttendanceDateTime;
import domain.AttendanceTime;
import domain.Crew;
import service.AttendanceService;
import view.Function;
import view.InputView;

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void run() {
        Function function = InputView.readOption();
        runFunction(function);
    }

    public void runFunction(Function function) {
        if (function == Function.CHECK_ATTENDANCE) {
            checkAttendance();
            return;
        }
        if (function == Function.EDIT_ATTENDANCE) {

        }
        if (function == Function.CHECK_ATTENDANCE_OF_CREW) {

        }
        if (function == Function.CHECK_WARNING_CREW) {

        }
    }

    public void checkAttendance() {
        String nickname = InputView.readNickname();
        int day = InputView.readToday();
        AttendanceTimeDto attendanceTimeDto = InputView.readAttendanceTime();
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, attendanceTimeDto.hour(),
                attendanceTimeDto.minute());

        // 출석 확인 (새롭게 저장)
        attendanceService.checkAttendance(nickname, attendanceDateTime);
    }

}
