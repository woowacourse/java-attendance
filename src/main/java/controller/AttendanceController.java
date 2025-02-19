package controller;

import domain.AttendanceStatus;
import domain.Crew;
import domain.CrewRepository;
import dto.AttendanceRequest;
import dto.AttendanceResult;
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
        }
    }
}
