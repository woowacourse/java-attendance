package controller;

import domain.Crew;
import dto.AttendanceStatusDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import service.AttendanceService;
import view.FeatureType;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void run() {
        FeatureType featureType = getFeature();
        runFeature(featureType);
    }

    private void runFeature(FeatureType featureType) {
        if (featureType == FeatureType.CHECK_ATTENDANCE) {
            String nickname = InputView.askNickname(false);
            validateNicknameRegistered(nickname);

            // LocalDateTime을 아예 View에서 파싱해서 넘겨주는 게 더 좋은 설계일까? 고민
            LocalTime localTime = InputView.askAttendanceTime(false);
            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(ZoneId.of("Asia/Seoul")), localTime);

            Crew crew = Crew.from(nickname);
            validateHistoryNotAlreadyExists(crew, dateTime);

            AttendanceStatusDto dto = attendanceService.addAttendanceOf(crew, dateTime);
            OutputView.printAttendanceStatus(dto);
            return;
        }

        if (featureType == FeatureType.EDIT_ATTENDANCE) {
            return;
        }

        if (featureType == FeatureType.CHECK_ATTENDANCE_OF_CREW) {
            return;
        }

        if (featureType == FeatureType.CHECK_CREW_OF_BAN_RISK) {
            return;
        }
    }

    private void validateHistoryNotAlreadyExists(Crew crew, LocalDateTime dateTime) {
        if (attendanceService.checkHistoryAlreadyExists(crew, dateTime)) {
           OutputView.printErrorMessage("이미 출석 기록이 있습니다. 수정 기능을 이용하세요.");
           throw new IllegalArgumentException();
        }
    }

    private void validateNicknameRegistered(String nickname) {
        if (!attendanceService.checkNicknameRegistered(nickname)) {
            OutputView.printErrorMessage("등록되지 않은 닉네임입니다.");
            throw new IllegalArgumentException();
        }
    }

    private static FeatureType getFeature() {
        OutputView.printToday();
        return InputView.askFeature();
    }
}
