package controller;

import domain.CrewAttendanceStorage;
import view.InputView;
import view.OutputView;

import java.util.EnumMap;
import java.util.Map;

public class MainController {
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewAttendanceStorage storage;
    private final Map<Menu, Controller> controllerMapper = new EnumMap<>(Menu.class);

    public MainController(
            InputView inputView,
            OutputView outputView
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storage = CrewAttendanceStorage.init();
    }

    public void run() {
        init();
        outputView.printDateAndMenus();
        Menu menu = inputView.readMenu();
        while (menu != Menu.QUIT) {
            controllerMapper.get(menu).run();
            outputView.printDateAndMenus();
            menu = inputView.readMenu();
        }
    }

    private void init() {
        Controller storeController = new StoreController(storage);
        storeController.run();

        controllerMapper.put(Menu.ATTENDANCE_CHECK, new AttendanceCheckController(inputView, outputView, storage));
        controllerMapper.put(Menu.ATTENDANCE_MODIFY, new AttendanceModifyController(inputView, outputView, storage));
        controllerMapper.put(
                Menu.ATTENDANCE_HISTORY_CHECK, new AttendanceHistoryController(inputView, outputView, storage)
        );
        controllerMapper.put(Menu.CREW_STATUS_CHECK, new ExpellRiskCheckController(outputView, storage));
    }
}
