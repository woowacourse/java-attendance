package attendance;

import static attendance.domain.CrewStatus.INTERVIEW;
import static attendance.view.InputView.inputAttendanceTime;
import static attendance.view.InputView.inputCrewName;
import static attendance.view.InputView.inputModifyDate;
import static attendance.view.InputView.inputOption;
import static attendance.view.OutputView.printAttendanceHistories;
import static attendance.view.OutputView.printAttendanceHistory;
import static attendance.view.OutputView.printAttendanceStatistics;
import static attendance.view.OutputView.printDangerousCrews;
import static attendance.view.OutputView.printInterviewTarget;
import static attendance.view.OutputView.printModifyAttendanceHistory;

import attendance.domain.AttendanceHistory;
import attendance.domain.AttendancePolicy;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import attendance.domain.CrewManager;
import attendance.domain.dto.AttendanceHistoryDto;
import attendance.utils.AttendanceFileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) throws IOException {
        CrewManager crewManager = new CrewManager();
        LocalDate today = new SystemCurrentDate().now();
        initializeCrewsAndAttendances(crewManager);
        while (true) {
            String option = inputOption(today);
            if (option.equals("1")) {
                doAttendance(crewManager, today);
                continue;
            }
            if (option.equals("2")) {
                modifyAttendance(crewManager, today);
                continue;
            }
            if (option.equals("3")) {
                checkAttendanceHistoriesByCrew(crewManager, today);
                continue;
            }
            if (option.equals("4")) {
                checkDangerousCrews(crewManager, today);
                continue;
            }
            if (option.equals("Q")) {
                continue;
            }
            throw new IllegalArgumentException("잘못된 입력 입니다.");
        }
    }

    private static void initializeCrewsAndAttendances(CrewManager crewManager) throws IOException {
        BufferedReader file = AttendanceFileReader.read();
        AttendanceFileReader.initializeAttendances(file, crewManager);
    }

    private static void doAttendance(CrewManager crewManager, LocalDate today) {
        AttendancePolicy.checkNotWeekendAndHoliday(today);
        Crew crew = findCrew(crewManager);
        LocalTime attendanceTime = inputAttendanceTime();
        AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(today, attendanceTime);
        LocalDateTime attedanceDateTime = LocalDateTime.of(today, attendanceTime);
        AttendanceHistory attendanceHistory = new AttendanceHistory(attedanceDateTime, attendanceType);
        crew.addAttendanceHistory(attendanceHistory);
        printAttendanceHistory(attendanceHistory);
    }

    private static void modifyAttendance(CrewManager crewManager, LocalDate today) {
        Crew crew = findCrew(crewManager);
        LocalDate modifyDate = inputModifyDate(today);
        LocalTime modifyTime = inputAttendanceTime();
        AttendanceHistory attendanceHistory = crew.getAttendanceHistory(modifyDate);
        AttendanceHistoryDto beforeAttendanceHistoryDto = AttendanceHistoryDto.of(attendanceHistory);
        AttendanceHistory afterAttendanceHistory = crew.modifyAttendanceResult(attendanceHistory, modifyTime);
        printModifyAttendanceHistory(beforeAttendanceHistoryDto, afterAttendanceHistory);
    }

    private static void checkAttendanceHistoriesByCrew(CrewManager crewManager, LocalDate today) {
        Crew crew = findCrew(crewManager);
        Map<AttendanceType, Integer> attendanceResult = crew.calculateAttendanceResult(today);
        printAttendanceHistories(today, crew);
        printAttendanceStatistics(attendanceResult);
        if (crew.calculateCrewStatus(attendanceResult) == INTERVIEW) {
            printInterviewTarget();
        }
    }

    private static void checkDangerousCrews(CrewManager crewManager, LocalDate today) {
        List<Crew> dangerousCrews = crewManager.getDangerousCrews(today);
        printDangerousCrews(today, dangerousCrews);
    }

    private static Crew findCrew(CrewManager crewManager) {
        String crewName = inputCrewName();
        return crewManager.findByCrewName(crewName);
    }
}
