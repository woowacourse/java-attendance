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
import dto.AttendanceHistoryResponseDto;
import dto.AttendanceModifyRequestDto;
import dto.AttendanceRequestDto;
import dto.AttendanceResponseDto;
import dto.CrewAlmostExpelledResponseDto;
import dto.ModifiedResponseDto;
import dto.OptionRequestDto;
import util.DateTimeUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final CrewRepository crewRepository = CrewRepository.fromFile();

    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            OptionRequestDto optionRequestDto = InputView.scanOption();
            isRunning = processMenu(optionRequestDto.option());
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
        AttendanceRequestDto request = InputView.scanAttendance();
        validateCampusTime(request.time());
        Crew crew = crewRepository.get(request.nickname());
        AttendanceStatus status = crew.attendance(DateTimeUtil.nowDate(), request.time());
        OutputView.printAttendanceResult(AttendanceResponseDto.of(DateTimeUtil.nowDate(), request.time(), status));
    }

    private void attendanceModify() {
        AttendanceModifyRequestDto request = InputView.scanModify();
        validateCampusTime(request.time());
        Crew crew = crewRepository.get(request.nickname());
        validateAttendanceTime(crew, request.date());
        ModifiedResponseDto.InnerModifiedDetail before = generateInnerStatus(crew, request);
        crew.modifyAttendance(request.date(), request.time());
        ModifiedResponseDto.InnerModifiedDetail after = generateInnerStatus(crew, request);
        OutputView.printModifiedResult(new ModifiedResponseDto(request.date(), before, after));
    }

    private void validateAttendanceTime(Crew crew, LocalDate date) {
        if (crew.getAttendanceTimeByDate(date) == null) {
            throw new IllegalArgumentException("출석 기록이 없는 날짜입니다.");
        }
    }

    private ModifiedResponseDto.InnerModifiedDetail generateInnerStatus(Crew crew, AttendanceModifyRequestDto request) {
        return new ModifiedResponseDto.InnerModifiedDetail(
            crew.getAttendanceTimeByDate(request.date()),
            crew.getAttendanceStatusByDate(request.date()));
    }

    private void checkAttendanceHistory() {
        Crew crew = crewRepository.get(InputView.scanNickname());
        LocalDate now = DateTimeUtil.nowDate();
        List<HistoryDto> historyDto = crew.getAllHistory(now);
        Manage manage = Manage.of(crew.getAttendanceStatusCounter(now));
        OutputView.printHistory(
            new AttendanceHistoryResponseDto(
                crew.getNickname(),
                historyDto,
                crew.getAttendanceStatusCounter(now),
                manage));
    }

    private void checkCrewsAlmostExpelled() {
        List<Crew> crews = crewRepository.getAll();
        List<CrewAlmostExpelledResponseDto> result = crews.stream()
            .map(crew -> {
                Map<AttendanceStatus, Integer> statusCounter = crew.getAttendanceStatusCounter(DateTimeUtil.nowDate());
                return new CrewAlmostExpelledResponseDto(
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
