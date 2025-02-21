import controller.AttendanceController;
import controller.AttendanceControllerExceptionHandleProxy;
import controller.AttendanceControllerImpl;
import io.view.InputView;
import io.view.OutputView;
import util.dataTimeProvider.DefaultDateProvider;
import util.inputProvider.DefaultInputProvider;
import util.outputHandler.DefaultOutputHandler;

public class AttendanceApplication {
    
    public static void main(String[] args) throws Exception {
        getAttendanceController().run();
    }
    
    private static AttendanceController getAttendanceController() {
        var outputHandler = new DefaultOutputHandler();
        var dateProvider = new DefaultDateProvider();
        AttendanceController targetController = new AttendanceControllerImpl(
                dateProvider,
                new InputView(
                        new DefaultInputProvider(),
                        outputHandler,
                        dateProvider
                ),
                new OutputView(
                        outputHandler,
                        dateProvider
                )
        );
        
        return new AttendanceControllerExceptionHandleProxy(
                outputHandler,
                targetController
        );
    }
}
