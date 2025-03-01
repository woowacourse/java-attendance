package controller;

import model.*;
import view.DateInfoDto;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;

public class AttendanceController {

    private final AttendanceManager manager;

    public AttendanceController(final AttendanceManager manager) {
        this.manager = manager;
    }

    public void run() {
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of("2024-12-16 10:00");
        final String symbolInput = InputView.readCommand(new DateInfoDto(attendanceDateTime));
        final Command command = Command.findBySymbol(symbolInput);

        if (command.equals(Command.ATTENDANCE_CHECK)) {
            processAttendanceCheck(attendanceDateTime);
        }

    }

    private void processAttendanceCheck(final AttendanceDateTime todayDateTime) {
        Attendance.validatePossibleDate(todayDateTime);
        final Nickname nickname = new Nickname(InputView.readNickname());
        final Crew crew = manager.findByNickname(nickname);
        final LocalDate date = todayDateTime.getDateTime().toLocalDate();
        final AttendanceTime attendanceTime = AttendanceTime.of(InputView.readAttendanceTime());
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(date, attendanceTime);
        final Attendance attendance = Attendance.of(attendanceDateTime);
        manager.saveAttendance(crew, attendance);
        OutputView.printAttendance(new DateInfoDto(attendanceDateTime), attendance.getAttendanceStatus());
    }
}
