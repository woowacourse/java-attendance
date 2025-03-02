package attendance.config;

import attendance.controller.AttendanceController;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatistics;
import attendance.domain.DefaultAttendanceStatistics;
import attendance.domain.LocalDateProvider;
import attendance.util.DataLoader;
import attendance.view.DataFileReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
//        LocalDateProvider localDateProvider = new SystemLocalDateProvider();
        LocalDateProvider localDateProvider = new FixedLocalDateProvider(LocalDate.of(2024, 12, 31));
        AttendanceStatistics attendanceStatistics = new DefaultAttendanceStatistics(localDateProvider);

        InputView inputView = new InputView(localDateProvider);
        OutputView outputView = new OutputView(localDateProvider);

        AttendanceManager attendanceManager = new AttendanceManager(
                DataLoader.loadAttendancesData(DataFileReader.readFile()), localDateProvider, attendanceStatistics);

        AttendanceController controller = new AttendanceController(inputView, outputView, attendanceManager);

        controller.run();
    }
}
