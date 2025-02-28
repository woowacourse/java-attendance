package attendance;

import static attendance.domain.CampusManager.validateOperationDate;
import static attendance.domain.CampusManager.validateOperationTime;
import static attendance.view.InputView.readAttendanceDateToModify;
import static attendance.view.InputView.readAttendanceModificationTime;
import static attendance.view.InputView.readAttendanceTime;
import static attendance.view.InputView.readCrewNickname;
import static attendance.view.InputView.readCrewNicknameToModify;
import static attendance.view.OutputView.printAttendance;
import static attendance.view.OutputView.printAttendanceModificationResult;
import static attendance.view.OutputView.printAttendanceStatistics;
import static attendance.view.OutputView.printDangerousCrewsInformation;
import static attendance.view.OutputView.printMonthlyAttendances;
import static attendance.view.OutputView.printNoAttendanceToModify;

import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatistics;
import attendance.domain.Nickname;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Options {
    private static final String QUIT_OPTION = "Q";

    private Map<String, Runnable> options;
    private final AttendanceManager attendanceManager;
    private final LocalDate today;

    public Options(final AttendanceManager attendanceManager, final LocalDate today) {
        this.options = initialize();
        this.attendanceManager = attendanceManager;
        this.today = today;
    }

    private Map<String, Runnable> initialize() {
        options = new HashMap<>();
        options.put("1", this::doAttendance);
        options.put("2", this::modifyAttendance);
        options.put("3", this::checkAttendanceHistory);
        options.put("4", this::checkDangerousCrews);
        options.put("Q", null);
        return options;
    }

    public boolean isExitOption(final String option) {
        return option.equals(QUIT_OPTION);
    }

    public void run(final String option) {
        Runnable function = options.getOrDefault(option, null);
        if (function == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 옵션입니다.");
        }
        function.run();
    }

    private void doAttendance() {
        validateOperationDate(today);
        Nickname crewNickname = new Nickname(readCrewNickname());
        attendanceManager.validateExistingCrew(crewNickname);
        attendanceManager.validateDuplicatedAttendance(crewNickname, today);
        LocalTime attendanceTime = readAttendanceTime();
        validateOperationTime(attendanceTime);
        Attendance attendance = attendanceManager.addAttendance(crewNickname, today, attendanceTime);
        printAttendance(attendance);
    }

    private void modifyAttendance() {
        Nickname crewNickname = new Nickname(readCrewNicknameToModify());
        attendanceManager.validateExistingCrew(crewNickname);
        LocalDate dateToModify = readAttendanceDateToModify(today);
        validateOperationDate(dateToModify);
        Optional<Attendance> existingAttendance = attendanceManager.findAttendance(crewNickname, dateToModify);
        if (existingAttendance.isEmpty()) {
            printNoAttendanceToModify();
            return;
        }
        LocalTime modificationTime = readAttendanceModificationTime();
        validateOperationTime(modificationTime);
        Attendance modifiedAttendance = attendanceManager.modifyAttendance(crewNickname, dateToModify, modificationTime);
        printAttendanceModificationResult(existingAttendance.get(), modifiedAttendance);
    }

    private void checkAttendanceHistory() {
        Nickname crewNickname = new Nickname(readCrewNickname());
        attendanceManager.validateExistingCrew(crewNickname);
        List<Attendance> monthlyAttendances = attendanceManager.getMonthlyAttendances(today, crewNickname);
        printMonthlyAttendances(today, crewNickname, monthlyAttendances);
        AttendanceStatistics attendanceStatistics = attendanceManager.getAttendanceStatistics(today, crewNickname);
        printAttendanceStatistics(attendanceStatistics);
    }

    private void checkDangerousCrews() {
        Map<Nickname, AttendanceStatistics> dangerousCrewsInformation = attendanceManager.getDangerousCrewsInformation(today);
        printDangerousCrewsInformation(dangerousCrewsInformation);
    }
}
