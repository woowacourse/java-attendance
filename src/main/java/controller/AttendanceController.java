package controller;

import domain.Crew;
import dto.AttendanceStatusDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            // 닉네임 입력 -> 체크 -> 등교시간 입력 -> 출석 기록
            String nickname = InputView.askNickname(false);
            validateNicknameRegistered(nickname);
            Crew crew = Crew.from(nickname);
            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), InputView.askAttendanceTime(false));
            if (attendanceService.checkHistoryAlreadyExists(crew, dateTime)) {
               OutputView.printErrorMessage("이미 출석 기록이 있습니다. 수정 기능을 이용하세요.");
               throw new IllegalArgumentException();
            }
            AttendanceStatusDto dto = attendanceService.addAttendanceOf(crew, dateTime);

            return;
        }

        if (featureType == FeatureType.CHECK_ATTENDANCE) {
            return;
        }

        if (featureType == FeatureType.CHECK_ATTENDANCE) {
            return;
        }

        if (featureType == FeatureType.CHECK_ATTENDANCE) {
            return;
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
