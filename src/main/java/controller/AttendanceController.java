package controller;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.MenuOption;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
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

    public void run() throws IOException {
        LocalDate nowDate = LocalDate.now();
        Attendance attendance = new Attendance(AttendancesFileHandler.generateAttendances(), nowDate);

        while (true) {
            if(getOption(attendance, nowDate).equals(MenuOption.QUIT.getCommand())) {
                break;
            }
        }
    }

    private void process(String option, Attendance attendance, LocalDate nowDate) {
        if (option.equals(MenuOption.ATTENDANCE_CHECK.getCommand())) {
            validateCampusOpenDate(attendance, nowDate);
            checkAttendance(attendance, nowDate);
        } else if (option.equals(MenuOption.ATTENDANCE_CORRECTION.getCommand())) {
            editAttendance(attendance);
        } else if (option.equals(MenuOption.CREW_ATTENDANCE_CHECK.getCommand())) {
            checkCrewAttendance(attendance);
        } else if (option.equals(MenuOption.CHECK_EXPELLED_CREW.getCommand())) {
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

        AttendanceTime oldAttendanceTime = repeatExecutor.repeatUntilSuccess(() -> {
            int editArrivalDate = getEditArrivalDate();
            LocalDate editDate = LocalDate.of(2024, 12, editArrivalDate);
            return attendance.findAttendanceTime(nickName, editDate);
        });

        int editArrivalDate = oldAttendanceTime.getAttendanceDateTime().getDayOfMonth();
        LocalDate editDate = LocalDate.of(2024, 12, editArrivalDate);

        repeatExecutor.repeatUntilSuccess(() -> {
            LocalTime editArrivalTime = inputView.readEditArrivalTime();
            attendance.edit(nickName, editArrivalDate, editArrivalTime);
            return null;
        });

        AttendanceTime newAttendanceTime = attendance.findAttendanceTime(nickName, editDate);

        outputView.printEditAttendanceMessage(oldAttendanceTime, newAttendanceTime);
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

        List<AttendanceTime> crewAttendances = attendance.getAttendanceTimes(nickName);
        for (AttendanceTime crewAttendance : crewAttendances) {
            outputView.printCheckAttendanceMessage(crewAttendance.getAttendanceDateTime(), crewAttendance.getAttendanceStatus());
        }

        Map<AttendanceStatus, Integer> attendStatuses = attendance.countAttendanceStatus(nickName);
        outputView.printCrewStatuses(attendStatuses);
    }

    private void checkExpelledCrew(Attendance attendance) {
        List<String> expelledCrews = attendance.checkExpelledCrew();

        expelledCrews.sort(Comparator.comparing(attendance::getAbsentCount)
                        .thenComparing(attendance::getLateCount).reversed()
                        .thenComparing(name->name));

        Map<String, Map<AttendanceStatus, Integer>> expelledResult = new HashMap<>();
        for (String expelledCrew : expelledCrews) {
            expelledResult.put(expelledCrew, attendance.countAttendanceStatus(expelledCrew));
        }

        outputView.printExpelledCrewHeader();
        for (String crew : expelledCrews) {
            outputView.printExpelledCrew(crew, attendance.countAttendanceStatus(crew));
        }
    }
}
