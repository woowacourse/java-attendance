package controller.command;

import java.time.LocalDate;

public interface ControllerCommand {

    void execute(LocalDate today);
}
