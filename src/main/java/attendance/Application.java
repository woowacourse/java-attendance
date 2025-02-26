package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceHistory;
import attendance.domain.Crews;
import attendance.loader.AttendanceAssembler;
import attendance.loader.AttendancesLoader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceAssembler assembler = new AttendanceAssembler(new AttendancesLoader());
        Map<String, AttendanceHistory> assembleDatas = assembler.assembleDatas();
        Crews crews = assembler.assembleCrews(assembleDatas);

        AttendanceBook attendanceBook = new AttendanceBook(assembleDatas);
        AttendanceController controller = new AttendanceController(inputView, outputView, attendanceBook, crews);

        controller.run();

    }
}
