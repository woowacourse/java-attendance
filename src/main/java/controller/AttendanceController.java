package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import util.DayUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private CrewRepository crewRepository = new CrewRepository();

    public void run() {
        String option = InputView.scanOption();
        switch (option) {
            case "1" -> {
                AttendanceRequest request = InputView.scanAttendance();
                Crew crew = crewRepository.get(request.nickname());
                AttendanceStatus status = crew.attendance(DayUtil.now(), request.time());
                OutputView.printAttendanceResult(
                    AttendanceResult.of(
                        DayUtil.now(),
                        request.time(),
                        status));
            }
            case "2" -> {
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
                LocalDate now = DayUtil.now();
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
                        DayUtil.now());
                    result.add(new CrewCloseToExpelledResult(
                        crew.getNickname(),
                        attendanceStatusStatistics,
                        Manage.of(attendanceStatusStatistics)
                    ));
                }
                OutputView.printCrewsCloseToExpelled(result);
            }
        }
    }
}
