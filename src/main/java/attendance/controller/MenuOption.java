package attendance.controller;

import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class MenuOption {
    protected static final LocalDate LOCAL_DATE_TODAY = LocalDate.now();

    protected final InputView inputView;
    protected final OutputView outputView;
    protected final Crews crews;
    protected final Attendances attendances;

    public MenuOption(InputView inputView, OutputView outputView, Crews crews, Attendances attendances) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crews = crews;
        this.attendances = attendances;
    }

    public abstract void executeMenuOption(MenuCommand command);

    protected LocalDateTime createLocalDateTime(final LocalDate localDate, final String localTime) {
        return LocalDateTime.of(localDate, LocalTime.parse(localTime));
    }

    protected Crew findCrewByCrewName() {
        String crewName = inputView.readCrewName();
        return crews.findCrew(crewName);
    }
}
