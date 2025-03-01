package attendance.controller;

import static attendance.view.Command.ATTENDANCE;
import static attendance.view.Command.ATTENDANCE_CHECK;
import static attendance.view.Command.ATTENDANCE_UPDATE;
import static attendance.view.Command.ATTENDANCE_WARNING_CHECK;

import attendance.AttendanceBookInitializer;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceResult;
import attendance.domain.CrewAttendance;
import attendance.domain.CrewAttendances;
import attendance.view.Command;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Optional;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(LocalDate today) {
        AttendanceBook attendanceBook = new AttendanceBookInitializer().Initialize();

        Command command = inputView.inputCommand(today);
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
//        crewAttendances.
    }
}
