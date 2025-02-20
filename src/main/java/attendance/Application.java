package attendance;

import attendance.controller.Controller;
import attendance.model.domain.crew.DefaultCrewAttendanceComparator;
import attendance.model.repository.AttendanceRepository;
import attendance.model.repository.CrewAttendanceDeserializer;
import attendance.model.service.AttendanceService;
import attendance.view.input.ConsoleInputView;
import attendance.view.output.ConsoleOutputView;
import java.nio.file.Path;

public class Application {

    public static void main(String[] args) {

        AttendanceRepository attendanceRepository = new AttendanceRepository(
                new CrewAttendanceDeserializer(),
                Path.of("src/main/resources/attendances.csv")
        );

        Controller controller = new Controller(
                new ConsoleInputView(),
                new ConsoleOutputView(),
                new AttendanceService(attendanceRepository),
                new DefaultCrewAttendanceComparator()
        );

        controller.run();
    }
}
