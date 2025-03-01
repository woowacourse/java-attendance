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
        } else if (command.equals(Command.ATTENDANCE_CORRECTION)) {
            processAttendanceCorrection();
        }
    }

    private void processAttendanceCheck(final AttendanceDateTime todayDateTime) {
        Attendance.validatePossibleDate(todayDateTime);
        final Nickname nickname = new Nickname(InputView.readNickname());
        final Crew crew = manager.findCrewByNickname(nickname);
        final LocalDate date = todayDateTime.getDateTime().toLocalDate();
        final AttendanceTime attendanceTime = AttendanceTime.of(InputView.readAttendanceTime());
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(date, attendanceTime);
        final Attendance attendance = Attendance.of(attendanceDateTime);
        manager.saveAttendance(crew, attendance);
        OutputView.printAttendance(new DateInfoDto(attendanceDateTime), attendance.getAttendanceStatus());
    }

    private void processAttendanceCorrection() {
        final Nickname nickname = new Nickname(InputView.readNickname());
        final Crew crew = manager.findCrewByNickname(nickname);
        final AttendanceBook attendanceBook = manager.findAttendanceBookByCrew(crew);

        final DayOfMonth dayOfMonth = DayOfMonth.of(InputView.readDayOfMonthByUpdate());
        final Attendance oldAttendance = attendanceBook.findByDayOfMonth(dayOfMonth);
        final AttendanceDateTime oldDateTime = oldAttendance.getAttendanceDateTime();

        final AttendanceTime attendanceTime = AttendanceTime.of(InputView.readAttendanceTime());
        oldAttendance.validateSameTime(attendanceTime);
        final AttendanceDateTime newDateTime = AttendanceDateTime.of(oldDateTime.getDateTime().toLocalDate(), attendanceTime);
        final Attendance newAttendance = Attendance.of(newDateTime);

        attendanceBook.update(oldAttendance, newAttendance);
        OutputView.printAttendanceCorrection(new DateInfoDto(oldAttendance.getAttendanceDateTime()), oldAttendance.getAttendanceStatus(), new DateInfoDto(newAttendance.getAttendanceDateTime()), newAttendance.getAttendanceStatus());
    }
}
