package controller;

import model.*;
import view.DateInfoDto;
import view.InputView;
import view.OutputView;
import view.TypeInfoDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {

    private final AttendanceManager manager;

    public AttendanceController(final AttendanceManager manager) {
        this.manager = manager;
    }

    public void run(final AttendanceDateTime todayDateTime) {
        updateAttendanceRecord(todayDateTime);
        final String symbolInput = InputView.readCommand(new DateInfoDto(todayDateTime));
        final Command command = Command.findBySymbol(symbolInput);

        if (command.equals(Command.ATTENDANCE_CHECK)) {
            processAttendanceCheck(todayDateTime);
        } else if (command.equals(Command.ATTENDANCE_CORRECTION)) {
            processAttendanceCorrection();
        } else if (command.equals(Command.CHECK_ATTENDANCE_RECORDS_BY_CREW)) {
            processCrewAttendanceBook(todayDateTime);
        }
    }

    private void processAttendanceCheck(final AttendanceDateTime todayDateTime) {
        Attendance.validatePossibleDate(todayDateTime);
        final Nickname nickname = new Nickname(InputView.readNickname());
        final Crew crew = manager.findCrewByNickname(nickname);
        final LocalDate date = todayDateTime.getDateTime().toLocalDate();
        final AttendanceTime attendanceTime = AttendanceTime.of(InputView.readAttendanceTime());
        BusinessHours.validateOperatingTime(attendanceTime);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(date, attendanceTime);
        final Attendance attendance = Attendance.of(attendanceDateTime);
        manager.saveAttendance(crew, attendance);
        OutputView.printAttendance(TypeInfoDto.of(attendance));
    }

    private void processAttendanceCorrection() {
        final Nickname nickname = new Nickname(InputView.readNickname());
        final Crew crew = manager.findCrewByNickname(nickname);
        final AttendanceBook attendanceBook = manager.findAttendanceBookByCrew(crew);

        final DayOfMonth dayOfMonth = DayOfMonth.of(InputView.readDayOfMonthByUpdate());
        final Attendance oldAttendance = attendanceBook.findByDayOfMonth(dayOfMonth);
        final AttendanceDateTime oldDateTime = oldAttendance.getAttendanceDateTime();

        final AttendanceTime attendanceTime = AttendanceTime.of(InputView.readAttendanceTime());
        BusinessHours.validateOperatingTime(attendanceTime);
        oldAttendance.validateSameTime(attendanceTime);
        final AttendanceDateTime newDateTime = AttendanceDateTime.of(oldDateTime.getDateTime().toLocalDate(), attendanceTime);
        final Attendance newAttendance = Attendance.of(newDateTime);

        attendanceBook.update(oldAttendance, newAttendance);
        OutputView.printAttendanceCorrection(TypeInfoDto.of(oldAttendance), TypeInfoDto.of(newAttendance));
    }

    private void processCrewAttendanceBook(final AttendanceDateTime todayDateTime) {
        final Nickname nickname = new Nickname(InputView.readNickname());
        final Crew crew = manager.findCrewByNickname(nickname);
        final AttendanceBook attendanceBook = manager.findAttendanceBookByCrew(crew);

        final AttendanceBook recordAttendanceBook = attendanceBook.getBefore(todayDateTime);
        final List<AttendanceStatus> statuses = recordAttendanceBook.getStatuses();
        final AttendanceCountsDto countsDto = new AttendanceCountsDto(AttendanceStatus.countStatus(statuses));
        final ExpulsionType expulsionType = ExpulsionType.find(countsDto);

        final List<TypeInfoDto> dtos = new ArrayList<>();
        for (final Attendance attendance : recordAttendanceBook.getAttendances()) {
            dtos.add(TypeInfoDto.of(attendance));
        }
        OutputView.printCrewAttendanceBook(crew, dtos, countsDto, expulsionType);
    }

    private void updateAttendanceRecord(final AttendanceDateTime todayDateTime) {
        final int todayDayOfMonth = todayDateTime.getDateTime().getDayOfMonth();
        final int toDayOfMonth = ValidManager.getInstance().getLastByDayOfMonth(todayDayOfMonth);
        manager.updateAttendanceRecord(toDayOfMonth);
    }
}
