package controller;

import domain.AttendanceDateTime;
import domain.AttendanceType;
import domain.Crew;
import dto.AttendanceStatusDto;
import dto.AttendanceStatusesOfCrewDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
        FeatureType selectedFeature;
        while (shouldContinue(selectedFeature = getFeature())) {
            runFeature(selectedFeature);
        }
    }

    private boolean shouldContinue(FeatureType featureType) {
        return featureType != FeatureType.QUIT;
    }

    private static FeatureType getFeature() {
        OutputView.printToday();
        return InputView.askFeature();
    }

    private void runFeature(FeatureType featureType) {
        if (featureType == FeatureType.CHECK_ATTENDANCE) {
            runCheckAttendance();
            return;
        }

        if (featureType == FeatureType.EDIT_ATTENDANCE) {
            runEditAttendance();
            return;
        }

        if (featureType == FeatureType.CHECK_ATTENDANCE_OF_CREW) {
            runCheckAttendanceOfCrew();
            return;
        }

        if (featureType == FeatureType.CHECK_CREW_OF_BAN_RISK) {
            runCheckCrewOfBanRisk();
            return;
        }

        if (featureType == FeatureType.QUIT) {
            return;
        }
    }

    private void runCheckAttendance() {
        validateIsSchoolDay();
        String nickname = InputView.askNickname(false);
        validateNicknameRegistered(nickname);

        LocalTime localTime = InputView.askAttendanceTime(false);
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(ZoneId.of("Asia/Seoul")), localTime);

        Crew crew = Crew.from(nickname);
        validateHistoryNotAlreadyExists(crew, dateTime);

        AttendanceStatusDto dto = attendanceService.addAttendanceHistoryOf(crew, dateTime);
        OutputView.printAttendanceStatus(dto);
    }

    private void runEditAttendance() {
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
    }

    private void runCheckAttendanceOfCrew() {
        int currentDay = LocalDate.now(ZoneId.of("Asia/Seoul")).getDayOfMonth();
        String nickname = InputView.askNickname(false);
        validateNicknameRegistered(nickname);
        AttendanceStatusesOfCrewDto statusesDto = attendanceService.getHistoriesDtoFrom(Crew.from(nickname), currentDay);
        OutputView.printAttendanceStatus(statusesDto, nickname);
    }

    private void runCheckCrewOfBanRisk() {
        int currentDay = LocalDate.now(ZoneId.of("Asia/Seoul")).getDayOfMonth();
        Map<Crew, Map<AttendanceType, Integer>> attendanceTypeCountOfCrew = attendanceService.getAllAttendanceTypeCountOfCrew(currentDay);
        OutputView.printCrewOfBanRisk(attendanceTypeCountOfCrew);
    }

    private void validateIsSchoolDay() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        if (AttendanceDateTime.from(now).isRestDay()) {
            OutputView.printErrorMessage(String.format("%d월 %d일 %s은 등교일이 아닙니다.",
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));

            throw new IllegalArgumentException("");
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
}
