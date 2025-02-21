package controller;

import constant.CampusConstant;
import domain.AttendanceStatus;
import domain.AttendanceStatusStatistics;
import domain.Crew;
import domain.CrewRepository;
import domain.Manage;
import dto.AttendanceModifyRequest;
import dto.AttendanceRecord;
import dto.AttendanceRequest;
import dto.AttendanceResult;
import dto.CrewAlmostExpelledResult;
import dto.ModifiedResult;
import dto.MonthAttendanceRecordsResult;
import dto.OptionRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import util.DateTimeUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    public AttendanceController() {
        ResourceLoader.loadCrewRepository();
    }

    public void run() {

        boolean isRunning = true;
        while (isRunning) {
            OptionRequest optionRequest = InputView.scanOption();
            switch (optionRequest.option()) {
                case "1" -> attendanceCheck();
                case "2" -> attendanceModify();
                case "3" -> printMonthAttendanceRecords();
                case "4" -> checkCrewsAlmostExpelled();
                case "q", "Q" -> isRunning = false;
                default -> System.out.println("존재하지 않는 옵션입니다.");
            }
        }
    }

    private void attendanceCheck() {
        AttendanceRequest request = InputView.scanAttendance();
        validateCampusTime(request.time());
        Crew crew = CrewRepository.findByNickname(request.nickname());
        AttendanceStatus status = crew.addAttendanceTime(DateTimeUtil.nowDate(), request.time());
        OutputView.printAttendanceResult(AttendanceResult.of(DateTimeUtil.nowDate(), request.time(), status));
    }

    private void attendanceModify() {
        AttendanceModifyRequest request = InputView.scanModify();
        validateCampusTime(request.time());
        Crew crew = CrewRepository.findByNickname(request.nickname());
        ModifiedResult.InnerStatus before = generateInnerStatus(crew, request);
        crew.modifyAttendanceTime(request.date(), request.time());
        ModifiedResult.InnerStatus after = generateInnerStatus(crew, request);
        OutputView.printModifiedResult(new ModifiedResult(request.date(), before, after));
    }

    private ModifiedResult.InnerStatus generateInnerStatus(Crew crew, AttendanceModifyRequest request) {
        return new ModifiedResult.InnerStatus(
                crew.getAttendanceTimeByDate(request.date()),
                crew.getAttendanceStatusByDate(request.date()));
    }

    private void printMonthAttendanceRecords() {
        Crew crew = CrewRepository.findByNickname(InputView.scanNickname());
        LocalDate now = DateTimeUtil.nowDate();
        List<AttendanceRecord> attendanceRecords = crew.getMonthAttendanceRecords(now);
        Manage manage = Manage.of(crew.getAttendanceStatusStatistics(now));

        OutputView.printMonthAttendanceRecords(
                new MonthAttendanceRecordsResult(
                        crew.getNickname(), attendanceRecords, crew.getAttendanceStatusStatistics(now), manage
                ));
    }

    private void checkCrewsAlmostExpelled() {
        List<Crew> crews = CrewRepository.findAll();
        List<CrewAlmostExpelledResult> result = crews.stream()
                .map(crew -> {
                    AttendanceStatusStatistics attendanceStatusStatistics
                            = crew.getAttendanceStatusStatistics(DateTimeUtil.nowDate());
                    return new CrewAlmostExpelledResult(
                            crew.getNickname(), attendanceStatusStatistics, Manage.of(attendanceStatusStatistics));
                })
                .toList();
        OutputView.printCrewsAlmostExpelled(result);
    }

    private void validateCampusTime(LocalTime time) {
        if (time.isBefore(CampusConstant.startTime) || time.isAfter(CampusConstant.endTime)) {
            throw new IllegalArgumentException("캠퍼스 운영시간이 아닙니다.");
        }
    }
}
