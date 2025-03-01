package controller.command;

import domain.AttendanceBook;

public interface ControllerCommand {

    void execute(AttendanceBook book);
}
