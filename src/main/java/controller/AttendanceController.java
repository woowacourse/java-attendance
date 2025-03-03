package controller;

import domain.AttendanceType;
import domain.Crew;
import dto.AttendanceStatusDto;
import dto.AttendanceStatusesOfCrewDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import service.AttendanceService;
import view.FeatureType;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final AttendanceService attendanceService;
    private LocalDate currentDate;

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

    private FeatureType getFeature() {
        int currentDay = LocalDate.now(ZoneId.of("Asia/Seoul")).getDayOfMonth();
        currentDate = LocalDate.of(2024, 12, currentDay);
        OutputView.printToday(currentDate);
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
        attendanceService.validateIsSchoolDay(currentDate);
        String nickname = InputView.askNickname(false);
        attendanceService.validateNicknameRegistered(nickname);

        LocalTime localTime = InputView.askAttendanceTime(false);
        LocalDateTime dateTime = LocalDateTime.of(currentDate, localTime);

        Crew crew = Crew.from(nickname);
        attendanceService.validateHistoryNotDuplicated(crew, dateTime);

        AttendanceStatusDto dto = attendanceService.addAttendanceHistory(crew, dateTime);
        OutputView.printAttendanceStatus(dto);
    }

    private void runEditAttendance() {
        String nickname = InputView.askNickname(true);
        attendanceService.validateNicknameRegistered(nickname);
        Crew crew = Crew.from(nickname);

        LocalDate localDate = InputView.askDayForEdit();
        LocalTime localTime = InputView.askAttendanceTime(true);
        LocalDateTime newDateTime = LocalDateTime.of(localDate, localTime);

        attendanceService.validateHistoryNotDuplicated(crew, newDateTime);

        List<AttendanceStatusDto> statusDtos = attendanceService.replaceAttendanceHistory(crew, newDateTime);
        OutputView.printEditAttendanceStatus(statusDtos);
    }

    private void runCheckAttendanceOfCrew() {
        int untilDay = currentDate.getDayOfMonth(); // 오늘 날짜 이전까지 검색
        String nickname = InputView.askNickname(false);
        attendanceService.validateNicknameRegistered(nickname);

        AttendanceStatusesOfCrewDto statusesDto = attendanceService.getAllHistories(Crew.from(nickname), untilDay);
        OutputView.printAttendanceStatus(statusesDto, nickname);
    }

    private void runCheckCrewOfBanRisk() {
        int untilDay = currentDate.getDayOfMonth();
        Map<Crew, Map<AttendanceType, Integer>> attendanceTypeCountOfCrew = attendanceService.getAllAttendanceTypeCountOfCrew(untilDay);
        OutputView.printCrewOfBanRisk(attendanceTypeCountOfCrew);
    }
}
