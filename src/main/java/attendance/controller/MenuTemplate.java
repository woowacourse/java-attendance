package attendance.controller;

import attendance.view.OutputView;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;

public class MenuTemplate {

    public static void run(Consumer<AttendanceMenu> method, AttendanceMenu menu, OutputView outputView) {
        while (true) {
            try {
                method.accept(menu);
                return;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    public static AttendanceMenu run(
            Function<LocalDate, AttendanceMenu> method, LocalDate menu, OutputView outputView
    ) {
        while (true) {
            try {
                return method.apply(menu);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
