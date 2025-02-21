package attendance.controller;

import attendance.dto.AttendanceLogResponse;
import attendance.dto.RequiresManagementCrewResponse;
import attendance.dto.UpdateAttendanceResponse;
import attendance.model.domain.crew.Crew;
import attendance.model.domain.crew.CrewAttendanceComparator;
import attendance.model.service.AttendanceService;
import attendance.view.input.InputView;
import attendance.view.output.OutputView;
import java.time.LocalDateTime;
import java.util.List;

public class DefaultController implements Controller {

    private final InputView inputView;
    private final OutputView outputView;

    private final AttendanceService attendanceService;
    private final CrewAttendanceComparator crewAttendanceComparator;

    public DefaultController(
            InputView inputView,
            OutputView outputView,
            AttendanceService attendanceService,
            CrewAttendanceComparator crewAttendanceComparator
    ) {

        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceService = attendanceService;
        this.crewAttendanceComparator = crewAttendanceComparator;
    }

    @Override
    public void run() {
        Command command;
        do {
            command = inputView.inputCommand();
            command.run(this);
        } while (command != Command.QUIT);
    }

    @Override
    public void attendance() {
        try {
            String crewName = inputView.inputNickname();
            Crew crew = attendanceService.findCrewByName(crewName);

            LocalDateTime attendanceTime = inputView.inputAttendanceTime();

            AttendanceLogResponse response = attendanceService.attendance(crew, attendanceTime);

            outputView.printAttendanceLogResponse(response);
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }

    @Override
    public void updateAttendance() {
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

    @Override
    public void checkCrewAttendance() {
        try {
            String crewName = inputView.inputNickname();
            Crew crew = attendanceService.findCrewByName(crewName);

            outputView.printCrewAttendanceLogResponse(attendanceService.getAttendanceLog(crew));
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }

    @Override
    public void printRequiresManagementCrews() {
        try {
            List<RequiresManagementCrewResponse> responses =
                    attendanceService.getRequiresManagementCrews(crewAttendanceComparator);

            outputView.printRequiresManagementCrewResponse(responses);
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }

    @Override
    public void quit() {
    }
}
