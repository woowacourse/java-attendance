package attendance.controller.command;

import attendance.domain.model.CrewHistories;

public interface Command {

    void execute(CrewHistories crewHistories);
}
