import controller.AttendanceApplication;
import controller.AttendanceCheckExecutor;
import controller.AttendanceHandler;
import controller.AttendanceUpdateExecutor;
import controller.CrewAttendanceRetrieveExecutor;
import controller.QuitExecutor;
import controller.MenuExecutor;
import controller.PenaltyRetrieveExecutor;
import domain.AttendanceManager;
import domain.AttendanceStatusCalculator;
import domain.CrewAttendanceBook;
import domain.CrewPenaltyManager;
import java.util.Map;
import util.ApplicationDate;
import util.AttendanceApplicationDate;
import util.DataInitializer;
import util.FileLoader;
import util.StringParser;
import view.ConsoleView;
import view.InputView;
import view.Menu;
import view.OutputView;
import view.parser.InputParser;
import view.parser.OutputParser;

public final class AttendanceConfig {

    public AttendanceApplication attendanceApplication(CrewAttendanceBook initCrewAttendanceBook) {
        return new AttendanceApplication(attendanceHandler(initCrewAttendanceBook), consoleView(), applicationTime());
    }

    public DataInitializer dataInitializer() {
        return new DataInitializer(new FileLoader(), new StringParser(), attendanceManager(CrewAttendanceBook.createEmpty()));
    }

    private AttendanceHandler attendanceHandler(CrewAttendanceBook initCrewAttendanceBook) {
        return new AttendanceHandler(initExecutor(initCrewAttendanceBook));
    }

    private ConsoleView consoleView() {
        return ConsoleView.create(new InputView(), new OutputView(), new InputParser(), new OutputParser());
    }

    private ApplicationDate applicationTime() {
        return AttendanceApplicationDate.getInstance();
    }

    private Map<Menu, MenuExecutor> initExecutor(CrewAttendanceBook initCrewAttendanceBook) {
        return Map.of(
                Menu.CHECK_ATTENDANCE, attendanceCheckExecutor(initCrewAttendanceBook),
                Menu.UPDATE_ATTENDANCE, attendanceUpdateExecutor(initCrewAttendanceBook),
                Menu.CREW_ATTENDANCE_RECORD, crewAttendanceRetrieveExecutor(initCrewAttendanceBook),
                Menu.PENALTY_RECORD, penaltyRetrieveExecutor(initCrewAttendanceBook),
                Menu.QUIT, quitExecutor()
        );
    }

    private AttendanceManager attendanceManager(CrewAttendanceBook crewAttendanceBook) {
        return new AttendanceManager(crewAttendanceBook);
    }

    private AttendanceStatusCalculator attendanceStatusCalculator() {
        return new AttendanceStatusCalculator();
    }

    private CrewPenaltyManager crewPenaltyManager() {
        return new CrewPenaltyManager(attendanceStatusCalculator());
    }

    private MenuExecutor attendanceCheckExecutor(CrewAttendanceBook initCrewAttendanceBook) {
        return new AttendanceCheckExecutor(consoleView(), applicationTime(), attendanceManager(initCrewAttendanceBook));
    }

    private MenuExecutor attendanceUpdateExecutor(CrewAttendanceBook initCrewAttendanceBook) {
        return new AttendanceUpdateExecutor(consoleView(), applicationTime(), attendanceManager(initCrewAttendanceBook));
    }

    private MenuExecutor crewAttendanceRetrieveExecutor(CrewAttendanceBook initCrewAttendanceBook) {
        return new CrewAttendanceRetrieveExecutor(consoleView(), applicationTime(), attendanceManager(initCrewAttendanceBook), attendanceStatusCalculator());
    }

    private MenuExecutor penaltyRetrieveExecutor(CrewAttendanceBook initCrewAttendanceBook) {
        return new PenaltyRetrieveExecutor(consoleView(), applicationTime(), attendanceManager(initCrewAttendanceBook), crewPenaltyManager());
    }

    private MenuExecutor quitExecutor() {
        return new QuitExecutor();
    }
}
