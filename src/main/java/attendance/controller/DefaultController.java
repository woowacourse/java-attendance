package attendance.controller;

import attendance.controller.dto.CrewAttendanceResponse;
import attendance.controller.dto.WarningCrewResponse;
import attendance.model.attendance.log.AttendanceLog;
import attendance.model.attendance.log.AttendanceLogs;
import attendance.model.attendance.repository.CrewAttendanceRepository;
import attendance.model.attendance.status.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.Crew;
import attendance.view.input.InputView;
import attendance.view.output.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class DefaultController implements Controller {

    private static final String ERROR_FORMAT = "[ERROR] %s";

    private static final LocalDate START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate TODAY = LocalDate.of(2024, 12, 13);
    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();
    private final CrewAttendanceRepository crewAttendanceRepository;
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public DefaultController(CrewAttendanceRepository crewAttendanceRepository) {
        this.crewAttendanceRepository = crewAttendanceRepository;
    }

    @Override
    public void run() {
        Command command;
        do {
            command = inputCommand();
            command.run(this);
        } while (command != Command.QUIT);
    }

    private Command inputCommand() {
        try {
            return inputView.inputCommand(TODAY);
        } catch (final IllegalArgumentException e) {
            handleException(e);
            return inputCommand();
        }
    }

    @Override
    public void attendance() {
        try {
            final Crew crew = new Crew(inputView.inputCrewNickName());
            final LocalDateTime attendanceDateTime = LocalDateTime.of(TODAY, inputView.inputAttendanceTime());
            final AttendanceLog attendanceLog = AttendanceLog.fromDateTime(attendanceDateTime, campusOperationPolicy);

            crewAttendanceRepository.add(crew, attendanceLog);

        } catch (IllegalArgumentException e) {
            handleException(e);
        }
    }

    @Override
    public void updateAttendance() {
        try {
            final Crew crew = new Crew(inputView.inputUpdateCrewNickName());
            final LocalDateTime attendanceDateTime = LocalDateTime.of(TODAY, inputView.inputUpdateAttendanceTime());
            final AttendanceLog from = crewAttendanceRepository.findAttendanceLogByDate(crew, TODAY);
            final AttendanceLog to = AttendanceLog.fromDateTime(attendanceDateTime, campusOperationPolicy);

            crewAttendanceRepository.update(crew, from, to);

        } catch (IllegalArgumentException e) {
            handleException(e);
        }
    }

    @Override
    public void checkCrewAttendance() {
        try {
            final Crew crew = new Crew(inputView.inputCrewNickName());

            final AttendanceLogs attendanceLogs = crewAttendanceRepository.findByCrewFromTo(
                    crew,
                    START_DATE,
                    TODAY,
                    campusOperationPolicy
            );

            final CrewAttendanceResponse crewAttendanceResponse = CrewAttendanceResponse.from(
                    crew,
                    attendanceLogs,
                    AttendanceStatus.getStatistics(attendanceLogs.getAllAttendanceStatuses())
            );

            outputView.printCrewAttendance(crewAttendanceResponse);

        } catch (IllegalArgumentException e) {
            handleException(e);
        }
    }

    @Override
    public void printRequiresManagementCrews() {
        final Map<Crew, AttendanceLogs> warningCrewAttendanceLogs = crewAttendanceRepository.getWarningCrewAttendanceLogs(
                START_DATE,
                TODAY,
                campusOperationPolicy
        );

        final List<WarningCrewResponse> warningCrewResponses = warningCrewAttendanceLogs.entrySet().stream()
                .map(entry -> WarningCrewResponse.from(entry.getKey(), entry.getValue()))
                .toList();

        outputView.printWarningCrewResponses(warningCrewResponses);
    }

    @Override
    public void quit() {
    }

    private void handleException(final RuntimeException runtimeException) {
        System.out.printf((ERROR_FORMAT) + "%n", runtimeException.getMessage());
    }
}
