package attendance.controller;

import static attendance.domain.CrewStatus.INTERVIEW;

import attendance.CurrentDate;
import attendance.domain.AttendancePolicy;
import attendance.domain.AttendanceHistory;
import attendance.domain.dto.AttendanceHistoryDto;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import attendance.domain.CrewManager;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {
    private final InputView inputView;
    private final CrewManager crewManager;
    private final OutputView outputView;
    private final CurrentDate currentDate;

    public AttendanceController(InputView inputView, CrewManager crewManager, OutputView outputView, CurrentDate currentDate) {
        this.inputView = inputView;
        this.crewManager = crewManager;
        this.outputView = outputView;
        this.currentDate = currentDate;
    }

    public void start() {
        LocalDate now = currentDate.now();
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
            if (option.equals("4")) {
                checkDangerousCrews(now);
                continue;
            }
            if (option.equals("Q")) {
                continue;
            }
            throw new IllegalArgumentException("잘못된 입력 입니다.");
        }
    }

    private void checkDangerousCrews(LocalDate now) {
        List<Crew> dangerousCrews = crewManager.getDangerousCrews(now);
        outputView.printDangerousCrews(now, dangerousCrews);
    }

    private void checkAttendanceHistoriesByCrew(LocalDate now) {
        Crew crew = findCrew();
        Map<AttendanceType, Integer> attendanceResult = crew.calculateAttendanceResult(now);
        outputView.printAttendanceHistories(now, crew);
        outputView.printAttendanceResult(attendanceResult);
        if (crew.calculateCrewStatus(attendanceResult) == INTERVIEW) {
            outputView.printInterviewTarget();
        }
    }

    private void modifyAttendance(LocalDate now) {
        Crew crew = findCrew();
        LocalDate modifyDate = inputView.inputModifyDate(now);
        LocalTime modifyTime = inputView.inputAttendanceTime();
        AttendanceHistory attendanceHistory = crew.getAttendanceHistory(modifyDate);
        AttendanceHistoryDto beforeAttendanceHistoryDto = AttendanceHistoryDto.of(attendanceHistory);
        AttendanceHistory afterAttendanceHistory = crew.modifyAttendanceResult(attendanceHistory, modifyTime);
        outputView.printModifyAttendanceResult(beforeAttendanceHistoryDto, afterAttendanceHistory);
    }

    private void doAttendance(LocalDate now) {
        AttendancePolicy.checkHoliday(now);
        Crew crew = findCrew();
        LocalTime attendanceTime = inputView.inputAttendanceTime();
        AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(now, attendanceTime);
        LocalDateTime attedanceDateTime = LocalDateTime.of(now, attendanceTime);
        AttendanceHistory attendanceHistory = new AttendanceHistory(attedanceDateTime, attendanceType);
        crew.addAttendanceHistory(attendanceHistory);
        outputView.printAttendanceHistory(attendanceHistory);
    }

    private Crew findCrew() {
        String crewName = inputView.inputCrewName();
        return crewManager.findByCrewName(crewName);
    }
}
