package controller.commands;

import domain.CrewGroup;

public interface Command {
    void execute(CrewGroup crewGroup);
}
