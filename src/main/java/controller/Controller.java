package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Crew;
import domain.FileWithAttendanceData;
import domain.Holiday;
import domain.Option;
import domain.Penalty;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import view.InputView;
import view.OutputView;

public class Controller {

    private static final int ATTENDANCE_YEAR = 2024;
    private static final int ATTENDANCE_MONTH = 12;
    private static final int ATTENDANCE_DAY_OF_MONTH = 16;

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBook attendanceBook;

    public Controller(InputView inputView, OutputView outputView, AttendanceBook attendanceBook) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBook = attendanceBook;
    }

    public void loadFile() {
        FileWithAttendanceData fileWithAttendanceData = new FileWithAttendanceData(attendanceBook);
        fileWithAttendanceData.loadFile("src/main/resources/attendances.csv");
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
            if (option == Option.CHECK_RECORDS) {
                checkRecords(nowDate);
            }
            if (option == Option.CHECK_EXPULSION_RISK_CREW) {
                checkExpulsionRiskCrew(nowDate);
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

            Attendance attendance = attendanceBook.attendCrew(name, nowDate, attendTime);

            outputView.displayAttendanceCheck(nowDate, attendance.getTime(),
                    attendance.determineStatus().getDescription());
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

            displayAttendanceEditResult(originAttendance, editDate, updatedAttendance);
        } catch (DateTimeParseException e) {
            System.out.println("[ERROR] 시간 형식이 일치하지 않습니다.");
        } catch (DateTimeException e) {
            System.out.println("[ERROR] 날짜 형식이 일치하지 않습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayAttendanceEditResult(Attendance originAttendance, LocalDate editDate,
                                             Attendance updatedAttendance) {
        if (originAttendance == null) {
            outputView.displayAttendanceEditWithAbsence(editDate, updatedAttendance.getTime(),
                    updatedAttendance.determineStatus().getDescription());
            return;
        }
        outputView.displayAttendanceEdit(editDate, originAttendance.getTime(),
                originAttendance.determineStatus().getDescription(), updatedAttendance.getTime(),
                updatedAttendance.determineStatus().getDescription());
    }

    private void checkRecords(LocalDate nowDate) {
        String name = inputView.readName();

        outputView.displayRecordMessage(name);
        displayRecords(nowDate, name);
        outputView.displayPenaltyCount(attendanceBook.calculateAttendanceCount(name, nowDate),
                attendanceBook.calculateLatenessCount(name, nowDate),
                attendanceBook.calculateAbsenceCount(name, nowDate)
        );
        displayPenaltyStatus(nowDate, name);
    }

    private void displayPenaltyStatus(LocalDate nowDate, String name) {
        Penalty penalty = attendanceBook.determinePenaltyStatus(name, nowDate);
        if (penalty != Penalty.PASS) {
            outputView.displayPenaltyStatus(penalty.getDescription());
        }
    }

    private void displayRecords(LocalDate nowDate, String name) {
        LocalDate startDate = LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, 1);

        for (LocalDate date = startDate; date.isBefore(nowDate); date = date.plusDays(1)) {
            displayAttendanceRecord(name, date);
        }
    }

    private void displayAttendanceRecord(String name, LocalDate date) {
        if (Holiday.isHoliday(date)) {
            return;
        }

        Attendance attendance = attendanceBook.findAttendance(name, date);
        if (attendance == null) {
            outputView.displayAbsenceRecord(date);
            return;
        }
        outputView.displayRecord(date, attendance.getTime(), attendance.determineStatus().getDescription());
    }

    private void checkExpulsionRiskCrew(LocalDate nowDate) {
        List<Crew> riskCrewResult = attendanceBook.checkExpulsionRiskCrew(nowDate);

        outputView.displayExpulsionRiskCrewMessage();

        for (Crew crew : riskCrewResult) {
            outputView.displayExpulsionRiskCrew(crew.getName(), crew.calculateAbsenceCount(nowDate),
                    crew.calculateLatenessCount(nowDate), crew.determinePenaltyStatus(nowDate).getDescription());
        }
    }

    private LocalDate parseDate(String editDayOfMonth) {
        try {
            return LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, Integer.parseInt(editDayOfMonth));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 일치하지 않습니다.");
        }
    }
}
