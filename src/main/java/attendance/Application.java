package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceHistory;
import attendance.domain.Crew;
import attendance.domain.CustomClock;
import attendance.domain.EducationDayPolicy;
import attendance.loader.AttendanceAssembler;
import attendance.loader.AttendancesLoader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        CustomClock clock = new CustomClock(LocalDateTime.of(2024, 12, 15, 13, 0));
        EducationDayPolicy policy = new EducationDayPolicy(Set.of(LocalDate.of(2024, 12, 25)));

        InputView inputView = new InputView(clock);
        OutputView outputView = new OutputView();
        AttendanceAssembler assembler = new AttendanceAssembler(new AttendancesLoader(), policy);
        Map<Crew, AttendanceHistory> assembleDatas = assembler.assembleDatas();
        AttendanceBook attendanceBook = new AttendanceBook(assembleDatas);
        AttendanceController controller = new AttendanceController(inputView, outputView, attendanceBook, clock,
                policy);

        controller.run();

    }
}
