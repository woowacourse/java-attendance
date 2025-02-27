package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendancesFile;
import domain.Time;
import java.nio.file.Path;
import java.time.LocalDateTime;
import view.InputView;
import view.OutputView;

public class Controller {
    private static final LocalDateTime today = LocalDateTime.of(2024, 12, 18, 10, 0);
    private static final Path path = Path.of("src", "main", "resources", "attendances.csv");

    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void runAttendanceSystem() {
        AttendanceBook attendanceBook = new AttendanceBook(new AttendancesFile().loadInitialAttendances(path));
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
        String menuOption = "";
        while (!menuOption.equals("Q")) {
            menuOption = inputView.readMenuOption(today);
            if (menuOption.equals("1")) {
                runCheckAttendance(attendanceBook);
                continue;
            }
            if (menuOption.equals("2")) {
                runChangeAttendance(attendanceBook);
                continue;
            }
            if (menuOption.equals("3")) {
                runShowCrewAttendance(attendanceBook);
                continue;
            }
            if (menuOption.equals("4")) {
                runShowRiskOfExpelledCrews(attendanceBook);
                continue;
            }
            outputView.printException("올바른 번호를 입력해주세요");
        }
    }

    private void runCheckAttendance(AttendanceBook attendanceBook) {
        String nickname = inputView.readCheckAttendanceNickname();
        Time time = new Time(inputView.readCheckAttendanceTime());

        Attendance checkedAttendance = attendanceBook.addAttendanceForCrew(nickname,
                LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), time.getHour(),
                        time.getMinute()));

        outputView.printCheckAttendance(checkedAttendance.getDateTime(), checkedAttendance.calculateAttendanceStatus());
    }

    private void runChangeAttendance(AttendanceBook attendanceBook) {
        String nickname = inputView.readChangeAttendanceNickname();
        String dayOfMonth = inputView.readChangeAttendanceDayOfMonth();
        Time time = new Time(inputView.readChangeAttendanceTime());

        Attendance originalAttendance = attendanceBook.getAttendanceByNicknameAndDate(nickname,
                Integer.parseInt(dayOfMonth));
        Attendance changeAttendance = attendanceBook.updateAttendanceForCrew(nickname,
                LocalDateTime.of(today.getYear(), today.getMonth(), Integer.parseInt(dayOfMonth),
                        time.getHour(), time.getMinute()), today);

        outputView.printChangeAttendance(originalAttendance, changeAttendance);
    }

    private void runShowCrewAttendance(AttendanceBook attendanceBook) {
        String nickname = inputView.readShowCrewAttendanceNickname();
    }

    private void runShowRiskOfExpelledCrews(AttendanceBook attendanceBook) {

    }
}
