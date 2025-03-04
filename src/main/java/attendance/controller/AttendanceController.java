package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceLocalTime;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.domain.NullableLocalTime;
import attendance.domain.WarningLevel;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

public class AttendanceController {
    private final Map<AttendanceCommand, Runnable> commands;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceManager attendanceManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        commands = initCommands(attendanceManager);
    }

    public Map<AttendanceCommand, Runnable> initCommands(AttendanceManager attendanceManager) {
        Map<AttendanceCommand, Runnable> commands = new EnumMap<>(AttendanceCommand.class);
        commands.put(AttendanceCommand.CHECK_ATTENDANCE, () -> checkAttendance(attendanceManager));
        commands.put(AttendanceCommand.MODIFY_ATTENDANCE, () -> modifyAttendance(attendanceManager));
        commands.put(AttendanceCommand.VIEW_ATTENDANCE_RECORD, () -> viewAttendanceRecord(attendanceManager));
        commands.put(AttendanceCommand.VIEW_WARNING_CREWS, () -> viewWarningCrews(attendanceManager));
        return commands;
    }

    public void run() {
        while (true) {
            AttendanceCommand command = inputCommand();
            if (command == AttendanceCommand.EXIT) {
                break;
            }
            execute(command);
        }
        inputView.close();
    }

    private AttendanceCommand inputCommand() {
        try {
            return AttendanceCommand.from(inputView.inputCommand());
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return inputCommand();
        }
    }

    private void execute(AttendanceCommand command) {
        try {
            commands.get(command).run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
        }
    }

    private void checkAttendance(AttendanceManager attendanceManager) {
        String crewName = inputView.inputCrewName();
        attendanceManager.validateExistCrew(crewName);
        LocalTime enterTime = inputView.inputEnterTime();
        attendanceManager.attend(crewName, enterTime);

        outputView.printCheckAttendanceResult(new AttendanceLocalTime(enterTime));
    }

    private void modifyAttendance(AttendanceManager attendanceManager) {
        String crewName = inputView.inputModifyCrewName();
        attendanceManager.validateExistCrew(crewName);
        LocalDate modifyDate = inputView.inputModifyDate();
        LocalTime modifyTime = inputView.inputModifyTime();
        NullableLocalTime prevTime = attendanceManager.modify(crewName, modifyDate, modifyTime);

        outputView.printModifyAttendanceResult(prevTime, modifyDate, new AttendanceLocalTime(modifyTime));
    }

    private void viewAttendanceRecord(AttendanceManager attendanceManager) {
        String crewName = inputView.inputCrewName();
        Map<LocalDate, Attendance> crewAttendances = attendanceManager.getCrewAttendances(crewName);
        Map<AttendanceStatus, Integer> statusCounts = attendanceManager.calculateStatusCount(crewName);
        WarningLevel level = attendanceManager.calculateCrewWarningLevel(crewName);

        outputView.printAttendanceRecords(crewName, crewAttendances);
        outputView.printAttendanceStatusCount(statusCounts);
        outputView.printCrewWarningLevel(level);
    }

    private void viewWarningCrews(AttendanceManager attendanceManager) {
        Map<String, Map<AttendanceStatus, Integer>> crewsStatusCount = attendanceManager.getCrewsStatusCount();
        outputView.printWarningCrews(crewsStatusCount);
    }
}
