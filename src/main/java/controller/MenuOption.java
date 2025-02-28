package controller;

import controller.operator.AttendanceOperator;
import controller.operator.CrewAttendanceOperator;
import controller.operator.EditionOperator;
import controller.operator.ExpelledCrewOperator;
import controller.operator.OptionOperator;
import controller.operator.QuitOperator;
import domain.AttendanceBook;
import java.time.LocalDate;
import java.util.Arrays;

public enum MenuOption {

    REGISTER_ATTENDANCE("1", new AttendanceOperator()),
    EDIT_ATTENDANCE("2", new EditionOperator()),
    SHOW_CREW_ATTENDANCE("3", new CrewAttendanceOperator()),
    SHOW_EXPELLED_CREWS("4", new ExpelledCrewOperator()),
//    QUIT("Q", null)
    QUIT("Q", new QuitOperator())
    ;

    private final String command;
    private final OptionOperator optionOperator;

    MenuOption(String command, OptionOperator optionOperator) {
        this.command = command;
        this.optionOperator = optionOperator;
    }

    public static MenuOption findOptionByCommand(String input) {
        return Arrays.stream(MenuOption.values())
                .filter(option -> option.command.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 옵션입니다."));
    }

    public void process(AttendanceBook attendanceBook, LocalDate attendanceDate) {
        if (!this.equals(MenuOption.QUIT)) {
            this.optionOperator.process(attendanceBook, attendanceDate);
        }
    }
}
