import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceSystemHandler {
    private final AttendanceSystemManager attendanceSystemManager;
    private final Crews crews;
    private final AttendanceHistories attendanceHistories;

    private final Map<FunctionOption, Runnable> ACTION_FOR_OPTION = Map.of(
            FunctionOption.REGISTER_ATTENDANCE, this::registerAttendance,
            FunctionOption.UPDATE_ATTENDANCE, this::updateAttendance,
            FunctionOption.CHECK_ATTENDANCE_HISTORY_OF_CREW, this::checkAttendanceHistoryOfCrew,
            FunctionOption.CHECK_EXPULSION_CANDIDATES, this::checkExpulsionCandidates
    );

    public AttendanceSystemHandler(AttendanceSystemManager attendanceSystemManager, Crews crews,
                                   AttendanceHistories attendanceHistories) {
        this.attendanceSystemManager = attendanceSystemManager;
        this.crews = crews;
        this.attendanceHistories = attendanceHistories;
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
            FunctionOption functionOption = getFunctionOption();

            if (functionOption == FunctionOption.QUIT) {
                break;
            }

            ACTION_FOR_OPTION.get(functionOption).run();
        }
    }

    private void registerAttendance() {
        Crew crew = getRequestedCrew();
        LocalDateTime attendAt = LocalDateTime.of(getDateOfToday(), readAttendanceTime());

        AttendanceHistory attendanceHistory = attendanceSystemManager.registerNewAttendance(crew, attendAt);
        OutputView.printRegisteredHistory(attendanceHistory);
    }

    private void updateAttendance() {
        Crew crew = getRequestedCrewToUpdate();
        LocalDate requestedDate = readAttendanceDateToUpdate();

        LocalDateTime newAttendanceAt = LocalDateTime.of(requestedDate, readAttendanceTimeToUpdate());

        AttendanceHistory oldHistory = attendanceHistories.findByCrewAndDate(crew, requestedDate);
        AttendanceHistory newHistory = attendanceSystemManager.updateRegisteredAttendance(oldHistory, crew,
                newAttendanceAt);

        OutputView.printUpdatedHistory(oldHistory, newHistory);
    }

    private void checkAttendanceHistoryOfCrew() {
        Crew crew = getRequestedCrew();
        LocalDate date = getDateOfToday();

        Map<LocalDateTime, AttendanceType> historiesOfCrew = attendanceSystemManager.findAllHistoriesOfCrew(crew, date);
        OutputView.printAttendanceHistories(crew, historiesOfCrew);

        PenaltyResultOfCrew penaltyResultOfCrew = attendanceSystemManager.getPenaltyResultOfCrew(crew, historiesOfCrew);
        OutputView.printPenaltyResultOfCrew(penaltyResultOfCrew);
    }

    private void checkExpulsionCandidates() {
        LocalDate date = getDateOfToday();

        List<PenaltyResultOfCrew> expulsionCandidates = attendanceSystemManager.findExpulsionCandidates(date);
        OutputView.printExpulsionCandidates(expulsionCandidates);
    }

    private LocalDate getDateOfToday() {
        return LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
    }

    private LocalDate getDateOfRequestedDate(int date) {
        return LocalDate.of(2024, 12, date);
    }

    private LocalTime readAttendanceTime() {
        String rawAttendanceTime = InputView.readAttendanceTime();
        return InputParser.parseTime(rawAttendanceTime);
    }

    private LocalTime readAttendanceTimeToUpdate() {
        String rawNewAttendanceTime = InputView.readNewAttendanceTime();
        return InputParser.parseTime(rawNewAttendanceTime);
    }

    private LocalDate readAttendanceDateToUpdate() {
        String rawRequestDate = InputView.readUpdateRequestDate();
        int requestDate = InputParser.parseInteger(rawRequestDate);
        return getDateOfRequestedDate(requestDate);
    }

    private Crew getRequestedCrew() {
        String name = InputView.readName();
        return crews.findCrewByName(name);
    }

    private Crew getRequestedCrewToUpdate() {
        String name = InputView.readNameToUpdate();
        return crews.findCrewByName(name);
    }

    private FunctionOption getFunctionOption() {
        String optionSign = InputView.readOption();
        return FunctionOption.findBySign(optionSign);
    }
}
