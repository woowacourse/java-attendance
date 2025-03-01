import java.io.*;
import java.time.Clock;

import controller.AttendanceController;
import controller.AttendanceControllerExceptionWriteProxy;
import controller.AttendanceControllerImpl;
import factory.BufferedReaderAttendanceBookFactory;
import io.reader.ConsoleReader;
import io.view.InputView;
import io.view.OutputView;
import io.writer.ConsoleWriter;
import service.today_provider.ClockTodayProvider;

public class AttendanceApplication {
    
    public static void main(String[] args) throws FileNotFoundException {
        var controller = setupController();
        controller.run();
    }
    
    private static AttendanceController setupController() throws FileNotFoundException {
        var reader = new ConsoleReader(new BufferedReader(new InputStreamReader(System.in)));
        var writer = new ConsoleWriter(new PrintWriter(new OutputStreamWriter(System.out), true));
        
        var fileReader = new BufferedReader(new FileReader(getFilePath()));
        var todayProvider = new ClockTodayProvider(Clock.systemDefaultZone());
        
        var attendanceController = new AttendanceControllerImpl(
                new InputView(reader, writer),
                new OutputView(writer),
                todayProvider,
                new BufferedReaderAttendanceBookFactory(todayProvider, fileReader)
        );
        
        return new AttendanceControllerExceptionWriteProxy(attendanceController, writer);
    }
    
    private static String getFilePath() {
        return Thread.currentThread().getContextClassLoader().getResource("attendances.csv").getPath();
    }
}
