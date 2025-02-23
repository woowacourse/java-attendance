package attendance.controller;

import attendance.CurrentDate;
import attendance.domain.AttendanceHistories;
import attendance.domain.AttendanceHistory;
import attendance.domain.Crew;
import attendance.domain.CrewAttendanceManager;
import attendance.domain.Crews;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceController {

    private final InputView inputView;
    private final Crews crews;
    private final OutputView outputView;
    private final CurrentDate currentDate;
    private final CrewAttendanceManager crewAttendanceManager;

    public AttendanceController(InputView inputView, Crews crews, OutputView outputView,
        CurrentDate currentDate,
        CrewAttendanceManager crewAttendanceManager) {
        this.inputView = inputView;
        this.crews = crews;
        this.outputView = outputView;
        this.currentDate = currentDate;
        this.crewAttendanceManager = crewAttendanceManager;
    }

    public void start() {
        LocalDate now = currentDate.now();
        crewAttendanceManager.insertAbsenceIfNotExistsAttendance(now);
        while (true) {
            String option = inputView.inputOption(now);
            if (option.equals("1")) {
                doAttendance(now);
                continue;
            }
            if (option.equals("2")) {
                modifyAttendance(now);
                continue;
            }

            if (option.equals("3")) {
                checkAttendanceHistoriesByCrew(now);
                continue;
            }
            /*
            if (option.equals("4")) {
                checkDangerousCrews(now);
                continue;
            }
            if (option.equals("Q")) {
                continue;
            }
             */
            throw new IllegalArgumentException("잘못된 입력 입니다.");
        }
    }

    /*
    private void checkDangerousCrews(LocalDate now) {
        List<Crew> dangerousCrews = crewManager.getDangerousCrews(now);
        outputView.printDangerousCrews(now, dangerousCrews);
    }
     */

    private void checkAttendanceHistoriesByCrew(LocalDate now) {
        String crewName = inputView.inputCrewName();
        Crew crew = crews.findByCrewName(crewName);
        AttendanceHistories attendanceHistories = crewAttendanceManager.findAttendanceHistoriesByCrew(
            crew);
        outputView.printAttendanceHistories(crew, attendanceHistories);
    }

    private void modifyAttendance(LocalDate now) {
        String crewName = inputView.inputCrewName();
        Crew crew = crews.findByCrewName(crewName);
        LocalDate modifyDate = inputView.inputModifyDate(now);
        LocalTime modifyTime = inputView.inputAttendanceTime();

        AttendanceHistories attendanceHistories = crewAttendanceManager.findAttendanceHistoriesByCrew(
            crew);
        AttendanceHistory attendanceHistory = attendanceHistories.getValidationAttendanceDate(
            modifyDate);
        AttendanceHistory modifyAttendanceHistory = attendanceHistories.modifyAttendanceResult(
            LocalDateTime.of(modifyDate, modifyTime));

        outputView.printModifyAttendanceResult(attendanceHistory, modifyAttendanceHistory);
    }

    private void doAttendance(LocalDate now) {
        String crewName = inputView.inputCrewName();
        Crew crew = crews.findByCrewName(crewName);
        LocalTime attendanceTime = inputView.inputAttendanceTime();
        LocalDateTime currentDateTime = LocalDateTime.of(now, attendanceTime);
        AttendanceHistory attendanceHistory = AttendanceHistory.from(currentDateTime);
        crewAttendanceManager.addCrewAttendanceInfo(crew, attendanceHistory);
        outputView.printAttendanceResult(attendanceHistory);
    }
}
