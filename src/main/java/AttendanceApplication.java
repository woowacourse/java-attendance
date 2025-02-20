import controller.AttendanceController;
import util.dataTimeProvider.DateProviderImpl;
import util.inputProvider.DefaultInputProvider;
import util.outputHandler.DefaultOutputHandler;
import view.InputView;
import view.OutputView;

import java.io.IOException;

public class AttendanceApplication {
    
    public static void main(String[] args) throws IOException {
        
        var defaultOutputHandler = new DefaultOutputHandler();
        
        var dateProvider = new DateProviderImpl();
        new AttendanceController(
                dateProvider,
                new InputView(
                        new DefaultInputProvider(),
                        defaultOutputHandler,
                        dateProvider
                ),
                new OutputView(
                        new DefaultOutputHandler(),
                        dateProvider
                )
        ).run();
    }
}
