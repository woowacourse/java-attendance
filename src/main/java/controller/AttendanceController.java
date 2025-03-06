package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Crew;
import domain.Holiday;
import domain.Option;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import loader.FileLoader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final int ATTENDANCE_YEAR = 2024;
    private static final int ATTENDANCE_MONTH = 12;
    private static final int ATTENDANCE_DAY_OF_MONTH = 16;
    public static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBook attendanceBook;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceBook attendanceBook) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBook = attendanceBook;
    }

    public void loadFile() {
        FileLoader fileLoader = new FileLoader(attendanceBook);
        fileLoader.loadFile(ATTENDANCE_FILE_PATH);
    }

    public void runSystem() {
        LocalDate nowDate = LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, ATTENDANCE_DAY_OF_MONTH);
        loadFile();

        try {
            selectOption(nowDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void selectOption(LocalDate nowDate) {
        while (true) {
            Option option = Option.from(inputView.readOptionNumber(nowDate));

            if (option == Option.ATTEND) {
                attend(nowDate);
            }
            if (option == Option.EDIT) {
                edit(nowDate);
            }
            if (option == Option.DISPLAY_ATTENDANCE_SUMMARY) {
                displayAttendanceSummary(nowDate);
            }
            if (option == Option.DISPLAY_EXPULSION_RISK_CREW) {
                findAndDisplayExpulsionRiskCrews(nowDate);
            }
            if (option == Option.QUIT) {
                break;
            }
        }
    }

    private void attend(LocalDate nowDate) {
        try {
            String name = inputView.readName();
            LocalTime attendTime = LocalTime.parse(inputView.readAttendTime());
            outputView.printAttendanceCheck(nowDate, attendanceBook.attendCrew(name, nowDate, attendTime));
        } catch (DateTimeParseException e) {
            System.out.println("[ERROR] 시간 형식이 일치하지 않습니다.");
        }
    }

    private void edit(LocalDate nowDate) {
        try {
            String name = inputView.readEditName();
            LocalDate editDate = parseDate(inputView.readEditDayOfMonth());
            LocalTime editTime = LocalTime.parse(inputView.readEditTime());

            Attendance originAttendance = attendanceBook.findAttendance(name, editDate);
            Attendance updatedAttendance = attendanceBook.editCrew(name, nowDate, editDate, editTime);

            outputView.printAttendanceEdit(editDate, originAttendance, updatedAttendance);
        } catch (DateTimeParseException e) {
            System.out.println("[ERROR] 시간 형식이 일치하지 않습니다.");
        } catch (DateTimeException e) {
            System.out.println("[ERROR] 날짜 형식이 일치하지 않습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayAttendanceSummary(LocalDate nowDate) {
        String name = inputView.readName();

        outputView.printRecordMessage(name);
        displayAttendanceRecords(nowDate, name);
        outputView.printPenaltyCount(
                attendanceBook.calculateAttendanceCount(name, nowDate),
                attendanceBook.calculateLatenessCount(name, nowDate),
                attendanceBook.calculateAbsenceCount(name, nowDate)
        );
        outputView.printPenaltyStatus(attendanceBook.determinePenaltyStatus(name, nowDate));
    }

    private void displayAttendanceRecords(LocalDate nowDate, String name) {
        LocalDate startDate = LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, 1);

        startDate.datesUntil(nowDate)
                .filter(date -> !Holiday.isHoliday(date))
                .forEach(date -> outputView.printRecord(date, attendanceBook.findAttendance(name, date)));
    }

    private void findAndDisplayExpulsionRiskCrews(LocalDate nowDate) {
        List<Crew> riskCrewResult = attendanceBook.findExpulsionRiskCrews(nowDate);
        outputView.printExpulsionRiskCrewList(riskCrewResult, nowDate);
    }

    private LocalDate parseDate(String editDayOfMonth) {
        try {
            return LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, Integer.parseInt(editDayOfMonth));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 일치하지 않습니다.");
        }
    }
}
