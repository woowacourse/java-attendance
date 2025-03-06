package attendance.controller;

import attendance.domain.CrewHistories;

public interface Command {

    void execute(CrewHistories crewHistories);
}
