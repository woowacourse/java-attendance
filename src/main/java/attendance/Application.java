package attendance;

import attendance.domain.CrewAttendanceManager;
import attendance.util.CurrentDateGenerator;
import attendance.util.DateGenerator;
import attendance.view.InputView;
import attendance.view.OutputView;

class Application {
    public static void main(String[] args) {
        DateGenerator dateGenerator = new CurrentDateGenerator();

        AttendanceSystem attendanceSystem = new AttendanceSystem(
                new InputView(),
                new OutputView(),
                dateGenerator,
                new CrewAttendanceManager(dateGenerator)
        );

        attendanceSystem.run();
    }
}
