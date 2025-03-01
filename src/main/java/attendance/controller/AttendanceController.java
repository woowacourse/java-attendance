package attendance.controller;

import static attendance.io.view.Command.ATTENDANCE;
import static attendance.io.view.Command.ATTENDANCE_CHECK;
import static attendance.io.view.Command.ATTENDANCE_UPDATE;
import static attendance.io.view.Command.ATTENDANCE_WARNING_CHECK;
import static attendance.io.view.Command.QUIT;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceResult;
import attendance.domain.CrewAttendance;
import attendance.domain.CrewAttendances;
import attendance.initialize.AttendanceBookInitializer;
import attendance.io.view.Command;
import attendance.io.view.InputView;
import attendance.io.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(LocalDate today, AttendanceBookInitializer attendanceBookInitializer) {
        AttendanceBook attendanceBook = attendanceBookInitializer.Initialize();
        inputCommandAndExecuteUtilQuit(today, attendanceBook);
    }

    private void inputCommandAndExecuteUtilQuit(LocalDate today, AttendanceBook attendanceBook) {
        Command command = null;
        do {
            try {
                command = inputView.inputCommand(today);
                executeCommand(today, attendanceBook, command);
            } catch (RuntimeException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        } while (command != QUIT);
    }

    private void executeCommand(LocalDate today, AttendanceBook attendanceBook, Command command) {
        if (command == ATTENDANCE) {
            attend(today, attendanceBook);
        }
        if (command == ATTENDANCE_UPDATE) {
            updateAttendance(YearMonth.from(today), attendanceBook);
        }
        if (command == ATTENDANCE_CHECK) {
            checkAttendances(today.minusDays(1), attendanceBook);
        }
        if (command == ATTENDANCE_WARNING_CHECK) {
            checkAttendanceWarningCrews(today.minusDays(1), attendanceBook);
        }
    }

    public void attend(LocalDate attendanceDate, AttendanceBook attendanceBook) {
        String nickname = inputView.inputNickname();
        LocalTime attendanceTime = inputView.inputAttendanceTime();

        LocalDateTime attendanceDateTime = LocalDateTime.of(attendanceDate, attendanceTime);
        attendanceBook.attend(nickname, attendanceDateTime);

        outputView.printAttendance(new Attendance(nickname, attendanceDateTime));
    }

    private void updateAttendance(YearMonth uppdateYearMonth, AttendanceBook attendanceBook) {
        String nickname = inputView.inputNicknameForAttendanceUpdate();
        int dateOfMonth = inputView.inputUpdateDateOfMonth();
        LocalTime updateTime = inputView.inputUpdateTime();

        LocalDate updateDate = LocalDate.of(uppdateYearMonth.getYear(), uppdateYearMonth.getMonth(), dateOfMonth);
        LocalDateTime updateDateTime = LocalDateTime.of(updateDate, updateTime);

        Optional<Attendance> pastAttendance = attendanceBook.findAttendance(nickname, updateDate);
        attendanceBook.updateAttendance(nickname, updateDateTime);
        Attendance currentAttendance = new Attendance(nickname, updateDateTime);

        outputView.printUpdatedAttendance(pastAttendance, currentAttendance);
    }

    private void checkAttendances(LocalDate checkEndDate, AttendanceBook attendanceBook) {
        String nickname = inputView.inputNickname();
        CrewAttendance crewAttendance = attendanceBook.findAttendancesByNickname(nickname);
        AttendanceResult attendanceResult = crewAttendance.createAttendanceResult(checkEndDate);
        outputView.printAttendanceResult(crewAttendance.getAttendances(), attendanceResult, checkEndDate);
    }

    private void checkAttendanceWarningCrews(LocalDate checkEndDate, AttendanceBook attendanceBook) {
        CrewAttendances crewAttendances = attendanceBook.createCrewAttendances();
        List<AttendanceResult> attendanceResults = crewAttendances.createAllAttendanceResult(checkEndDate);
        outputView.printAttendanceResults(attendanceResults);
    }
}
