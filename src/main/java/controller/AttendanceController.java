package controller;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceTime;
import domain.crew.Crew;
import domain.crew.CrewAttendance;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final AttendanceBook attendanceBook;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(AttendanceBook attendanceBook, InputView inputView, OutputView outputView) {
        this.attendanceBook = attendanceBook;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Command command = Command.NONE;
        do {
            LocalDate today = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
            try {
                command = Command.from(inputView.readCommandCode(today));
                runCommand(command, today);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (command != Command.QUIT);
    }

    public void runCommand(Command command, LocalDate today) {
        if (command == Command.ATTEND) {
            attend(today);
        }
        if (command == Command.MODIFY_ATTENDANCE) {
            modifyAttendanceTime(today);
        }
        if (command == Command.READ_ATTENDANCE_LOG) {
            readAttendanceLogs(today);
        }
        if (command == Command.READ_DISCIPLINARY_CREWS) {
            readDisciplinaryCrews(today);
        }
        if (command == Command.QUIT) {
            quit();
        }
    }

    private void attend(LocalDate today) {
        String crewName = inputView.readNickname();
        CrewAttendance crewAttendance = getCrewAttendance(crewName);

        String rawTime = inputView.readTime();
        LocalTime time = parseTime(rawTime);

        AttendanceTime attendanceTime = AttendanceTime.of(today, time);
        crewAttendance.attend(attendanceTime);

        outputView.attendPage(attendanceTime);
    }

    private void modifyAttendanceTime(LocalDate today) {
        String crewName = inputView.readNicknameForModify();
        CrewAttendance crewAttendance = getCrewAttendance(crewName);

        String rawDay = inputView.readModifyDay();
        LocalDate date = parseDate(rawDay);
        if (date.isAfter(today)) {
            throw new IllegalArgumentException("미래의 날짜는 수정할 수 없습니다.");
        }

        String rawTime = inputView.readModifyTime();
        LocalTime time = parseTime(rawTime);

        AttendanceTime attendanceTime = AttendanceTime.of(date, time);
        AttendanceTime previous = crewAttendance.modify(attendanceTime);

        outputView.modifyPage(previous, attendanceTime);
    }

    private LocalDate parseDate(String rawDay) {
        LocalDate date;
        try {
            int dayOfMonth = Integer.parseInt(rawDay);
            date = LocalDate.of(2024, 12, dayOfMonth);
        } catch (NumberFormatException | DateTimeException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
        }
        return date;
    }

    private LocalTime parseTime(String rawTime) {
        try {
            return LocalTime.parse(rawTime, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("유효하지 않은 시간입니다.");
        }
    }

    private void readAttendanceLogs(LocalDate today) {
        String crewName = inputView.readNickname();
        CrewAttendance crewAttendance = getCrewAttendance(crewName);

        outputView.attendanceLogPage(crewAttendance, today);
    }

    private CrewAttendance getCrewAttendance(String crewName) {
        Crew crew = Crew.of(crewName);
        return attendanceBook.findCrewAttendanceByCrew(crew);
    }

    private void readDisciplinaryCrews(LocalDate today) {
        List<CrewAttendance> disciplinaryCrews = attendanceBook.findDisciplinaryCrews(today);
        outputView.disciplinaryCrewsPage(disciplinaryCrews, today);
    }

    private void quit() {
        System.out.println("프로그램을 종료합니다.");
    }
}
