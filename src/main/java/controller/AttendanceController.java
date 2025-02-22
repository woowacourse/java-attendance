package controller;

import domain.UpdatedAttendanceSnapshot;
import domain.constants.AnswerCommand;
import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceSystem;
import domain.AttendanceSystemFactory;
import domain.Crew;
import domain.constants.ExpulsionStatus;
import domain.constants.UserCommand;
import dto.AttendanceResponse;
import dto.ExpulsionCrewResponse;
import dto.UpdatedAttendanceSnapshotResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import util.LoopTemplate;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceSystemFactory attendanceSystemFactory;

    public AttendanceController(final InputView inputView, final OutputView outputView,
                                final AttendanceSystemFactory attendanceSystemFactory) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceSystemFactory = attendanceSystemFactory;
    }

    public void run() {
        final AttendanceSystem attendanceSystem = attendanceSystemFactory.createAttendanceSystem();
        retryUntilOperationQuit(attendanceSystem);
    }

    public UserCommand selectOperation(final AttendanceSystem attendanceSystem) {
        outputView.printToday(attendanceSystem.today());
        outputView.printIntroduceOperation();
        final UserCommand userCommand = inputView.readChoiceOperation();
        switch (userCommand) {
            case ADD_ATTENDANCE -> addAttendance(attendanceSystem);
            case UPDATE_ATTENDANCE -> updateAttendance(attendanceSystem);
            case LOOKUP_CREW_ATTENDANCE -> responseCrewAttendanceHistory(attendanceSystem);
            case LOOKUP_EXPULSION_CREWS -> responseExpulsionCrews(attendanceSystem);
        }
        return userCommand;
    }

    private void retryUntilOperationQuit(final AttendanceSystem attendanceSystem) {
        while (selectOperation(attendanceSystem) != UserCommand.QUIT) {
        }
    }

    private void addAttendance(final AttendanceSystem attendanceSystem) {
        if (attendanceSystem.isNotAttendanceDay()) {
            outputView.printNotAttendanceDay();
            return;
        }
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewName, attendanceSystem, outputView);
        if (attendanceSystem.isAlreadyTodayAttendance(crewName)) {
            updateAttendanceForDuplicateAttendance(crewName, attendanceSystem);
            return;
        }
        final Attendance attendance = LoopTemplate.tryCatchLoop(this::attendance, crewName, attendanceSystem
                , outputView);
        final AttendanceResponse attendanceResponse = convertAttendanceToResponse(attendance);
        outputView.printCrewAttendances(List.of(attendanceResponse));


    }

    private void updateAttendanceForDuplicateAttendance(final String crewName,
                                                        final AttendanceSystem attendanceSystem) {
        outputView.printIntroduceAnswerCommand();
        final AnswerCommand answerCommand = inputView.readAnswerCommand();
        if (answerCommand == AnswerCommand.YES) {
            updateAttendance(attendanceSystem);
        }
    }

    private void updateByDateAndCrewName(final String crewName, final AttendanceSystem attendanceSystem) {
        final int dayOfMonth = LoopTemplate.tryCatchLoop(this::inputDayOfMonthForUpdate, crewName, attendanceSystem,
                outputView);
        final LocalTime targetTime = LoopTemplate.tryCatchLoop(this::inputUpdateTime, outputView);
        final UpdatedAttendanceSnapshot updatedAttendanceSnapshot =
                attendanceSystem.updateAttendanceByCrewNameAndDay(targetTime, crewName, dayOfMonth);
        outputView.printUpdateAttendanceResult(
                convertUpdatedAttendanceSnapshotToResponse(updatedAttendanceSnapshot));
    }

    private Attendance attendance(final String crewName, final AttendanceSystem attendanceSystem) {
        outputView.printAddAttendanceDate();
        final LocalTime attendanceTime = inputView.readTime();
        final Attendance attendance = attendanceSystem.attendance(crewName, attendanceTime);
        return attendance;
    }

    private String inputCrewName(final AttendanceSystem attendanceSystem) {
        outputView.printAddAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceSystem.validateCrewByName(crewName);
        return crewName;
    }

    private void updateAttendance(final AttendanceSystem attendanceSystem) {
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewNameForUpdate, attendanceSystem, outputView);
        updateByDateAndCrewName(crewName, attendanceSystem);
    }

    private void responseCrewAttendanceHistory(final AttendanceSystem attendanceSystem) {
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewName, attendanceSystem, outputView);
        outputView.printAttendanceHistoryTitle(crewName);
        outputAttendances(attendanceSystem, crewName);
        outputRiskOfAttendancesCount(attendanceSystem, crewName);
        outputRiskOfExpulsionStatus(attendanceSystem, crewName);

    }

    private void outputRiskOfExpulsionStatus(final AttendanceSystem attendanceSystem, final String crewName) {
        final ExpulsionStatus expulsionStatus = attendanceSystem.calculateExpulsionStatusByCrew(crewName);
        outputView.printCrewExpulsionStatus(expulsionStatus);
    }

    private void outputRiskOfAttendancesCount(final AttendanceSystem attendanceSystem, final String crewName) {
        final Map<AttendanceStatus, Integer> attendanceStatistics =
                attendanceSystem.calculateAttendanceStatisticsByCrew(crewName);
        outputView.printAttendancesStatistics(attendanceStatistics);
    }

    private void outputAttendances(final AttendanceSystem attendanceSystem, final String crewName) {
        final List<AttendanceResponse> attendanceResponses =
                convertAttendancesToResponses(attendanceSystem.getAttendancesByCrew(crewName));
        outputView.printCrewAttendances(attendanceResponses);
    }

    private void responseExpulsionCrews(final AttendanceSystem attendanceSystem) {
        final List<Crew> crews = attendanceSystem.calculateRiskOfExpulsionCrews();
        outputView.printExpulsionCrewResponses(convertExpulsionCrewResponses(crews));
    }

    private LocalTime inputUpdateTime() {
        outputView.printUpdateAttendanceDate();
        final LocalTime targetTime = inputView.readTime();
        return targetTime;
    }

    private int inputDayOfMonthForUpdate(final String crewName, final AttendanceSystem attendanceSystem) {
        outputView.printUpdateAttendanceDayOfMonth();
        final int dayOfMonth = inputView.readDayOfMonth();
        attendanceSystem.validateUpdateAttendanceDay(crewName, dayOfMonth);
        return dayOfMonth;
    }

    private String inputCrewNameForUpdate(final AttendanceSystem attendanceSystem) {
        outputView.printUpdateAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceSystem.validateCrewByName(crewName);
        return crewName;
    }

    private UpdatedAttendanceSnapshotResponse convertUpdatedAttendanceSnapshotToResponse(
            final UpdatedAttendanceSnapshot updatedAttendanceSnapshot
    ) {
        return new UpdatedAttendanceSnapshotResponse(
                convertAttendanceToResponse(updatedAttendanceSnapshot.getBefore()),
                convertAttendanceToResponse(updatedAttendanceSnapshot.getAfter())
        );
    }


    private List<AttendanceResponse> convertAttendancesToResponses(final List<Attendance> attendances) {
        return attendances.stream()
                .map(this::convertAttendanceToResponse)
                .toList();
    }

    private AttendanceResponse convertAttendanceToResponse(final Attendance attendance) {
        return new AttendanceResponse(attendance.getDateTime(), attendance.calculateStatus(), attendance.isEmpty());
    }

    private List<ExpulsionCrewResponse> convertExpulsionCrewResponses(final List<Crew> crews) {
        return crews.stream()
                .map(crew -> new ExpulsionCrewResponse(crew.getName().getName(), crew.calculateAttendanceStatistics(),
                        crew.calculateExpulsionStatus()))
                .toList();
    }

}
