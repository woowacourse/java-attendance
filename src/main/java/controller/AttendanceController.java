package controller;

import domain.AttendanceBook;
import domain.AttendanceTime;
import domain.Crew;
import domain.CrewAttendance;
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
        while (true) {
            LocalDate today = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
            String commandCode = inputView.readCommandCode(today);
            if (commandCode.equals("Q")) {
                return;
            }
            runCommand(commandCode, today);
        }
    }

    public void runCommand(String commandCode, LocalDate today) {
        if (commandCode.equals("1")) {
            attend(today);
        }
        if (commandCode.equals("2")) {
            modifyAttendanceTime();
        }
        if (commandCode.equals("3")) {
            readAttendanceLogs(today);
        }
        if (commandCode.equals("4")) {
            readDisciplinaryCrews(today);
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

    private void modifyAttendanceTime() {
        String crewName = inputView.readNicknameForModify();
        Crew crew = Crew.of(crewName);
        CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);

        String rawDay = inputView.readModifyDay();
        int dayOfMonth = Integer.parseInt(rawDay);
        LocalDate date = LocalDate.of(2024, 12, dayOfMonth);

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
        List<AttendanceTime> localDateTimes = crewAttendance.readAttendanceTimesBefore(today);

        outputView.attendanceLogPage(localDateTimes);
    }

    private void readDisciplinaryCrews(LocalDate today) {
        List<CrewAttendance> disciplinaryCrews = attendanceBook.findDisciplinaryCrews(today);
        outputView.disciplinaryCrewsPage(disciplinaryCrews, today);
    }
}
