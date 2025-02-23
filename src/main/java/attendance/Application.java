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
import attendance.utils.AttendanceFileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    private static final Map<String, Runnable> optionMenu = new HashMap<>();
    private static final CrewManager crewManager = new CrewManager();
    private static final CurrentDate currentDate = new SystemCurrentDate();

    static {
        optionMenu.put("1", Application::doAttendance);
        optionMenu.put("2", Application::modifyAttendance);
        optionMenu.put("3", Application::checkAttendanceHistoriesByCrew);
        optionMenu.put("4", Application::checkDangerousCrews);
    }

    public static void main(String[] args) throws IOException {
        LocalDate today = currentDate.now();
        initializeCrewsAndAttendances();
        while (true) {
            String option = inputOption(today);
            if (option.equals("Q")) {
                break;
            }
            run(option);
        }
    }

    private static void run(String option) {
        Runnable runnable = optionMenu.getOrDefault(option, null);
        if (runnable == null) {
            throw new IllegalArgumentException("잘못된 입력 입니다.");
        }
        runnable.run();
    }

    private static void initializeCrewsAndAttendances() throws IOException {
        BufferedReader file = AttendanceFileReader.read();
        AttendanceFileReader.initializeAttendances(file, crewManager);
    }

    private static void doAttendance() {
        LocalDate today = currentDate.now();
        AttendancePolicy.checkNotWeekendAndHoliday(today);
        Crew crew = findCrew(crewManager);
        LocalTime attendanceTime = inputAttendanceTime();
        AttendanceHistory attendanceHistory = crew.doAttendance(today, attendanceTime);
        printAttendanceHistory(attendanceHistory);
    }

    private static void modifyAttendance() {
        LocalDate today = currentDate.now();
        Crew crew = findCrew(crewManager);
        LocalDate modifyDate = inputModifyDate(today);
        LocalTime modifyTime = inputAttendanceTime();
        AttendanceHistory beforeAttendanceHistory = crew.getAttendanceHistoryByDate(modifyDate);
        AttendanceHistory afterAttendanceHistory = crew.modifyAttendance(modifyDate, modifyTime);
        printModifyAttendanceHistory(beforeAttendanceHistory, afterAttendanceHistory);
    }

    private static void checkAttendanceHistoriesByCrew() {
        LocalDate today = currentDate.now();
        Crew crew = findCrew(crewManager);
        Map<AttendanceType, Integer> attendanceResult = crew.calculateAttendanceResult(today);
        printAttendanceHistories(today, crew);
        printAttendanceStatistics(attendanceResult);
        if (crew.calculateCrewStatus(attendanceResult) == INTERVIEW) {
            printInterviewTarget();
        }
    }

    private static void checkDangerousCrews() {
        LocalDate today = currentDate.now();
        List<Crew> dangerousCrews = crewManager.getDangerousCrews(today);
        printDangerousCrews(today, dangerousCrews);
    }

    private static Crew findCrew(CrewManager crewManager) {
        String crewName = inputCrewName();
        return crewManager.findByCrewName(crewName);
    }
}
