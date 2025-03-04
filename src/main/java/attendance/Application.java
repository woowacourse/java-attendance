package attendance;

import attendance.controller.DefaultController;
import attendance.model.attendance.log.CrewAttendanceLogDeserializer;
import attendance.model.attendance.repository.CrewAttendanceRepository;
import attendance.model.campus.CampusOperationPolicy;
import java.nio.file.Path;

public class Application {

    public static void main(String[] args) {

        final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();
        final Path path = Path.of("src/main/resources/attendances.csv");
        final CrewAttendanceLogDeserializer crewAttendanceLogDeserializer = new CrewAttendanceLogDeserializer();
        final CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(
                crewAttendanceLogDeserializer,
                path,
                campusOperationPolicy
        );

        final DefaultController defaultController = new DefaultController(crewAttendanceRepository);
        defaultController.run();
    }
}
