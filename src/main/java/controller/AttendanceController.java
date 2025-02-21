package controller;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.AttendanceTimes;
import domain.DecemberCalender;
import domain.MenuOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import util.AttendancesFileHandler;
import util.Convertor;
import util.RepeatExecutor;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final RepeatExecutor repeatExecutor;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.repeatExecutor = new RepeatExecutor(outputView);
    }

    public void run() {
        LocalDate nowDate = LocalDate.now();
        Attendance attendance = new Attendance(AttendancesFileHandler.generateAttendances(), nowDate);

        String option;
        do {
            option = getOption(nowDate);
            MenuOption menuOption = MenuOption.getMenuOption(option);
            process(menuOption, attendance, nowDate);
        } while (!option.equals(MenuOption.QUIT.getCommand()));
    }

    private String getOption(LocalDate nowDate) {
        outputView.printMenuHeader(nowDate);
        return inputView.readOption(Arrays.asList(MenuOption.values()));
    }

    private void process(MenuOption menuOption, Attendance attendance, LocalDate nowDate) {
        if (menuOption.equals(MenuOption.ATTENDANCE_CHECK)) {
            checkAttendance(attendance, nowDate);
            return;
        }
        if (menuOption.equals(MenuOption.ATTENDANCE_CORRECTION)) {
            editAttendance(attendance);
            return;
        }
        if (menuOption.equals(MenuOption.CREW_ATTENDANCE_CHECK)) {
            checkCrewAttendance(attendance);
            return;
        }
        if (menuOption.equals(MenuOption.CHECK_EXPELLED_CREW)) {
            checkExpelledCrew(attendance);
        }
    }

    private void checkAttendance(Attendance attendance, LocalDate nowDate) {
        validateCampusOpenDate(attendance, nowDate);

        String nickName = processNickNameInput(attendance);
        repeatExecutor.repeatUntilSuccess(() -> {
            LocalTime arrivalTime = getLocalTime();
            attendance.attend(nickName, LocalDateTime.of(nowDate, arrivalTime));
            return null;
        });

        LocalDateTime attendanceDateTime = attendance.getAttendanceDateTime(nickName, nowDate);
        AttendanceStatus attendanceStatus = attendance.getAttendanceStatus(nickName, nowDate);
        outputView.printCheckAttendanceMessage(attendanceDateTime, attendanceStatus);
    }

    private void validateCampusOpenDate(Attendance attendance, LocalDate nowDate) {
        if (attendance.isClosed(nowDate)) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s요일은 등교일이 아닙니다.", nowDate.getMonthValue(), nowDate.getDayOfMonth(),
                    Convertor.convertDayOfWeekToKorean(nowDate.getDayOfWeek())));
        }
    }

    private String processNickNameInput(Attendance attendance) {
        return repeatExecutor.repeatUntilSuccess(() -> {
            String nickName = inputView.readNickname();
            attendance.validateNickName(nickName);
            return nickName;
        });
    }

    private String getEditNickName(Attendance attendance) {
        return repeatExecutor.repeatUntilSuccess(() -> {
            String nickName = inputView.readEditNickname();
            attendance.validateNickName(nickName);
            return nickName;
        });
    }

    private LocalTime getLocalTime() {
        return repeatExecutor.repeatUntilSuccess(inputView::readArrivalTime);
    }

    private void editAttendance(Attendance attendance) {
        String nickName = getEditNickName(attendance);
        AttendanceTime oldAttendanceTime = getOldAttendanceTime(attendance, nickName);

        int editArrivalDate = oldAttendanceTime.getAttendanceDateTime().getDayOfMonth();
        LocalDate editDate = LocalDate.of(2024, 12, editArrivalDate);

        editAttendanceTime(attendance, nickName, editArrivalDate);
        AttendanceTime newAttendanceTime = attendance.findAttendanceTime(nickName, editDate);
        outputView.printEditAttendanceMessage(oldAttendanceTime, newAttendanceTime);
    }

    private AttendanceTime getOldAttendanceTime(Attendance attendance, String nickName) {
        return repeatExecutor.repeatUntilSuccess(() -> {
            DecemberCalender editArrivalDate = getEditArrivalDate();
            return attendance.findAttendanceTime(nickName, editArrivalDate.getDate());
        });
    }

    private void editAttendanceTime(Attendance attendance, String nickName, int editArrivalDate) {
        repeatExecutor.repeatUntilSuccess(() -> {
            LocalTime editArrivalTime = inputView.readEditArrivalTime();
            attendance.edit(nickName, editArrivalDate, editArrivalTime);
            return null;
        });
    }

    private DecemberCalender getEditArrivalDate() {
        return new DecemberCalender(inputView.readEditArrivalDate());
    }

    private void checkCrewAttendance(Attendance attendance) {
        String nickName = processNickNameInput(attendance);
        outputView.printCrewAttendanceHeader(nickName);

        AttendanceTimes crewAttendances = attendance.getAttendanceTimes(nickName);
        List<AttendanceTime> attendanceTimes = crewAttendances.getAttendanceTimes();
        attendanceTimes.sort(Comparator.comparing(AttendanceTime::getAttendanceDateTime));
        printCrewAttendances(attendance, attendanceTimes, nickName);
    }

    private void printCrewAttendances(Attendance attendance, List<AttendanceTime> attendanceTimes, String nickName) {
        for (AttendanceTime crewAttendance : attendanceTimes) {
            outputView.printCheckAttendanceMessage(crewAttendance.getAttendanceDateTime(), crewAttendance.getAttendanceStatus());
        }

        Map<AttendanceStatus, Integer> attendStatuses = attendance.getCrewAttendanceStatus(nickName);
        outputView.printCrewStatuses(attendStatuses);
    }

    private void checkExpelledCrew(Attendance attendance) {
        List<String> expelledCrews = attendance.checkExpelledCrew();
        expelledCrews.sort(Comparator.comparing(attendance::getAbsentCount)
                        .thenComparing(attendance::getLateCountForSort).reversed()
                        .thenComparing(name->name));

        outputView.printExpelledCrewHeader();
        for (String crew : expelledCrews) {
            outputView.printExpelledCrew(crew, attendance.getCrewAttendanceStatus(crew));
        }
    }
}
