package controller;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceTimeDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.AttendanceDateTime;
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
            while (true) {
                Function function = InputView.readOption();
                if (function == Function.QUIT) {
                    break;
                }
                runFunction(function);
            }
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

        AttendanceHistoryDto attendanceHistoryDto = attendanceService.checkAttendance(nickname, attendanceDateTime);
        OutputView.printCheckedHistory(attendanceHistoryDto);
    }


    private void editAttendance() {
        String nickname = getValidNickname();
        AttendanceDateTime newDateTime = getAttendanceDateTime();

        AttendanceUpdateResultDto attendanceUpdateResultDto = attendanceService.editAttendance(nickname, newDateTime);
        OutputView.printUpdatedResult(attendanceUpdateResultDto);
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
