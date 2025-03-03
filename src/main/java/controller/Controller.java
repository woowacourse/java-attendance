package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Attendances;
import domain.AttendancesFile;
import domain.MenuOption;
import domain.Time;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class Controller {
    private static final LocalDate TODAY = LocalDate.of(2024, 12, 18);
    private static final Path PATH = Path.of("src", "main", "resources", "attendances.csv");

    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void runAttendanceSystem() {
        AttendanceBook attendanceBook = new AttendanceBook(new AttendancesFile().loadInitialAttendances(PATH, TODAY));
        try {
            runMenuOption(attendanceBook);
        } catch (UnsupportedOperationException ex) {
            outputView.printAlreadyCheckedGuide(ex.getMessage());
            runMenuOption(attendanceBook);
        } catch (Exception ex) {
            outputView.printException(ex.getMessage());
            runMenuOption(attendanceBook);
        }
    }

    private void runMenuOption(AttendanceBook attendanceBook) {
        while (true) {
            MenuOption menuOption = MenuOption.selectOption(inputView.readMenuOption(TODAY));
            if (menuOption == MenuOption.QUIT) {
                return;
            }
            if (menuOption == MenuOption.CHECK_ATTENDANCE) {
                runCheckAttendance(attendanceBook);
            }
            if (menuOption == MenuOption.CHANGE_ATTENDANCE) {
                runChangeAttendance(attendanceBook);
            }
            if (menuOption == MenuOption.SHOW_CREW_ATTENDANCE) {
                runShowCrewAttendance(attendanceBook);
            }
            if (menuOption == MenuOption.SHOW_RISK_OF_EXPELLED_CREWS) {
                runShowRiskOfExpelledCrews(attendanceBook);
            }
        }
    }

    private void runCheckAttendance(AttendanceBook attendanceBook) {
        String nickname = inputView.readCheckAttendanceNickname();
        Time time = inputView.readCheckAttendanceTime();

        Attendance checkedAttendance = attendanceBook.addAttendanceForCrew(nickname,
                LocalDateTime.of(TODAY.getYear(), TODAY.getMonth(), TODAY.getDayOfMonth(), time.getHour(),
                        time.getMinute()));

        outputView.printCheckAttendance(checkedAttendance);
    }

    private void runChangeAttendance(AttendanceBook attendanceBook) {
        String nickname = inputView.readChangeAttendanceNickname();
        int dayOfMonth = inputView.readChangeAttendanceDayOfMonth();
        Time time = inputView.readChangeAttendanceTime();

        Attendance originalAttendance = attendanceBook.getAttendanceByNicknameAndDate(nickname,
                dayOfMonth);
        Attendance changeAttendance = attendanceBook.updateAttendanceForCrew(nickname,
                LocalDateTime.of(TODAY.getYear(), TODAY.getMonth(), dayOfMonth,
                        time.getHour(), time.getMinute()), TODAY);

        outputView.printChangeAttendance(originalAttendance, changeAttendance);
    }

    private void runShowCrewAttendance(AttendanceBook attendanceBook) {
        String nickname = inputView.readShowCrewAttendanceNickname();
        Attendances crewRecords = attendanceBook.getAttendanceByNickname(nickname);
        outputView.printCrewAttendances(nickname, crewRecords, TODAY);
    }

    private void runShowRiskOfExpelledCrews(AttendanceBook attendanceBook) {
        Map<String, Attendances> riskOfExpelledCrews = attendanceBook.getRiskOfExpelledCrews(TODAY);
        outputView.printRiskOfExpelledCrews(riskOfExpelledCrews, TODAY);
    }
}
