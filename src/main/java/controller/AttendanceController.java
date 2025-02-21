package controller;

import controller.dto.AttendanceHistoryDto;
import controller.dto.AttendanceHistoryWithPenaltyTypeDto;
import controller.dto.AttendanceRequestDto;
import controller.dto.AttendanceTimeDto;
import controller.dto.AttendanceTypeCountDto;
import controller.dto.AttendanceUpdateResultDto;
import domain.date.AttendanceDateTime;
import io.CustomFileReader;
import java.io.FileNotFoundException;
import java.util.List;
import service.AttendanceService;
import view.FeatureOption;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void run() {
        try {
            readInitialData();
            processAttendanceSystem();
        } catch (Exception exception) {
            OutputView.printErrorMessage(exception.getMessage());
        }
    }

    private void processAttendanceSystem() {
        while (readFeatureOption() != FeatureOption.QUIT) {
            FeatureOption featureOption = InputView.readOption();
            runFunction(featureOption);
        }
    }

    private FeatureOption readFeatureOption() {
        return InputView.readOption();
    }

    private void readInitialData() throws FileNotFoundException {
        List<String> names = CustomFileReader.readCrewNames();
        attendanceService.saveCrews(names);

        List<AttendanceRequestDto> attendanceRequestDtos = CustomFileReader.readAttendanceInfo();
        attendanceService.initializeAttendanceHistories(attendanceRequestDtos);
    }

    public void runFunction(FeatureOption featureOption) {
        if (featureOption == FeatureOption.APPLY_ATTENDANCE) {
            applyAttendance();
            return;
        }
        if (featureOption == FeatureOption.EDIT_ATTENDANCE) {
            editAttendance();
            return;
        }
        if (featureOption == FeatureOption.CHECK_ATTENDANCE_OF_CREW) {
            checkAttendanceOfCrew();
            return;
        }
        if (featureOption == FeatureOption.CHECK_WARNING_CREW) {
            checkWarningCrew();
        }
    }

    private void applyAttendance() {
        String nickname = getValidNickname();
        AttendanceDateTime attendanceDateTime = getAttendanceDateTime();

        AttendanceHistoryDto attendanceHistoryDto = attendanceService.applyAttendance(nickname, attendanceDateTime);
        OutputView.printCheckedHistory(attendanceHistoryDto);
    }

    private void editAttendance() {
        String nickname = getValidNickname();
        AttendanceDateTime newDateTime = getAttendanceDateTime();

        AttendanceUpdateResultDto attendanceUpdateResultDto = attendanceService.editAttendance(nickname, newDateTime);
        OutputView.printUpdatedResult(attendanceUpdateResultDto);
    }

    private void checkAttendanceOfCrew() {
        String nickname = getValidNickname();
        int day = InputView.readToday();
        AttendanceHistoryWithPenaltyTypeDto attendanceHistoryWithPenaltyTypeDto = attendanceService.checkAttendanceOf(
                nickname, day);
        OutputView.printAttendanceHistories(attendanceHistoryWithPenaltyTypeDto);
    }

    private void checkWarningCrew() {
        int day = InputView.readToday();
        List<AttendanceTypeCountDto> attendanceTypeCountDtos = attendanceService.checkWarningCrews(day);
        OutputView.printBanWarningCrews(attendanceTypeCountDtos);
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
