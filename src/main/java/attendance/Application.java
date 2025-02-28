package attendance;

import static attendance.view.InputView.readAttendanceDateToModify;
import static attendance.view.InputView.readAttendanceModificationTime;
import static attendance.view.InputView.readAttendanceTime;
import static attendance.view.InputView.readCrewNickname;
import static attendance.view.InputView.readCrewNicknameToModify;
import static attendance.view.InputView.readOption;

import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatistics;
import attendance.domain.CampusManager;
import attendance.domain.Nickname;
import attendance.view.OutputView;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Application {
    private static final String QUIT_OPTION = "Q";
    private static final Map<String, Runnable> options;
    private static final AttendanceManager attendanceManager = new AttendanceManager();

    static {
        options = new HashMap<>();
        options.put("1", Application::doAttendance);
        options.put("2", Application::modifyAttendance);
        options.put("3", Application::checkAttendanceHistory);
        options.put("4", Application::checkDangerousCrews);
        options.put(QUIT_OPTION, null);
    }

    public static void main(String[] args) {
        initAttendances();
        while (true) {
            LocalDate today = now();
            String option = readOption(today);
            if (option.equals(QUIT_OPTION)) {
                break;
            }
            try {
                run(option);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void initAttendances() {
        try {
            BufferedReader bufferedReader = AttendancesFileReader.readFile();
            AttendanceFileParser.initAttendances(bufferedReader, attendanceManager);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void run(final String option) {
        Runnable function = options.getOrDefault(option, null);
        if (function == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 옵션입니다.");
        }
        function.run();
    }

    private static void doAttendance() {
        LocalDate today = now();
        boolean isOperationDate = CampusManager.isOperationDate(today);
        if (!isOperationDate) {
            OutputView.printNotOperationDate(today);
            return;
        }
        Nickname crewNickname = new Nickname(readCrewNickname());
        attendanceManager.validateExistingCrew(crewNickname);
        Optional<Attendance> existingAttendance = attendanceManager.findAttendance(crewNickname, today);
        if (existingAttendance.isPresent()) {
            OutputView.printDuplicatedAttendance();
            return;
        }
        LocalTime attendanceTime = readAttendanceTime();
        CampusManager.validateOperationTime(attendanceTime);
        Attendance attendance = attendanceManager.addAttendance(crewNickname, today, attendanceTime);
        OutputView.printAttendance(attendance);
    }

    private static void modifyAttendance() {
        LocalDate today = now();
        Nickname crewNickname = new Nickname(readCrewNicknameToModify());
        attendanceManager.validateExistingCrew(crewNickname);
        LocalDate dateToModify = readAttendanceDateToModify(today);
        boolean isOperationDate = CampusManager.isOperationDate(dateToModify);
        if (!isOperationDate) {
            OutputView.printNotOperationDate(dateToModify);
            return;
        }
        Optional<Attendance> existingAttendance = attendanceManager.findAttendance(crewNickname, dateToModify);
        if (existingAttendance.isEmpty()) {
            OutputView.printNoAttendanceToModify();
            return;
        }
        LocalTime modificationTime = readAttendanceModificationTime();
        CampusManager.validateOperationTime(modificationTime);
        Attendance modifiedAttendance = attendanceManager.modifyAttendance(crewNickname, dateToModify, modificationTime);
        OutputView.printAttendanceModificationResult(existingAttendance.get(), modifiedAttendance);
    }

    private static void checkAttendanceHistory() {
        LocalDate today = now();
        Nickname crewNickname = new Nickname(readCrewNickname());
        attendanceManager.validateExistingCrew(crewNickname);
        List<Attendance> monthlyAttendances = attendanceManager.getMonthlyAttendances(today, crewNickname);
        OutputView.printMonthlyAttendances(today, crewNickname, monthlyAttendances);
        AttendanceStatistics attendanceStatistics = attendanceManager.getAttendanceStatistics(today, crewNickname);
        OutputView.printAttendanceStatistics(attendanceStatistics);
    }

    private static void checkDangerousCrews() {
        LocalDate today = now();
        Map<Nickname, AttendanceStatistics> dangerousCrewsInformation = attendanceManager.getDangerousCrewsInformation(today);
        OutputView.printDangerousCrewsInformation(dangerousCrewsInformation);
    }

    private static LocalDate now() {
        return LocalDate.of(2024, 12, 16);
    }
}
