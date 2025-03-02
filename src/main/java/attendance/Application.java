package attendance;

import static attendance.domain.CampusManager.validateOperationDate;
import static attendance.domain.CampusManager.validateOperationTime;
import static attendance.view.InputView.readAttendanceDateToModify;
import static attendance.view.InputView.readAttendanceModificationTime;
import static attendance.view.InputView.readAttendanceTime;
import static attendance.view.InputView.readCrewNickname;
import static attendance.view.InputView.readCrewNicknameToModify;
import static attendance.view.InputView.readOption;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Application {
    private static final AttendanceManager attendanceManager = new AttendanceManager();

    public static void main(String[] args) {
        initAttendances(attendanceManager);
        while (true) {
            LocalDate today = now();
            String option = readOption(today);
            boolean isExitOption = Options.isExitOption(option);
            if (isExitOption) {
                break;
            }
            run(option);
        }
    }

    private static void run(final String option) {
        try {
            Options.run(option);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initAttendances(final AttendanceManager attendanceManager) {
        try {
            BufferedReader bufferedReader = AttendancesFileReader.readFile();
            AttendanceFileParser.initAttendances(bufferedReader, attendanceManager);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void doAttendance() {
        validateOperationDate(now());
        Nickname crewNickname = new Nickname(readCrewNickname());
        attendanceManager.validateExistingCrew(crewNickname);
        attendanceManager.validateDuplicatedAttendance(crewNickname, now());
        LocalTime attendanceTime = readAttendanceTime();
        validateOperationTime(attendanceTime);
        Attendance attendance = attendanceManager.addAttendance(crewNickname, now(), attendanceTime);
        printAttendance(attendance);
    }

    public static void modifyAttendance() {
        Nickname crewNickname = new Nickname(readCrewNicknameToModify());
        attendanceManager.validateExistingCrew(crewNickname);
        LocalDate dateToModify = readAttendanceDateToModify(now());
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

    public static void checkAttendanceHistory() {
        Nickname crewNickname = new Nickname(readCrewNickname());
        attendanceManager.validateExistingCrew(crewNickname);
        List<Attendance> monthlyAttendances = attendanceManager.getMonthlyAttendances(now(), crewNickname);
        printMonthlyAttendances(now(), crewNickname, monthlyAttendances);
        AttendanceStatistics attendanceStatistics = attendanceManager.getAttendanceStatistics(now(), crewNickname);
        printAttendanceStatistics(attendanceStatistics);
    }

    public static void checkDangerousCrews() {
        Map<Nickname, AttendanceStatistics> dangerousCrewsInformation = attendanceManager.getDangerousCrewsInformation(now());
        printDangerousCrewsInformation(dangerousCrewsInformation);
    }

    private static LocalDate now() {
        return LocalDate.of(2024, 12, 16);
    }
}
