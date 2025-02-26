package controller.command;

import domain.model.CrewHistories;

public interface Command {

    void execute(CrewHistories crewHistories);
}
