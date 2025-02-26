package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceStatus;
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
    private final AttendanceBook attendanceBook;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        final List<String> attendanceLines = FileManager.readFileLines(ATTENDANCE_HISTORY_FILE_NAME);
        attendanceLines.removeFirst();
        attendanceBook = AttendanceBook.of(attendanceLines, LocalDate.now().withYear(2024).withMonth(12));
    }

    public void run() {
        final Operation operation = requestOperation();
        if (operation == Operation.QUIT) {
            return;
        }
        if (operation == Operation.ADD_ATTENDANCE) {
            attendance();
        } else if (operation == Operation.UPDATE_ATTENDANCE) {
            updateAttendance();
        } else if (operation == Operation.LOOKUP_CREW_ATTENDANCE) {
            responseCrewAttendanceHistory();
        } else if (operation == Operation.LOOKUP_EXPULSION_CREWS) {
            responseExpulsionCrews();
        }
        run();
    }

    private Operation requestOperation() {
        outputView.printToday(LocalDate.now().withYear(2024).withMonth(12));
        outputView.printIntroduceOperation();
        return inputView.readChoiceOperation();
    }

    private void attendance() {
        final LocalDate today = LocalDate.now().withYear(2024).withMonth(12);
        if (attendanceBook.isNotAttendanceDay(today)) {
            outputView.printNotAttendanceDay();
            return;
        }
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewName, outputView);
        if (attendanceBook.isAlreadyTodayAttendance(crewName, today)) {
            outputView.printAlreadyAttendance();
            return;
        }
        addAttendance(crewName);
    }

    private void addAttendance(final String crewName) {
        final LocalTime localTime = LoopTemplate.tryCatchLoop(this::readAttendanceTime, outputView);
        final Attendance attendance = LoopTemplate.tryCatchLoop(
                () -> attendanceBook.attendance(crewName, convertLocalDateTime(localTime)), outputView);
        final AttendanceResponse attendanceResponse = convertAttendanceToResponse(attendance);
        outputView.printAttendances(List.of(attendanceResponse));
    }

    private LocalTime readAttendanceTime() {
        outputView.printAddAttendanceDate();
        return inputView.readTime();
    }

    private String inputCrewName() {
        outputView.printAddAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceBook.validateCrewByName(crewName);
        return crewName;
    }

    private LocalDateTime convertLocalDateTime(final LocalTime time) {
        return LocalDateTime.of(LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth()), time);
    }

    private void updateAttendance() {
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewNameForUpdate, outputView);
        final int dayOfMonth = LoopTemplate.tryCatchLoop(this::inputDayOfMonthForUpdate, crewName, outputView);
        modifyAttendance(crewName, dayOfMonth);
    }

    private void modifyAttendance(final String crewName, final int dayOfMonth) {
        final LocalTime targetTime = LoopTemplate.tryCatchLoop(this::inputUpdateTime, outputView);
        final Attendance beforeAttendance = attendanceBook.updateAttendanceByCrewNameAndDay(targetTime, crewName,
                dayOfMonth);
        final LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2024, 12, dayOfMonth), targetTime);
        outputView.printUpdateAttendanceResult(convertAttendanceToResponse(beforeAttendance), targetTime,
                AttendanceStatus.of(localDateTime));
    }

    private LocalTime inputUpdateTime() {
        outputView.printUpdateAttendanceDate();
        return inputView.readTime();
    }

    private int inputDayOfMonthForUpdate(final String crewName) {
        outputView.printUpdateAttendanceDayOfMonth();
        final int dayOfMonth = inputView.readDayOfMonth();
        attendanceBook.validateUpdateAttendanceDay(crewName, dayOfMonth);
        return dayOfMonth;
    }

    private String inputCrewNameForUpdate() {
        outputView.printUpdateAttendanceCrewName();
        final String crewName = inputView.readCrewName();
        attendanceBook.validateCrewByName(crewName);
        return crewName;
    }


    private void responseCrewAttendanceHistory() {
        final String crewName = LoopTemplate.tryCatchLoop(this::inputCrewName, outputView);
        final Crew crew = attendanceBook.findCrewByName(crewName);

        outputView.printCrewAttendances(crewName, convertAttendancesToResponses(crew.getAttendances()));
        outputView.printAttendancesStatistics(crew.calculateAttendanceStatistics(), crew.calculateExpulsionStatus());
    }


    private void responseExpulsionCrews() {
        final List<Crew> crews = attendanceBook.calculateExpulsionCrews();
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
