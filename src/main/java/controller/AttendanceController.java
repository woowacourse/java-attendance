package controller;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.AttendanceTimes;
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
            option = getOption(attendance, nowDate);
        } while (!option.equals(MenuOption.QUIT.getCommand()));
    }

    private void process(String option, Attendance attendance, LocalDate nowDate) {
        processAttendanceCheck(option, attendance, nowDate);
        processAttendanceCorrection(option, attendance);
        processCrewAttendanceCheck(option, attendance);
        processCheckExpelledCrew(option, attendance);
    }

    private void processAttendanceCheck(String option, Attendance attendance, LocalDate nowDate) {
        if (option.equals(MenuOption.ATTENDANCE_CHECK.getCommand())) {
            validateCampusOpenDate(attendance, nowDate);
            checkAttendance(attendance, nowDate);
        }
    }

    private void processAttendanceCorrection(String option, Attendance attendance) {
        if (option.equals(MenuOption.ATTENDANCE_CORRECTION.getCommand())) {
            editAttendance(attendance);
        }
    }

    private void processCrewAttendanceCheck(String option, Attendance attendance) {
        if (option.equals(MenuOption.CREW_ATTENDANCE_CHECK.getCommand())) {
            checkCrewAttendance(attendance);
        }
    }

    private void processCheckExpelledCrew(String option, Attendance attendance) {
        if (option.equals(MenuOption.CHECK_EXPELLED_CREW.getCommand())) {
            checkExpelledCrew(attendance);
        }
    }

    private void checkAttendance(Attendance attendance, LocalDate nowDate) {
        String nickName = getCheckNickName(attendance);
        repeatExecutor.repeatUntilSuccess(() -> {
            LocalTime arrivalTime = getLocalTime();
            attendance.attend(nickName, LocalDateTime.of(nowDate, arrivalTime));
            return null;
        });

        LocalDateTime attendanceDateTime = attendance.getAttendanceDateTime(nickName, nowDate);
        AttendanceStatus attendanceStatus = attendance.getAttendanceStatus(nickName, nowDate);
        outputView.printCheckAttendanceMessage(attendanceDateTime, attendanceStatus);
    }

    private String getOption(Attendance attendance, LocalDate nowDate) {
        return repeatExecutor.repeatUntilSuccess(() -> {
            outputView.printMenuHeader(nowDate);
            String option = inputView.readOption(Arrays.asList(MenuOption.values()));
            MenuOption.validateCommandExist(option);
            process(option, attendance, nowDate);
            return option;
        });
    }

    private void validateCampusOpenDate(Attendance attendance, LocalDate nowDate) {
        if (attendance.isClosed(nowDate)) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s요일은 등교일이 아닙니다.", nowDate.getMonthValue(), nowDate.getDayOfMonth(),
                    Convertor.convertDayOfWeekToKorean(nowDate.getDayOfWeek())));
        }
    }

    private String getCheckNickName(Attendance attendance) {
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
            int editArrivalDate = getEditArrivalDate();
            LocalDate editDate = LocalDate.of(2024, 12, editArrivalDate);
            return attendance.findAttendanceTime(nickName, editDate);
        });
    }

    private void editAttendanceTime(Attendance attendance, String nickName, int editArrivalDate) {
        repeatExecutor.repeatUntilSuccess(() -> {
            LocalTime editArrivalTime = inputView.readEditArrivalTime();
            attendance.edit(nickName, editArrivalDate, editArrivalTime);
            return null;
        });
    }

    private int getEditArrivalDate() {
        int day = inputView.readEditArrivalDate();
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("[ERROR] 유효한 날짜가 아닙니다.");
        }
        return day;
    }

    private void checkCrewAttendance(Attendance attendance) {
        String nickName = inputView.readNickname();
        outputView.printCrewAttendanceHeader(nickName);

        AttendanceTimes crewAttendances = attendance.getAttendanceTimes(nickName);
        List<AttendanceTime> attendanceTimes = crewAttendances.getAttendanceTimes();
        attendanceTimes.sort(Comparator.comparing(AttendanceTime::getAttendanceDateTime));
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
