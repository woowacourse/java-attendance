package controller;

import java.util.Arrays;

public enum Command {
    ATTEND("1"),
    EDIT("2"),
    FIND_CREW_RECORD("3"),
    FIND_WARNING_CREWS("4"),
    EXIT("Q");

    private final String controlCommand;

    Command(String controlCommand) {
        this.controlCommand = controlCommand;
    }

    public static Command convertToCommand(String commandLine){
        return Arrays.stream(values())
                .filter(command -> command.getControlCommand().equals(commandLine.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 옳바르지 않은 명령어 입니다."));
    }

    public String getControlCommand() {
        return controlCommand;
    }
}
