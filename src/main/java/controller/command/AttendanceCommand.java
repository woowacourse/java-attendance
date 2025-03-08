package controller.command;

import domain.crew.Crews;

public interface AttendanceCommand {
    void execute(Crews crews);
}
