package attendance;

import attendance.domain.CrewAttendanceManager;
import attendance.util.CurrentDateGenerator;
import attendance.util.DateGenerator;
import attendance.view.InputView;
import attendance.view.OutputView;

class Application {

    private static final String ATTENDANCES_CSV_FILE_NAME = "attendances.csv";

    public static void main(String[] args) {

        DateGenerator dateGenerator = new CurrentDateGenerator();

        AttendanceSystem attendanceSystem = new AttendanceSystem(
                new InputView(),
                new OutputView(),
                dateGenerator,
                new CrewAttendanceManager(dateGenerator, ATTENDANCES_CSV_FILE_NAME)
        );

        attendanceSystem.run();
    }
}
