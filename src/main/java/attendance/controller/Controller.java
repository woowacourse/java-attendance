package attendance.controller;

import attendance.dto.AttendanceLogResponse;
import attendance.dto.RequiresManagementCrewResponse;
import attendance.dto.UpdateAttendanceResponse;
import attendance.model.domain.crew.Crew;
import attendance.model.domain.crew.CrewAttendanceComparator;
import attendance.model.domain.crew.DefaultCrewAttendanceComparator;
import attendance.model.repository.AttendanceRepository;
import attendance.model.repository.CrewAttendanceDeserializer;
import attendance.model.service.AttendanceService;
import attendance.view.input.ConsoleInputView;
import attendance.view.input.InputView;
import attendance.view.output.ConsoleOutputView;
import attendance.view.output.OutputView;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private final Map<Command, Runnable> commandActions = new HashMap<>();

    private final InputView inputView = new ConsoleInputView();
    private final OutputView outputView = new ConsoleOutputView();

    private final CrewAttendanceDeserializer crewAttendanceDeserializer = new CrewAttendanceDeserializer();
    private final Path crewAttendanceDataPath = Path.of("src/main/resources/attendances.csv");
    private final AttendanceRepository attendanceRepository = new AttendanceRepository(crewAttendanceDeserializer,
            crewAttendanceDataPath);
    private final AttendanceService attendanceService = new AttendanceService(attendanceRepository);
    private final CrewAttendanceComparator crewAttendanceComparator = new DefaultCrewAttendanceComparator();

    public Controller() {
        setUpCommandActions();
    }

    public void run() {
        Command command;
        do {
            command = inputView.inputCommand();
            commandActions.get(command).run();
        } while (command != Command.QUIT);
    }

    private void setUpCommandActions() {
        commandActions.put(Command.ATTENDANCE, this::attendance);
        commandActions.put(Command.UPDATE_ATTENDANCE, this::updateAttendance);
        commandActions.put(Command.GET_ATTENDANCE_LOG, this::checkCrewAttendance);
        commandActions.put(Command.GET_REQUIRES_MANAGEMENT_CREWS, this::printRequiresManagementCrews);
    }

    private void attendance() {
        try {
            String crewName = inputView.inputNickname();
            Crew crew = attendanceService.findCrewByName(crewName);

            LocalDateTime attendanceTime = inputView.inputAttendanceTime();

            AttendanceLogResponse response = attendanceService.attendance(crew, attendanceTime);

            outputView.printAttendanceLog(response);
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }

    private void updateAttendance() {
        try {
            String crewName = inputView.inputUpdateCrewName();
            Crew crew = attendanceService.findCrewByName(crewName);

            LocalDateTime updatedTime = LocalDateTime.of(inputView.inputUpdateAttendanceDate(),
                    inputView.inputUpdateAttendanceTime());

            UpdateAttendanceResponse updateAttendanceResponse = attendanceService.updateAttendance(crew, updatedTime);

            outputView.printUpdateAttendanceResponse(updateAttendanceResponse);

        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }

    private void checkCrewAttendance() {
        try {
            String crewName = inputView.inputNickname();
            Crew crew = attendanceService.findCrewByName(crewName);

            outputView.printCrewAttendanceLogResponse(attendanceService.getAttendanceLog(crew));
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }

    private void printRequiresManagementCrews() {
        try {
            List<RequiresManagementCrewResponse> responses =
                    attendanceService.getRequiresManagementCrews(crewAttendanceComparator);

            outputView.printRequiresManagementCrewResponse(responses);
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }
}
