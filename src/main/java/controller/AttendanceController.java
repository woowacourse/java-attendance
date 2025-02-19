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
import view.FeatureType;
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
            configProgram();
            startProgram();
        } catch (Exception exception) {
            OutputView.printErrorMessage(exception.getMessage());
        }
    }

    public void runFeature(FeatureType featureType) {
        if (featureType == FeatureType.APPLY_ATTENDANCE) {
            applyAttendance();
            return;
        }
        if (featureType == FeatureType.EDIT_ATTENDANCE) {
            editAttendance();
            return;
        }
        if (featureType == FeatureType.CHECK_ATTENDANCE_OF_CREW) {
            checkAttendanceOfCrew();
            return;
        }
        if (featureType == FeatureType.CHECK_WARNING_CREW) {
            checkWarningCrew();
            return;
        }
    }

    private void configProgram() throws FileNotFoundException {
        readConfigFile();
        today = InputView.readToday();
    }

    private void startProgram() {
        while (true) {
            printTodayMessage();
            FeatureType featureType = InputView.readFeatureType();
            if (featureType == FeatureType.QUIT) {
                break;
            }

            runFeature(featureType);
        }
    }

    private void printTodayMessage() {
        DayOfWeek dayOfWeek = DayOfWeek.of(AttendanceDate.getDayOfWeek(today));
        String parsedDayOfWeek = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);

        OutputView.printTodayMessage(SERVICE_ABLE_MONTH, today, parsedDayOfWeek);
    }

    private void readConfigFile() throws FileNotFoundException {
        List<String> names = CustomFileReader.readCrewNames();
        attendanceService.saveCrews(names);

        List<AttendanceRequestDto> attendanceRequestDtos = CustomFileReader.readAttendanceInfo();
        attendanceService.initializeAttendanceHistories(attendanceRequestDtos);
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
        if (AttendanceDate.isRestDay(day)) {
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

        return AttendanceDateTime.of(day, attendanceTimeDto.hour(), attendanceTimeDto.minute());
    }

    private AttendanceDateTime getAttendanceDateTimeWillEditHistory() {
        int day = InputView.readDay();
        AttendanceTimeDto attendanceTimeDto = InputView.readAttendanceTimeWillEditHistory();

        return AttendanceDateTime.of(day, attendanceTimeDto.hour(), attendanceTimeDto.minute());
    }
}
