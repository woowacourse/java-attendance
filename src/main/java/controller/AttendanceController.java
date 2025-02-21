package controller;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceHistoryWithPenaltyTypeDto;
import controller.dto.AttendanceRequestDto;
import controller.dto.AttendanceTimeDto;
import controller.dto.AttendanceTypeCountDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.date.AttendanceDate;
import domain.date.AttendanceDateTime;
import io.CustomFileReader;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import service.AttendanceService;
import view.Function;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final static int SERVICE_ABLE_MONTH = 12;
    private final AttendanceService attendanceService;
    private int today;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void run() {
        try {
            readConfigFile();
            today = InputView.readToday();
            while (true) {
                OutputView.printTodayMessage(SERVICE_ABLE_MONTH, today, DayOfWeek.of(new AttendanceDate(today).getDayOfWeek()).getDisplayName(TextStyle.FULL, Locale.KOREAN));
                Function function = InputView.readOption();
                if (function == Function.QUIT) {
                    break;
                }
                runFunction(function);
            }
        } catch (Exception exception) {
            OutputView.printErrorMessage(exception.getMessage());
        }
    }

    private void readConfigFile() throws FileNotFoundException {
        List<String> names = CustomFileReader.readCrewNames();
        attendanceService.saveCrews(names);

        List<AttendanceRequestDto> attendanceRequestDtos = CustomFileReader.readAttendanceInfo();
        attendanceService.initializeAttendanceHistories(attendanceRequestDtos);
    }

    public void runFunction(Function function) {
        if (function == Function.APPLY_ATTENDANCE) {
            applyAttendance();
            return;
        }
        if (function == Function.EDIT_ATTENDANCE) {
            editAttendance();
            return;
        }
        if (function == Function.CHECK_ATTENDANCE_OF_CREW) {
            checkAttendanceOfCrew();
            return;
        }
        if (function == Function.CHECK_WARNING_CREW) {
            checkWarningCrew();
        }
    }

    private void applyAttendance() {
        if (validateDayForApply(today)) {
            return;
        }

        String nickname = InputView.readNickname();
        attendanceService.checkNicknameIsExisted(nickname);
        attendanceService.checkAlreadyPresented(nickname, today);

        AttendanceDateTime attendanceDateTime = getAttendanceDateTime(today);

        AttendanceHistoryDto attendanceHistoryDto = attendanceService.applyAttendance(nickname, attendanceDateTime);
        OutputView.printCheckedHistory(attendanceHistoryDto);
    }


    private void editAttendance() {
        String nickname = InputView.readNicknameWillEditHistory();
        attendanceService.checkNicknameIsExisted(nickname);
        AttendanceDateTime newDateTime = getAttendanceDateTimeWillEditHistory();

        if (validateDayForApply(newDateTime.getDay())) {
            return;
        }

        AttendanceUpdateResultDto attendanceUpdateResultDto = attendanceService.editAttendance(nickname, newDateTime);
        OutputView.printUpdatedResult(attendanceUpdateResultDto);
    }

    private boolean validateDayForApply(int day) {
        int rawDayOfWeek = AttendanceDate.getDayOfWeek(day);
        if (DayOfWeek.of(rawDayOfWeek) == DayOfWeek.SATURDAY || DayOfWeek.of(rawDayOfWeek) == DayOfWeek.SUNDAY) {
            OutputView.printAttendanceDayErrorMessage(12, today, DayOfWeek.of(rawDayOfWeek));
            return true;
        }

        return false;
    }

    private void checkAttendanceOfCrew() {
        String nickname = InputView.readNickname();
        attendanceService.checkNicknameIsExisted(nickname);

        AttendanceHistoryWithPenaltyTypeDto attendanceHistoryWithPenaltyTypeDto = attendanceService.checkAttendanceOf(
                nickname, today);
        OutputView.printAttendanceHistories(nickname, attendanceHistoryWithPenaltyTypeDto);
    }

    private void checkWarningCrew() {
        List<AttendanceTypeCountDto> attendanceTypeCountDtos = attendanceService.checkWarningCrews(today);
        OutputView.printBanWarningCrews(attendanceTypeCountDtos);
    }

    private AttendanceDateTime getAttendanceDateTime(int day) {
        AttendanceTimeDto attendanceTimeDto = InputView.readAttendanceTime();

        return AttendanceDateTime.of(day, attendanceTimeDto.hour(),
                attendanceTimeDto.minute());
    }

    private AttendanceDateTime getAttendanceDateTimeWillEditHistory() {
        int day = InputView.readDay();
        AttendanceTimeDto attendanceTimeDto = InputView.readAttendanceTimeWillEditHistory();

        return AttendanceDateTime.of(day, attendanceTimeDto.hour(),
                attendanceTimeDto.minute());
    }
}
