import controller.AttendanceController;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;
import repository.CrewRepository;
import repository.CrewRepositoryImpl;
import service.AttendanceCheckService;
import service.AttendanceStoreService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        AttendanceRepository attendanceRepository = new AttendanceRepositoryImpl();
        CrewRepository crewRepository = new CrewRepositoryImpl();

        AttendanceController attendanceController = new AttendanceController(
                new InputView(),
                new OutputView(),
                new AttendanceCheckService(crewRepository, attendanceRepository)
        );

        // TODO: 옮기기
        AttendanceStoreService attendanceStoreService = new AttendanceStoreService(
                crewRepository, attendanceRepository
        );
        attendanceStoreService.save();
        // -----------

        attendanceController.checkAttendance();
    }
}
