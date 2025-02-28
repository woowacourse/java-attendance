package attendance;

import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatistics;
import attendance.domain.CampusManager;
import attendance.domain.Crew;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Application {
    private static final String QUIT_OPTION = "Q";
    private static final int INDEX_AS_CREW_NICKNAME = 0;
    private static final int INDEX_AS_ATTENDANCE_DATE_TIME = 1;
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
        initializeAttendances();
        while (true) {
            LocalDate today = now();
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

    private static void initializeAttendances() {
        try {
            BufferedReader bufferedReader = AttendancesFileReader.readFile();
            bufferedReader.readLine();
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                Crew crew = new Crew(split[INDEX_AS_CREW_NICKNAME]);
                if (!attendanceManager.isCrewExists(crew)) {
                    attendanceManager.addCrew(crew);
                }
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String attendanceDateTime = split[INDEX_AS_ATTENDANCE_DATE_TIME];
                LocalDate attendanceDate = LocalDate.parse(attendanceDateTime, dateTimeFormatter);
                LocalTime attendanceTime = LocalTime.parse(attendanceDateTime, dateTimeFormatter);
                attendanceManager.addAttendance(crew, attendanceDate, attendanceTime);
            }
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
        LocalDate today = now();
        String crewNickname = InputView.readCrewNickname();
        Crew crew = new Crew(crewNickname);
        boolean isCrewExists = attendanceManager.isCrewExists(crew);
        if (!isCrewExists) {
            OutputView.printNotRegisteredCrewNickname();
            return;
        }
        List<Attendance> monthlyAttendances = attendanceManager.getMonthlyAttendances(today, crew);
        OutputView.printMonthlyAttendances(today, crew, monthlyAttendances);
        AttendanceStatistics attendanceStatistics = attendanceManager.getAttendanceStatistics(today, crew);
        OutputView.printAttendanceStatistics(attendanceStatistics);
    }

    private static void checkDangerousCrews() {
        LocalDate today = now();
        Map<Crew, AttendanceStatistics> dangerousCrews = attendanceManager.getDangerousCrews(today);
        OutputView.printDangerousCrews(dangerousCrews);
    }

    private static LocalDate now() {
        return LocalDate.of(2024, 12, 16);
    }
}
