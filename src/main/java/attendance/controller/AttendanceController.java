package attendance.controller;

import attendance.CurrentDate;
import attendance.domain.AttendanceHistories;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import attendance.domain.CrewAttendanceManager;
import attendance.domain.CrewStatus;
import attendance.domain.Crews;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {

    private final InputView inputView;
    private final Crews crews;
    private final OutputView outputView;
    private final CurrentDate currentDate;
    private final CrewAttendanceManager crewAttendanceManager;

    public AttendanceController(InputView inputView, Crews crews, OutputView outputView,
        CurrentDate currentDate, CrewAttendanceManager crewAttendanceManager) {
        this.inputView = inputView;
        this.crews = crews;
        this.outputView = outputView;
        this.currentDate = currentDate;
        this.crewAttendanceManager = crewAttendanceManager;
    }

    public void start() {
        LocalDate currentDate = this.currentDate.now();
        crewAttendanceManager.insertAbsenceIfNotExistsAttendance(currentDate);
        while (true) {
            String option = inputView.inputOption(currentDate);
            if (option.equals("1")) {
                doAttendance(currentDate);
            }
            if (option.equals("2")) {
                modifyAttendance(currentDate);
            }
            if (option.equals("3")) {
                checkAttendanceHistoriesByCrew();
            }
            if (option.equals("4")) {
                checkDangerousCrews();
            }
            if (option.equals("Q")) {
                break;
            }
        }
    }

    private void doAttendance(LocalDate currentDate) {
        String crewName = inputView.inputCrewName();
        Crew crew = crews.findByCrewName(crewName);
        LocalTime attendanceTime = inputView.inputAttendanceTime();
        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, attendanceTime);
        AttendanceHistory attendanceHistory = AttendanceHistory.from(currentDateTime);
        crewAttendanceManager.addCrewAttendanceInfo(crew, attendanceHistory);
        outputView.printAttendanceResult(attendanceHistory);
    }

    private void modifyAttendance(LocalDate currentDate) {
        String crewName = inputView.inputCrewName();
        Crew crew = crews.findByCrewName(crewName);
        LocalDate modifyDate = inputView.inputModifyDate(currentDate);
        LocalTime modifyTime = inputView.inputAttendanceTime();
        AttendanceHistories attendanceHistories = crewAttendanceManager.findAttendanceHistoriesByCrew(
            crew);
        AttendanceHistory attendanceHistory = attendanceHistories.getValidationAttendanceDate(
            modifyDate);
        AttendanceHistory modifyAttendanceHistory = attendanceHistories.modifyAttendanceResult(
            LocalDateTime.of(modifyDate, modifyTime));
        outputView.printModifyAttendanceResult(attendanceHistory, modifyAttendanceHistory);
    }

    private void checkAttendanceHistoriesByCrew() {
        String crewName = inputView.inputCrewName();
        Crew crew = crews.findByCrewName(crewName);
        AttendanceHistories attendanceHistories = crewAttendanceManager.findAttendanceHistoriesByCrew(
            crew);
        Map<AttendanceType, Integer> attendanceResult = attendanceHistories.calculateAttendanceResult();
        outputView.printAttendanceHistories(crew, attendanceHistories);
        outputView.printAttendanceTypeResult(attendanceResult);
        CrewStatus crewStatus = CrewStatus.calculateCrewStatus(attendanceResult);
        if (crewStatus == CrewStatus.INTERVIEW) {
            outputView.printInterviewTarget();
        }
    }

    private void checkDangerousCrews() {
        List<Crew> dangerousCrew = crews.findDangerousCrew(crewAttendanceManager);
        outputView.printDangerousMessage();
        for (Crew crew : dangerousCrew) {
            AttendanceHistories attendanceHistories = crewAttendanceManager.findAttendanceHistoriesByCrew(
                crew);
            Map<AttendanceType, Integer> attendanceResult = attendanceHistories.calculateAttendanceResult();
            CrewStatus crewStatus = CrewStatus.calculateCrewStatus(attendanceResult);
            outputView.printDangerousCrews(crew, attendanceResult, crewStatus);
        }
    }
}
