package controller;

import domain.AnswerCommand;
import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceSystem;
import domain.Crew;
import domain.ExpulsionStatus;
import domain.Operation;
import dto.AttendanceResponse;
import dto.ExpulsionCrewResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import util.FileManager;
import util.LoopTemplate;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final String ATTENDANCE_HISTORY_FILE_NAME = "attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceSystem attendanceSystem;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        final List<String> attendanceLines = FileManager.readFileLines(ATTENDANCE_HISTORY_FILE_NAME);
        attendanceLines.remove(0);
        attendanceSystem = AttendanceSystem.of(attendanceLines, LocalDate.now().withYear(2024).withMonth(12));
    }

    public void run() {
        retryUntilOperationQuit();
    }


    public Operation selectOperation() {
        outputView.printToday(LocalDate.now().withYear(2024).withMonth(12));
        outputView.printIntroduceOperation();
        final Operation operation = inputView.readChoiceOperation();
        if (operation == Operation.ADD_ATTENDANCE) {
            addAttendance();
        } else if (operation == Operation.UPDATE_ATTENDANCE) {
            updateAttendance();
        } else if (operation == Operation.LOOKUP_CREW_ATTENDANCE) {
            responseCrewAttendanceHistory();
        } else if (operation == Operation.LOOKUP_EXPULSION_CREWS) {
            responseExpulsionCrews();
        }
        return operation;
    }

    private void retryUntilOperationQuit() {
        if (selectOperation() != Operation.QUIT) {
            retryUntilOperationQuit();
        }
    }


    private void addAttendance() {
        final LocalDate today = LocalDate.now().withYear(2024).withMonth(12);
        if (attendanceSystem.isNotAttendanceDay(today)) {
            outputView.printNotAttendanceDay();
            return;
        }
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewName, outputView);
        if (!attendanceSystem.isAlreadyTodayAttendance(crewName, today)) {
            final Attendance attendance = LoopTemplate.tryCatchLoop(this::attendance, crewName, outputView);
            final AttendanceResponse attendanceResponse = convertAttendanceToResponse(attendance);
            outputView.printCrewAttendances(List.of(attendanceResponse));
            return;
        }
        updateAttendanceForDuplicateAttendance(crewName);
    }

    private void updateAttendanceForDuplicateAttendance(final String crewName) {
        outputView.printIntroduceAnswerCommand();
        final AnswerCommand answerCommand = inputView.readAnswerCommand();
        if (answerCommand == AnswerCommand.YES) {
            final int dayOfMonth = LocalDate.now().getDayOfMonth();
            final LocalTime targetTime = LoopTemplate.tryCatchLoop(this::inputUpdateTime, outputView);
            final Attendance beforeAttendance = attendanceSystem.findAttendanceByDate(crewName, dayOfMonth);
            final Attendance afterAttendance = attendanceSystem.updateAttendanceByCrewNameAndDay(targetTime, crewName,
                    dayOfMonth);
            outputView.printUpdateAttendanceResult(convertAttendanceToResponse(beforeAttendance),
                    convertAttendanceToResponse(afterAttendance));
        }
    }

    private Attendance attendance(final String crewName) {
        outputView.printAddAttendanceDate();
        final LocalTime localDateTime = inputView.readTime();
        return attendanceSystem.attendance(crewName, convertLocalDateTime(localDateTime));
    }

    private String inputCrewName() {
        outputView.printAddAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceSystem.validateCrewByName(crewName);
        return crewName;
    }

    private LocalDateTime convertLocalDateTime(final LocalTime time) {
        return LocalDateTime.of(LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth()), time);
    }

    private void updateAttendance() {
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewNameForUpdate, outputView);
        final int dayOfMonth = LoopTemplate.tryCatchLoop(this::inputDayOfMonthForUpdate, crewName, outputView);
        final LocalTime targetTime = LoopTemplate.tryCatchLoop(this::inputUpdateTime, outputView);
        final Attendance beforeAttendance = attendanceSystem.findAttendanceByDate(crewName, dayOfMonth);
        final Attendance afterAttendance = attendanceSystem.updateAttendanceByCrewNameAndDay(targetTime, crewName,
                dayOfMonth);
        outputView.printUpdateAttendanceResult(convertAttendanceToResponse(beforeAttendance),
                convertAttendanceToResponse(afterAttendance));
    }

    private LocalTime inputUpdateTime() {
        outputView.printUpdateAttendanceDate();
        return inputView.readTime();
    }

    private int inputDayOfMonthForUpdate(final String crewName) {
        outputView.printUpdateAttendanceDayOfMonth();
        final int dayOfMonth = inputView.readDayOfMonth();
        attendanceSystem.validateUpdateAttendanceDay(crewName, dayOfMonth);
        return dayOfMonth;
    }

    private String inputCrewNameForUpdate() {
        outputView.printUpdateAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceSystem.validateCrewByName(crewName);
        return crewName;
    }


    private void responseCrewAttendanceHistory() {
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewName, outputView);
        final Crew crew = attendanceSystem.findCrewByName(crewName);
        outputView.printAttendanceHistoryTitle(crewName);
        final List<AttendanceResponse> attendanceResponses = convertAttendancesToResponses(crew.getAttendances());
        outputView.printCrewAttendances(attendanceResponses);
        final Map<AttendanceStatus, Integer> attendanceStatistics = crew.calculateAttendanceStatistics();
        outputView.printAttendancesStatistics(attendanceStatistics);
        final ExpulsionStatus expulsionStatus = crew.calculateExpulsionStatus();
        outputView.printCrewExpulsionStatus(expulsionStatus);

    }


    private void responseExpulsionCrews() {
        final List<Crew> crews = attendanceSystem.calculateExpulsionCrews();
        outputView.printExpulsionCrewResponses(convertExpulsionCrewResponses(crews));

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
