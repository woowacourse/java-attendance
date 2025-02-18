package attendance.controller;

import attendance.model.Crew;
import attendance.model.CrewDataLoader;
import attendance.model.Crews;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final static Crews crews = new Crews();

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        CrewDataLoader crewDataLoader = new CrewDataLoader(crews);
        crewDataLoader.load("attendances.csv");
        String s = inputView.inputCommand();
        if (s.equals("3")) {
           String crewName = inputView.inputCrewName();
           outputView.printAttendanceHistory(crews.findCrew(new Crew(crewName)).get());

        }
    }
}
