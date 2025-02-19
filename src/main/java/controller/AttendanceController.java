package controller;

import controller.dto.AttendanceTimeDto;
import domain.AttendanceDate;
import domain.AttendanceDateTime;
import domain.AttendanceTime;
import domain.Crew;
import service.AttendanceService;
import view.Function;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void run() {
        try {
            Function function = InputView.readOption();
            runFunction(function);
        } catch (RuntimeException exception) {
            OutputView.printErrorMessage(exception.getMessage());
        }
    }

    public void runFunction(Function function) {
        if (function == Function.CHECK_ATTENDANCE) {
            checkAttendance();
            return;
        }
        if (function == Function.EDIT_ATTENDANCE) {
            editAttendance();
            return;
        }
        if (function == Function.CHECK_ATTENDANCE_OF_CREW) {

        }
        if (function == Function.CHECK_WARNING_CREW) {

        }
    }


    private void checkAttendance() {
        String nickname = getValidNickname();
        AttendanceDateTime attendanceDateTime = getAttendanceDateTime();

        attendanceService.checkAttendance(nickname, attendanceDateTime);
    }


    private void editAttendance() {
        String nickname = getValidNickname();
        AttendanceDateTime newDateTime = getAttendanceDateTime();

        attendanceService.editAttendance(nickname, newDateTime);
    }

    private static AttendanceDateTime getAttendanceDateTime() {
        int day = InputView.readToday();
        AttendanceTimeDto attendanceTimeDto = InputView.readAttendanceTime();

        return AttendanceDateTime.of(day, attendanceTimeDto.hour(),
                attendanceTimeDto.minute());
    }

    private String getValidNickname() {
        String nickname = InputView.readNickname();
        attendanceService.checkNicknameIsExisted(nickname);

        return nickname;
    }
}
