import console.AttendanceSystemConsole;
import console.FunctionOption;
import console.InputView;
import console.OutputView;
import crew.Crew;
import crew.Crews;
import history.AttendanceHistories;
import history.AttendanceHistory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import type.AttendanceType;
import type.PenaltyResultOfCrew;

public class AttendanceWorkflow {
    private final AttendanceProcessor attendanceProcessor;
    private final Crews crews;
    private final AttendanceHistories attendanceHistories;
    private final AttendanceSystemConsole console;

    private final Map<FunctionOption, Runnable> ACTION_FOR_OPTION = Map.of(
            FunctionOption.REGISTER_ATTENDANCE, this::registerAttendance,
            FunctionOption.UPDATE_ATTENDANCE, this::updateAttendance,
            FunctionOption.CHECK_ATTENDANCE_HISTORY_OF_CREW, this::checkAttendanceHistoryOfCrew,
            FunctionOption.CHECK_EXPULSION_CANDIDATES, this::checkExpulsionCandidates
    );

    public AttendanceWorkflow(AttendanceProcessor attendanceProcessor, Crews crews,
                              AttendanceHistories attendanceHistories,
                              AttendanceSystemConsole console) {
        this.attendanceProcessor = attendanceProcessor;
        this.crews = crews;
        this.attendanceHistories = attendanceHistories;
        this.console = console;
    }

    public void run() {
        try {
            processAttendanceSystem();
        } catch (Exception e) {
            OutputView.printErrorMessage(e.getMessage());
        }
    }

    private void processAttendanceSystem() {
        while (true) {
            FunctionOption functionOption = console.getFunctionOption();

            if (functionOption == FunctionOption.QUIT) {
                break;
            }

            ACTION_FOR_OPTION.get(functionOption).run();
        }
    }

    private void registerAttendance() {
        Crew crew = getRequestedCrew();
        LocalDateTime attendAt = LocalDateTime.of(console.getDateOfToday(), console.readAttendanceTime());

        AttendanceHistory attendanceHistory = attendanceProcessor.registerNewHistory(crew, attendAt);
        OutputView.printRegisteredHistory(attendanceHistory);
    }

    private void updateAttendance() {
        Crew crew = getRequestedCrewToUpdate();
        LocalDate requestedDate = console.readAttendanceDateToUpdate();

        LocalDateTime newAttendanceAt = LocalDateTime.of(requestedDate, console.readAttendanceTimeToUpdate());

        AttendanceHistory oldHistory = attendanceHistories.findByCrewAndDate(crew, requestedDate);
        AttendanceHistory newHistory = attendanceProcessor.updateRegisteredHistory(oldHistory, crew,
                newAttendanceAt);

        OutputView.printUpdatedHistory(oldHistory, newHistory);
    }

    private void checkAttendanceHistoryOfCrew() {
        Crew crew = getRequestedCrew();
        LocalDate date = console.getDateOfToday();

        Map<LocalDateTime, AttendanceType> historiesOfCrew = attendanceProcessor.findAllHistoriesOfCrew(crew, date);
        OutputView.printAttendanceHistories(crew, historiesOfCrew);

        PenaltyResultOfCrew penaltyResultOfCrew = attendanceProcessor.getPenaltyResultOfCrew(crew, historiesOfCrew);
        OutputView.printPenaltyResultOfCrew(penaltyResultOfCrew);
    }

    private void checkExpulsionCandidates() {
        LocalDate date = console.getDateOfToday();

        List<PenaltyResultOfCrew> expulsionCandidates = attendanceProcessor.findExpulsionCandidates(date);
        OutputView.printExpulsionCandidates(expulsionCandidates);
    }

    private Crew getRequestedCrew() {
        String name = InputView.readName();
        return crews.findCrewByName(name);
    }

    private Crew getRequestedCrewToUpdate() {
        String name = InputView.readNameToUpdate();
        return crews.findCrewByName(name);
    }
}
