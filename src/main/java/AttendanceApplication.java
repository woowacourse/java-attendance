import controller.AttendanceController;
import controller.AttendanceControllerExceptionHandleProxy;
import controller.AttendanceControllerImpl;
import util.dataTimeProvider.DateProviderImpl;
import util.inputProvider.DefaultInputProvider;
import util.outputHandler.DefaultOutputHandler;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    
    public static void main(String[] args) throws Exception {
        getAttendanceController().run();
    }
    
    private static AttendanceController getAttendanceController() {
        var outputHandler = new DefaultOutputHandler();
        var dateProvider = new DateProviderImpl();
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
