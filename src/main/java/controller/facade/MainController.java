package controller.facade;

import exception.handler.ExceptionHandler;
import controller.sub.parent.SubController;
import view.InputView;
import view.OutputView;

import java.util.EnumMap;
import java.util.Map;

public class MainController {
    private final SubController storeController;
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Menu, SubController> controllerMapper = new EnumMap<>(Menu.class);


    public MainController(
            InputView inputView,
            OutputView outputView,
            SubController storeController,
            SubController attendanceController,
            SubController modifyController,
            SubController historyController,
            SubController disenrollmentCheckController
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeController = storeController;

        controllerMapper.put(Menu.ATTENDANCE_CHECK, attendanceController);
        controllerMapper.put(Menu.ATTENDANCE_MODIFY, modifyController);
        controllerMapper.put(Menu.ATTENDANCE_HISTORY_CHECK, historyController);
        controllerMapper.put(Menu.CREW_STATUS_CHECK, disenrollmentCheckController);
    }

    public void run() {
        storeController.run();
        while (true) {
            Menu menu = readMenu();
            if (menu == Menu.QUIT) {
                return;
            }
            controllerMapper.get(menu).run();
        }
    }

    private Menu readMenu() {
        return ExceptionHandler.retryIfIllegalArgumentAndReturn(() -> {
            outputView.printDateAndMenus();
            return inputView.readMenu();
        });
    }
}
