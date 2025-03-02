package controller;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    ATTEND("1", AttendanceController::attendCommand),
    EDIT("2", AttendanceController::editCrewAttendance),
    FIND_CREW_RECORD("3",AttendanceController::findCrewCommand),
    FIND_WARNING_CREWS("4",AttendanceController::findWarningCrews),
    EXIT("Q", Optional::empty);

    private final String controlCommand;
    private final Runnable action;

    Command(String controlCommand, Runnable action) {
        this.controlCommand = controlCommand;
        this.action = action;
    }

    public static Command convertToCommand(String commandLine){
        return Arrays.stream(values())
                .filter(command -> command.getControlCommand().equals(commandLine.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 명령어 입니다."));
    }

    public void execute(){
        action.run();;
    }

    public String getControlCommand() {
        return controlCommand;
    }
}
