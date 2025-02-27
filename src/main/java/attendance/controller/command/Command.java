package attendance.controller.command;

import attendance.domain.CrewHistories;

public interface Command {

    void execute(CrewHistories crewHistories);
}
