package controller;

import domain.Crews;

public interface AttendanceCommand {
    void execute(Crews crews);
}
