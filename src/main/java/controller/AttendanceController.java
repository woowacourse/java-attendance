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
import java.util.List;
import java.util.Map;
import util.AttendancesFileHandler;
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

        outputView.printMenuHeader(nowDate);
        String option = inputView.readOption(Arrays.asList(MenuOption.values()));

        process(option, attendance, nowDate);
    }

    private void process(String option, Attendance attendance, LocalDate nowDate) {
        if (option.equals(MenuOption.ATTENDANCE_CHECK.getCommand())) {
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
        String nickName = getNickName(attendance);
        LocalTime arrivalTime = inputView.readArrivalTime();
        attendance.attend(nickName, LocalDateTime.of(nowDate, arrivalTime));

        LocalDateTime attendanceDateTime = attendance.getAttendanceDateTime(nickName, nowDate);
        AttendanceStatus attendanceStatus = attendance.getAttendanceStatus(nickName, nowDate);

        outputView.printCheckAttendanceMessage(attendanceDateTime, attendanceStatus);
    }

    private String getNickName(Attendance attendance) {
        return repeatExecutor.repeatUntilSuccess(() -> {
            String nickName = inputView.readNickname();
            attendance.validateNickName(nickName);
            return nickName;
        });
    }

    private void editAttendance(Attendance attendance) {
        String nickName = inputView.readEditNickname();
        int editArrivalDate = inputView.readEditArrivalDate();
        LocalTime editArrivalTime = inputView.readEditArrivalTime();

        LocalDate editDate = LocalDate.of(2024, 12, editArrivalDate);
        AttendanceTime oldAttendanceTime = attendance.findAttendanceTime(nickName, editDate);
        attendance.edit(nickName, editArrivalDate, editArrivalTime);
        AttendanceTime newAttendanceTime = attendance.findAttendanceTime(nickName, editDate);

        outputView.printEditAttendanceMessage(oldAttendanceTime, newAttendanceTime);
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
        outputView.printExpelledCrewHeader();
        for (String crew : expelledCrews) {
            outputView.printExpelledCrew(crew, attendance.countAttendanceStatus(crew));
        }
    }
}
