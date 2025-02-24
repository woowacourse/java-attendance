package controller;

import domain.DateTimeGenerator;
import domain.ResponseConverter;
import domain.UpdatedAttendanceSnapshot;
import domain.constants.AnswerCommand;
import domain.Attendance;
import domain.constants.AttendanceStatus;
import domain.AttendanceSystem;
import domain.AttendanceSystemFactory;
import domain.Crew;
import domain.constants.ExpulsionStatus;
import domain.constants.UserCommand;
import dto.AttendanceResponse;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import util.LoopTemplate;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final DateTimeGenerator dateTimeGenerator;
    private final AttendanceSystemFactory attendanceSystemFactory;
    private final ResponseConverter responseConverter;

    public AttendanceController(
            final InputView inputView,
            final OutputView outputView,
            final DateTimeGenerator dateTimeGenerator,
            final AttendanceSystemFactory attendanceSystemFactory,
            final ResponseConverter responseConverter
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.dateTimeGenerator = dateTimeGenerator;
        this.attendanceSystemFactory = attendanceSystemFactory;
        this.responseConverter = responseConverter;
    }

    public void run() {
        final AttendanceSystem attendanceSystem =
                attendanceSystemFactory.createAttendanceSystem(dateTimeGenerator.generateDate());
        retryUntilCommandQuit(attendanceSystem);
    }

    private void retryUntilCommandQuit(final AttendanceSystem attendanceSystem) {
        UserCommand command = inputUserCommand();
        while (command != UserCommand.QUIT) {
            selectOperation(attendanceSystem, command);
            command = inputUserCommand();
        }
    }

    public void selectOperation(final AttendanceSystem attendanceSystem, final UserCommand userCommand) {
        switch (userCommand) {
            case ADD_ATTENDANCE -> addAttendance(attendanceSystem);
            case UPDATE_ATTENDANCE -> updateAttendance(attendanceSystem);
            case LOOKUP_CREW_ATTENDANCE -> responseCrewAttendanceHistory(attendanceSystem);
            case LOOKUP_EXPULSION_CREWS -> responseExpulsionCrews(attendanceSystem);
        }
    }

    private void addAttendance(final AttendanceSystem attendanceSystem) {
        if (!attendanceSystem.isAttendanceDay(dateTimeGenerator.generateDate())) {
            outputView.printNotAttendanceDay();
            return;
        }
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewName, attendanceSystem, outputView);
        if (attendanceSystem.isAlreadyTodayAttendance(crewName, dateTimeGenerator.generateDate())) {
            updateAttendanceIfAlreadyTodayAttendance(attendanceSystem, crewName);
            return;
        }
        final Attendance attendance = LoopTemplate.tryCatchLoop(this::attendance, crewName, attendanceSystem
                , outputView);
        final AttendanceResponse attendanceResponse = responseConverter.convertAttendanceToResponse(attendance);
        outputView.printCrewAttendances(List.of(attendanceResponse));
    }

    private Attendance attendance(final String crewName, final AttendanceSystem attendanceSystem) {
        outputView.printAddAttendanceDate();
        final LocalTime attendanceTime = inputView.readTime();
        final Attendance attendance =
                attendanceSystem.attendance(crewName, attendanceTime, dateTimeGenerator.generateDate());
        return attendance;
    }

    private void updateAttendanceByToday(final AttendanceSystem attendanceSystem, final String crewName) {
        final LocalTime targetTime = LoopTemplate.tryCatchLoop(this::inputUpdateTime, outputView);
        final UpdatedAttendanceSnapshot updatedAttendanceSnapshot =
                attendanceSystem.updateTodayAttendance(crewName, targetTime, dateTimeGenerator.generateDate());
        outputView.printUpdateAttendanceResult(
                responseConverter.convertUpdatedAttendanceSnapshotToResponse(updatedAttendanceSnapshot));
    }

    private void updateAttendanceIfAlreadyTodayAttendance(
            final AttendanceSystem attendanceSystem,
            final String crewName
    ) {
        outputView.printIntroduceAnswerCommand();
        final AnswerCommand answerCommand = LoopTemplate.tryCatchLoop(inputView::readAnswerCommand, outputView);
        if (answerCommand == AnswerCommand.YES) {
            updateAttendanceByToday(attendanceSystem, crewName);
        }
    }

    private void updateAttendance(final AttendanceSystem attendanceSystem) {
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewNameForUpdate, attendanceSystem, outputView);
        final int dayOfMonth = LoopTemplate.tryCatchLoop(this::inputDayOfMonthForUpdate, crewName, attendanceSystem,
                outputView);
        final LocalTime targetTime = LoopTemplate.tryCatchLoop(this::inputUpdateTime, outputView);
        final UpdatedAttendanceSnapshot updatedAttendanceSnapshot =
                attendanceSystem.updateAttendanceByCrewNameAndDay(targetTime, crewName, dayOfMonth,
                        dateTimeGenerator.generateDate());
        outputView.printUpdateAttendanceResult(
                responseConverter.convertUpdatedAttendanceSnapshotToResponse(updatedAttendanceSnapshot));
    }

    private void responseCrewAttendanceHistory(final AttendanceSystem attendanceSystem) {
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewName, attendanceSystem, outputView);
        outputView.printAttendanceHistoryTitle(crewName);
        outputAttendances(attendanceSystem, crewName);
        outputRiskOfAttendancesCount(attendanceSystem, crewName);
        outputRiskOfExpulsionStatus(attendanceSystem, crewName);

    }

    private void outputAttendances(final AttendanceSystem attendanceSystem, final String crewName) {
        final List<AttendanceResponse> attendanceResponses =
                responseConverter.convertAttendancesToResponses(attendanceSystem.getAttendancesByCrew(crewName));
        outputView.printCrewAttendances(attendanceResponses);
    }

    private void outputRiskOfAttendancesCount(final AttendanceSystem attendanceSystem, final String crewName) {
        final Map<AttendanceStatus, Integer> attendanceStatistics =
                attendanceSystem.calculateAttendanceStatisticsByCrew(crewName);
        outputView.printAttendancesStatistics(attendanceStatistics);
    }

    private void outputRiskOfExpulsionStatus(final AttendanceSystem attendanceSystem, final String crewName) {
        final ExpulsionStatus expulsionStatus = attendanceSystem.calculateExpulsionStatusByCrew(crewName);
        outputView.printCrewExpulsionStatus(expulsionStatus);
    }

    private void responseExpulsionCrews(final AttendanceSystem attendanceSystem) {
        final List<Crew> crews = attendanceSystem.calculateRiskOfExpulsionCrews();
        outputView.printExpulsionCrewResponses(responseConverter.convertExpulsionCrewResponses(crews));
    }

    private UserCommand inputUserCommand() {
        outputView.printToday(dateTimeGenerator.generateDate());
        outputView.printIntroduceOperation();
        final UserCommand userCommand = inputView.readChoiceOperation();
        return userCommand;
    }

    private String inputCrewName(final AttendanceSystem attendanceSystem) {
        outputView.printAddAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceSystem.validateCrewByName(crewName);
        return crewName;
    }

    private LocalTime inputUpdateTime() {
        outputView.printUpdateAttendanceDate();
        final LocalTime targetTime = inputView.readTime();
        return targetTime;
    }

    private int inputDayOfMonthForUpdate(final String crewName, final AttendanceSystem attendanceSystem) {
        outputView.printUpdateAttendanceDayOfMonth();
        final int dayOfMonth = inputView.readDayOfMonth();
        attendanceSystem.validateUpdateAttendanceDay(crewName, dayOfMonth, dateTimeGenerator.generateDate());
        return dayOfMonth;
    }

    private String inputCrewNameForUpdate(final AttendanceSystem attendanceSystem) {
        outputView.printUpdateAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceSystem.validateCrewByName(crewName);
        return crewName;
    }

}
