package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import constant.CampusConstant;
import domain.AttendanceStatus;
import domain.Crew;
import domain.CrewRepository;
import dto.HistoryDto;
import domain.Manage;
import dto.AttendanceHistoryResult;
import dto.AttendanceModifyRequest;
import dto.AttendanceRequest;
import dto.AttendanceResult;
import dto.CrewAlmostExpelledResult;
import dto.ModifiedResult;
import dto.OptionRequest;
import util.DateTimeUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final CrewRepository crewRepository = CrewRepository.fromFile();

    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            OptionRequest optionRequest = InputView.scanOption();
            isRunning = processMenu(optionRequest.option());
        }
    }

    private boolean processMenu(String option) {
        switch (option) {
            case "1" -> attendanceCheck();
            case "2" -> attendanceModify();
            case "3" -> checkAttendanceHistory();
            case "4" -> checkCrewsAlmostExpelled();
            case "q", "Q" -> {
                return false;
            }
            default -> throw new IllegalArgumentException("존재하지 않는 기능입니다.");
        }
        return true;
    }

    private void attendanceCheck() {
        AttendanceRequest request = InputView.scanAttendance();
        validateCampusTime(request.time());
        Crew crew = crewRepository.get(request.nickname());
        AttendanceStatus status = crew.attendance(DateTimeUtil.nowDate(), request.time());
        OutputView.printAttendanceResult(AttendanceResult.of(DateTimeUtil.nowDate(), request.time(), status));
    }

    private void attendanceModify() {
        AttendanceModifyRequest request = InputView.scanModify();
        validateCampusTime(request.time());
        Crew crew = crewRepository.get(request.nickname());
        validateAttendanceTime(crew, request.date());
        ModifiedResult.InnerStatus before = generateInnerStatus(crew, request);
        crew.modifyAttendance(request.date(), request.time());
        ModifiedResult.InnerStatus after = generateInnerStatus(crew, request);
        OutputView.printModifiedResult(new ModifiedResult(request.date(), before, after));
    }

    private void validateAttendanceTime(Crew crew, LocalDate date) {
        if (crew.getAttendanceTimeByDate(date) == null) {
            throw new IllegalArgumentException("출석 기록이 없는 날짜입니다.");
        }
    }

    private ModifiedResult.InnerStatus generateInnerStatus(Crew crew, AttendanceModifyRequest request) {
        return new ModifiedResult.InnerStatus(
            crew.getAttendanceTimeByDate(request.date()),
            crew.getAttendanceStatusByDate(request.date()));
    }

    private void checkAttendanceHistory() {
        Crew crew = crewRepository.get(InputView.scanNickname());
        LocalDate now = DateTimeUtil.nowDate();
        List<HistoryDto> historyDto = crew.getAllHistory(now);
        Manage manage = Manage.of(crew.getAttendanceStatusCounter(now));
        OutputView.printHistory(
            new AttendanceHistoryResult(
                crew.getNickname(),
                historyDto,
                crew.getAttendanceStatusCounter(now),
                manage));
    }

    private void checkCrewsAlmostExpelled() {
        List<Crew> crews = crewRepository.getAll();
        List<CrewAlmostExpelledResult> result = crews.stream()
            .map(crew -> {
                Map<AttendanceStatus, Integer> statusCounter = crew.getAttendanceStatusCounter(DateTimeUtil.nowDate());
                return new CrewAlmostExpelledResult(
                    crew.getNickname(),
                    statusCounter,
                    Manage.of(statusCounter));
            }).toList();
        OutputView.printCrewsAlmostExpelled(result);
    }

    private void validateCampusTime(LocalTime time) {
        if (time.isBefore(CampusConstant.startTime) || time.isAfter(CampusConstant.endTime)) {
            throw new IllegalArgumentException("캠퍼스 운영시간이 아닙니다.");
        }
    }
}
