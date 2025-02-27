package controller;

import domain.Crew;
import dto.AttendanceStatusDto;
import dto.AttendanceStatusesOfCrewDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
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

            AttendanceStatusDto dto = attendanceService.addAttendanceHistoryOf(crew, dateTime);
            OutputView.printAttendanceStatus(dto);
            return;
        }

        if (featureType == FeatureType.EDIT_ATTENDANCE) {
            String nickname = InputView.askNickname(true);
            validateNicknameRegistered(nickname);
            Crew crew = Crew.from(nickname);

            LocalDate localDate = InputView.askDayForEdit();
            LocalTime localTime = InputView.askAttendanceTime(true);
            LocalDateTime newDateTime = LocalDateTime.of(localDate, localTime);
            if (!attendanceService.checkHistoryAlreadyExists(crew, newDateTime)) {
                OutputView.printErrorMessage("해당 날짜의 출석 기록이 존재하지 않습니다. 출석 확인 기능을 이용해주세요.");
                return;
            }

            List<AttendanceStatusDto> statusDtos = attendanceService.replaceAttendanceHistoryOf(crew, newDateTime);
            OutputView.printEditAttendanceStatus(statusDtos);
            return;
        }

        if (featureType == FeatureType.CHECK_ATTENDANCE_OF_CREW) {
            // 닉네임 입력 -> 등록 체크 -> 모든 출석 기록 출력 -> 출석/지각/결석 횟수 출력 -> 대상 패널티 출력
            int currentDay = LocalDate.now(ZoneId.of("Asia/Seoul")).getDayOfMonth();
            String nickname = InputView.askNickname(false);
            validateNicknameRegistered(nickname);
            AttendanceStatusesOfCrewDto statusesDto = attendanceService.getHistoriesDtoFrom(Crew.from(nickname), currentDay);
            OutputView.printAttendanceStatus(statusesDto);
            return;
        }

        if (featureType == FeatureType.CHECK_CREW_OF_BAN_RISK) {
            // 제적 위험자를 모두 가져올 방법 생각 ...
            return;
        }
    }

    private void validateHistoryNotAlreadyExists(Crew crew, LocalDateTime dateTime) {
        if (attendanceService.checkHistoryAlreadyExists(crew, dateTime)) {
           OutputView.printErrorMessage("해당 날짜에 출석 기록이 이미 존재합니다. 수정 기능을 이용해주세요.");
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
