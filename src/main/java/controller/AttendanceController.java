package controller;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceTime;
import domain.crew.Crew;
import domain.crew.CrewAttendance;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import view.InputView;
import view.OutputView;

public class AttendanceController {

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
        Crew crew = Crew.of(crewName);
        CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String rawTime = inputView.readTime();
        LocalTime time = LocalTime.parse(rawTime, timeFormatter);

        AttendanceTime attendanceTime = AttendanceTime.of(today, time);
        crewAttendance.attend(attendanceTime);

        outputView.attendPage(attendanceTime);
    }

    private void modifyAttendanceTime(LocalDate today) {
        String crewName = inputView.readNicknameForModify();
        Crew crew = Crew.of(crewName);
        CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);

        String rawDay = inputView.readModifyDay();
        int dayOfMonth = Integer.parseInt(rawDay);
        LocalDate date = LocalDate.of(2024, 12, dayOfMonth);
        if (date.isAfter(today)) {
            throw new IllegalArgumentException("미래의 날짜는 수정할 수 없습니다.");
        }

        String rawTime = inputView.readModifyTime();
        LocalTime time = LocalTime.parse(rawTime, DateTimeFormatter.ISO_LOCAL_TIME);

        AttendanceTime attendanceTime = AttendanceTime.of(date, time);
        Optional<AttendanceTime> previous = crewAttendance.modify(attendanceTime);

        outputView.modifyPage(previous, attendanceTime);
    }

    private void readAttendanceLogs(LocalDate today) {
        String crewName = inputView.readNickname();
        Crew crew = Crew.of(crewName);

        CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);

        outputView.attendanceLogPage(crewAttendance, today);
    }

    private void readDisciplinaryCrews(LocalDate today) {
        List<CrewAttendance> disciplinaryCrews = attendanceBook.findDisciplinaryCrews(today);
        outputView.disciplinaryCrewsPage(disciplinaryCrews, today);
    }

    private void quit() {
        System.out.println("프로그램을 종료합니다.");
    }
}
