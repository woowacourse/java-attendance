package controller;

import domain.*;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceController {

    private static final String EXIT = "Q";

    private final InputView inputView;
    private final OutputView outputView;
    private final Attendances attendances;
    private final Map<Command, Runnable> commandOption = Map.of(
            Command.ATTENDANCE_REGISTER, this::registerAttendance,
            Command.ATTENDANCE_UPDATE, this::updateAttendance,
            Command.ATTENDANCE_CHECK, this::identifyAttendanceLogsWithCrew,
            Command.PENALTY_CHECK, this::identifyPenaltyCrews
    );

    public AttendanceController(InputView inputView, OutputView outputView, Attendances attendances) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendances = attendances;
    }

    public void run() {
        attendances.recordAllAbsences();
        String command = "";
        while (!EXIT.equals(command)) {
            outputView.printOptionMessage();
            command = inputView.readOptionNumber();
            runCommand(command);
        }
    }

    public void runCommand(String inputOption) {
        Command command = Command.identify(inputOption);
        commandOption.get(command).run();
    }

    private void registerAttendance() {
        Nickname nickname = attendances.checkCrewName(new Nickname(inputView.readNickname()));
        LocalTime attendanceTime = inputView.readAttendanceTime();
        LocalDateTime localDateTime = LocalDateTime.of(TimeMachine.dateOfNow(), attendanceTime);
        attendances.addAttendanceLog(nickname, localDateTime);
        outputView.printAttendanceCheckMessage(localDateTime);
    }

    private void updateAttendance() {
        Nickname nickname = attendances.checkCrewName(new Nickname(inputView.readUpdateNickname()));
        int updateDayOfMonth = inputView.readUpdateDayOfMonth();
        validateDayOfMonth(updateDayOfMonth);
        LocalDate updateDate = getUpdateDate(updateDayOfMonth);

        Attendance originalAttendance = attendances.findLogWithNameAndDate(nickname, updateDate);
        LocalDateTime originalDateTime = originalAttendance.getLocalDateTime();

        LocalDateTime updateDateTime = LocalDateTime.of(updateDate, inputView.readUpdateAttendanceTime());
        attendances.updateAttendance(nickname, updateDateTime);
        outputView.printUpdatedAttendanceMessage(originalDateTime, updateDateTime);
    }

    private LocalDate getUpdateDate(int updateDayOfMonth) {
        LocalDate today = TimeMachine.dateOfNow();
        return LocalDate.of(today.getYear(), today.getMonthValue(), updateDayOfMonth);
    }

    private void identifyAttendanceLogsWithCrew() {
        Nickname nickname = attendances.checkCrewName(new Nickname(inputView.readNickname()));
        outputView.printAttendanceLogsWithCrew(nickname, attendances);

    }

    private void identifyPenaltyCrews() {
        outputView.printPenaltyCrews(attendances);
    }

    private void validateDayOfMonth(int updateDate) {
        LocalDate today = TimeMachine.dateOfNow();
        int endDayOfMonth = today.lengthOfMonth();
        if (1 > updateDate || endDayOfMonth < updateDate) {
            throw new IllegalArgumentException("[ERROR] 수정 일자가 해당 월의 일자 범위를 벗어납니다.");
        }
    }
}
