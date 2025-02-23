package controller;

import domain.Attendance;
import domain.MenuOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import util.AttendancesFileHandler;
import util.RepeatExecutor;
import view.InputView;
import view.OutputView;

public class MainController {

    private final InputView inputView;
    private final OutputView outputView;
    private final RepeatExecutor repeatExecutor;
    private final Map<MenuOption, AttendanceController> options;

    public MainController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.repeatExecutor = new RepeatExecutor(outputView);

        this.options = new HashMap<>();
        this.options.put(MenuOption.ATTENDANCE_REGISTER, new AttendanceRegisterController());
        this.options.put(MenuOption.ATTENDANCE_CORRECTION, new AttendanceEditController());
        this.options.put(MenuOption.CREW_ATTENDANCE_CHECK, new AttendanceCheckController());
        this.options.put(MenuOption.CHECK_EXPELLED_CREW, new AttendanceCheckExpelledController());
    }

    public void run() {
        LocalDate nowDate = LocalDate.now();
        Attendance attendance = new Attendance(AttendancesFileHandler.generateAttendances(), nowDate);

        repeatExecutor.repeatUntilSuccess(() -> {
            processUntilQuitInput(nowDate, attendance);
            return RepeatExecutor.SUCCESS;
        });
    }

    private void processUntilQuitInput(LocalDate nowDate, Attendance attendance) {
        MenuOption menuOption;
        do {
            String option = getOptionInput(nowDate);
            menuOption = MenuOption.findByCommand(option);
            this.options.get(menuOption).process(attendance, nowDate);
        } while (!menuOption.equals(MenuOption.QUIT));
    }

    private String getOptionInput(LocalDate nowDate) {
        outputView.printMenuHeader(nowDate);
        return inputView.readOption(Arrays.asList(MenuOption.values()));
    }
}
