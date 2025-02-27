package attendance;

import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;
import attendance.domain.CampusManager;
import attendance.domain.Crew;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Application {
    private static final Map<String, Runnable> options;
    private static final String QUIT_OPTION = "Q";
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
        /**
         * 테스트 코드
         */
        attendanceManager.addCrew(new Crew("레오"));
        while (true) {
            LocalDate today = LocalDate.now();
            String option = InputView.readOption(today);
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
        String crewNickname = InputView.readCrewNickname();
        Crew crew = new Crew(crewNickname);
        boolean isCrewExists = attendanceManager.isCrewExists(crew);
        if (!isCrewExists) {
            OutputView.printNotRegisteredCrewNickname();
            return;
        }
        Optional<Attendance> existingAttendance = attendanceManager.findAttendance(crew, today);
        if (existingAttendance.isPresent()) {
            OutputView.printDuplicatedAttendance();
            return;
        }
        LocalTime attendanceTime = InputView.readAttendanceTime();
        boolean isOperationTime = CampusManager.isOperationTime(attendanceTime);
        if (!isOperationTime) {
            OutputView.printNotOperationTime();
            return;
        }
        Attendance attendance = attendanceManager.addAttendance(crew, today, attendanceTime);
        OutputView.printAttendance(attendance);
    }

    private static void modifyAttendance() {
        LocalDate today = now();
        String crewNickname = InputView.readCrewNicknameToModify();
        Crew crew = new Crew(crewNickname);
        boolean isCrewExists = attendanceManager.isCrewExists(crew);
        if (!isCrewExists) {
            OutputView.printNotRegisteredCrewNickname();
            return;
        }
        LocalDate dateToModify = InputView.readAttendanceDateToModify(today);
        boolean isOperationDate = CampusManager.isOperationDate(dateToModify);
        if (!isOperationDate) {
            OutputView.printNotOperationDate(dateToModify);
            return;
        }
        Optional<Attendance> existingAttendance = attendanceManager.findAttendance(crew, dateToModify);
        if (existingAttendance.isEmpty()) {
            OutputView.printNoAttendanceToModify();
            return;
        }
        LocalTime modificationTime = InputView.readAttendanceModificationTime();
        boolean isOperationTime = CampusManager.isOperationTime(modificationTime);
        if (!isOperationTime) {
            OutputView.printNotOperationTime();
            return;
        }
        Attendance modifiedAttendance = attendanceManager.modifyAttendance(crew, dateToModify, modificationTime);
        OutputView.printAttendanceModificationResult(existingAttendance.get(), modifiedAttendance);
    }

    private static void checkAttendanceHistory() {
        String crewNickname = InputView.readCrewNicknameToModify();
        Crew crew = new Crew(crewNickname);
    }

    private static void checkDangerousCrews() {

    }

    private static LocalDate now() {
        return LocalDate.now();
    }
}
