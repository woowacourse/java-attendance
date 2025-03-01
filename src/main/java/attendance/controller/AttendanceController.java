package attendance.controller;

import attendance.controller.util.DateTimeConverter;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceTime;
import attendance.domain.Attendances;
import attendance.domain.Menu;
import attendance.dto.AttendanceResultResponse;
import attendance.dto.AttendancesResponse;
import attendance.dto.PenaltyCrewsResponse;
import attendance.util.AttendanceBookFactory;
import attendance.util.AttendancesFileReader;
import attendance.util.CrewAttendancesDataParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LocalDate today;
    private AttendanceBook attendanceBook;

    public AttendanceController(InputView inputView, OutputView outputView, LocalDate today) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.today = today;
    }

    public void run() {
        String fileInput = AttendancesFileReader.read();
        Map<String, List<LocalDateTime>> crewAttendancesData = CrewAttendancesDataParser.parse(fileInput);
        attendanceBook = AttendanceBookFactory.create(crewAttendancesData, today);

        boolean continueProcessMenu = true;
        while (continueProcessMenu) {
            continueProcessMenu = processMenu();
        }
    }

    private boolean processMenu() {
        try {
            return executeMenu(inputView.readSelectMenu(today));
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return true;
        }
    }

    private boolean executeMenu(final String input) {
        Menu selectedMenu = Menu.from(input);
        if (Menu.ATTEND.equals(selectedMenu)) {
            attend();
        }
        if (Menu.UPDATE_ATTENDANCE.equals(selectedMenu)) {
            updateAttendance();
        }
        if (Menu.PRINT_ATTENDANCES_BY_CREW.equals(selectedMenu)) {
            printAttendancesByCrew();
        }
        if (Menu.PRINT_WARNING.equals(selectedMenu)) {
            printPenaltyCrews();
        }
        return !Menu.QUIT.equals(selectedMenu);
    }

    private void attend() {
        AttendanceDate attendanceDate = AttendanceDate.from(today);

        LocalTime time = DateTimeConverter.convertToTime(inputView.readAttendTime());
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);
        attendanceBook.attend(inputView.readNickname(), attendance);

        outputView.printAttendResult(AttendanceResultResponse.from(attendance));
    }

    private void updateAttendance() {
        String nickname = inputView.readUpdateAttendanceNickname();

        LocalDateTime dateTime = DateTimeConverter.convertToDateTime(
                inputView.readUpdateAttendanceDay(), inputView.readUpdateAttendanceTime(), today);

        Attendance before = attendanceBook.findByNicknameAndDate(nickname, dateTime);
        AttendanceResultResponse beforeResponse = AttendanceResultResponse.from(before);
        Attendance after = attendanceBook.updateAttendance(nickname, dateTime);
        AttendanceResultResponse afterResponse = AttendanceResultResponse.from(after);

        outputView.printUpdateResult(beforeResponse, afterResponse);
    }

    private void printAttendancesByCrew() {
        String nickname = inputView.readNickname();
        Attendances attendances = attendanceBook.findByNickname(nickname);

        outputView.printAttendancesByCrew(AttendancesResponse.of(nickname, attendances));

        AttendancePenalty warning = attendances.calculatePenalty();
        if (!AttendancePenalty.NONE.equals(warning)) {
            outputView.printPenalty(warning);
        }
    }

    private void printPenaltyCrews() {
        PenaltyCrewsResponse response = PenaltyCrewsResponse.from(attendanceBook.findPenaltyCrews());
        outputView.printPenaltyCrews(response);
    }
}
