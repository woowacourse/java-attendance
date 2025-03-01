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

public class AttendanceController {

    private final AttendanceBook attendanceBook;
    private final InputView inputView;

    public AttendanceController(AttendanceBook attendanceBook, InputView inputView) {
        this.attendanceBook = attendanceBook;
        this.inputView = inputView;
    }

    public void run() {
        LocalDate now = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
        String commandCode = inputView.readCommandCode(now);
        if (commandCode.equals("Q")) {
            System.out.println("프로그램을 종료합니다.");
            return;
        }
        if (commandCode.equals("1")) {
            String crewName = inputView.readNickname();
            Crew crew = Crew.of(crewName);
            CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);
            String rawTime = inputView.readTime();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(rawTime, timeFormatter);
            LocalDate date = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
            AttendanceTime attendanceTime = AttendanceTime.of(date, time);
            crewAttendance.attend(attendanceTime);
            return;
        }
        if (commandCode.equals("2")) {
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
            return;
        }
        if (commandCode.equals("3")) {
            String crewName = inputView.readNickname();
            Crew crew = Crew.of(crewName);
            CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);
            LocalDate date = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
            List<AttendanceTime> localDateTimes = crewAttendance.readAttendanceTimesBefore(date);
            return;
        }
        if (commandCode.equals("4")) {
            LocalDate date = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
            List<CrewAttendance> disciplinaryCrews = attendanceBook.findDisciplinaryCrews(date);
            return;
        }
    }
}
