package attendance.controller.option;

import attendance.domain.Attendances;
import attendance.domain.CrewNames;
import attendance.view.InputView;
import attendance.view.OutputView;

public abstract class MenuOption {
    protected final InputView inputView;
    protected final OutputView outputView;
    protected final CrewNames crewNames;
    protected final Attendances attendances;

    public MenuOption(InputView inputView, OutputView outputView, CrewNames crewNames, Attendances attendances) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewNames = crewNames;
        this.attendances = attendances;
    }

    public abstract void execute();
}
