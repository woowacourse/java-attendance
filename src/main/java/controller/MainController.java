//package controller;
//
//import exception.CrewNotExistException;
//import exception.DuplicateAttendanceException;
//import view.InputView;
//import view.OutputView;
//
//import java.util.EnumMap;
//import java.util.Map;
//
//public class MainController {
//    private final Controller storeController;
//    private final InputView inputView;
//    private final OutputView outputView;
//    private final Map<Menu, Controller> controllerMapper = new EnumMap<>(Menu.class);
//
//    public MainController(
//            InputView inputView,
//            OutputView outputView,
//            Controller storeController,
//            Controller attendanceController,
//            Controller modifyController,
//            Controller historyController,
//            Controller disenrollmentCheckController
//    ) {
//        this.inputView = inputView;
//        this.outputView = outputView;
//        this.storeController = storeController;
//        controllerMapper.put(Menu.ATTENDANCE_CHECK, attendanceController);
//        controllerMapper.put(Menu.ATTENDANCE_MODIFY, modifyController);
//        controllerMapper.put(Menu.ATTENDANCE_HISTORY_CHECK, historyController);
//        controllerMapper.put(Menu.CREW_STATUS_CHECK, disenrollmentCheckController);
//    }
//
//    public void run() {
//        storeController.run();
//        while (true) {
//            outputView.printDateAndMenus();
//            Menu menu = inputView.readMenu();
//            if (menu == Menu.QUIT) {
//                return;
//            }
//            runController(controllerMapper.get(menu));
//        }
//    }
//
//    private void runController(Controller controller) {
//        try {
//            controller.run();
//        } catch (DuplicateAttendanceException e) {
//            outputView.recommendModifyFunction(e.getMessage());
//        } catch (Exception e) {
//            outputView.printExceptionMessage(e.getMessage());
//        }
//    }
//}
