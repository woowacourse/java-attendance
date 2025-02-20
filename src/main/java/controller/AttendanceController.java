package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import constant.CampusConstant;
import domain.AttendanceStatus;
import domain.Crew;
import domain.CrewRepository;
import domain.History;
import domain.Manage;
import dto.AttendanceHistoryResult;
import dto.AttendanceModifyRequest;
import dto.AttendanceRequest;
import dto.AttendanceResult;
import dto.CrewCloseToExpelledResult;
import dto.ModifiedResult;
import dto.OptionRequest;
import util.DateTimeUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private CrewRepository crewRepository = new CrewRepository();

    public void run() {
        boolean isRunning = true;
        while(isRunning) {
            OptionRequest optionRequest = InputView.scanOption();
            switch (optionRequest.option()) {
                case "1" -> {
                    validateCampusTime();
                    AttendanceRequest request = InputView.scanAttendance();
                    Crew crew = crewRepository.get(request.nickname());
                    AttendanceStatus status = crew.attendance(DateTimeUtil.nowDate(), request.time());
                    OutputView.printAttendanceResult(
                        AttendanceResult.of(
                            DateTimeUtil.nowDate(),
                            request.time(),
                            status));
                }
                case "2" -> {
                    validateCampusTime();
                    AttendanceModifyRequest request = InputView.scanModify();
                    Crew crew = crewRepository.get(request.nickname());
                    LocalTime before = crew.getAttendanceTimeByDate(request.date());
                    AttendanceStatus beforeStatus = crew.getAttendanceStatusByDate(request.date());
                    crew.modifyAttendance(request.date(), request.time());
                    LocalTime after = crew.getAttendanceTimeByDate(request.date());
                    AttendanceStatus afterStatus = crew.getAttendanceStatusByDate(request.date());
                    OutputView.printModifiedResult(new ModifiedResult(
                        request.date(),
                        before,
                        beforeStatus,
                        after,
                        afterStatus
                    ));
                }
                case "3" -> {
                    Crew crew = crewRepository.get(InputView.scanNickname());
                    LocalDate now = DateTimeUtil.nowDate();
                    List<History> history = crew.getAllHistory(now);
                    Manage manage = Manage.of(crew.getAttendanceStatusStatistics(now));

                    OutputView.printHistory(
                        AttendanceHistoryResult.of(
                            crew.getNickname(),
                            history,
                            crew.getAttendanceStatusStatistics(now),
                            manage
                        ));
                }
                case "4" -> {
                    List<Crew> crews = crewRepository.getAll();
                    List<CrewCloseToExpelledResult> result = new ArrayList<>();
                    for (Crew crew : crews) {
                        Map<AttendanceStatus, Integer> attendanceStatusStatistics = crew.getAttendanceStatusStatistics(
                            DateTimeUtil.nowDate());
                        result.add(new CrewCloseToExpelledResult(
                            crew.getNickname(),
                            attendanceStatusStatistics,
                            Manage.of(attendanceStatusStatistics)
                        ));
                    }
                    OutputView.printCrewsCloseToExpelled(result);
                }
                case "q", "Q" -> {
                    isRunning = false;
                }
            }
        }
    }

    private void validateCampusTime() {
        LocalTime now = DateTimeUtil.nowTime();
        if(now.isBefore(CampusConstant.startTime) || now.isAfter(CampusConstant.endTime)) {
            throw new IllegalArgumentException("지금은 캠퍼스 운영시간이 아닙니다.");
        }
    }
}
